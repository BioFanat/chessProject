import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.util.Duration;

public class Piece {
    private Board board;
    private Tile tile;
    private PieceType type;
    private final Color team;
    private boolean selected;

    public Piece(Color c, PieceType type, Board board) {
        this.team = c;
        this.type = type;
        this.board = board;
        selected = false;
    }

    public void toggleMoves() {
        selected = !selected;

        int x = tile.getX(),
                y = team.translateY(tile.getY());
        Tile[][] relPositions = team.getRelativeGrid(board.tiles);

        getPossMoves(relPositions, x, y)
                .filter(this::filterPossMoves)
                .forEach(arr -> board.tiles[arr[0]][arr[1]].setPossible(selected));
    }

    public Stream<int[]> getPossMoves(Tile[][] relPositions, int x, int y) {
        List<int[]> moves = new ArrayList<>();

        type.getConditionalMoves(relPositions, x, y).stream()
                .forEach(moves::add);
        type.getNormalMoves().stream()
                .flatMap(e -> {
                    if (e.length == 1) {
                        return walkAlong(relPositions, MovementType.fromValue(e[0]), x, y).stream();
                    }

                    // not set as possible since checks if is in bounds before using
                    int arr[] = { x + e[0], y + e[1] };
                    if (arr[0] > 7 || arr[0] < 0 || arr[1] > 7 || arr[1] < 0 ||
                            Tile.sameTeam(this.tile, relPositions[arr[0]][arr[1]]))
                        return Stream.empty();

                    return Stream.of(arr);
                })
                .forEach(moves::add);

        return moves.stream().peek(arr -> arr[1] = team.translateY(arr[1]));

    }

    private List<int[]> walkAlong(Tile[][] grid, MovementType direction, int xStart, int yStart) {
        List<int[]> validValues = new ArrayList<>();
        validValues.addAll(walkInDirection(grid, xStart, yStart, direction.getxShift(), direction.getyShift()));
        validValues.addAll(walkInDirection(grid, xStart, yStart, -direction.getxShift(), -direction.getyShift()));

        return validValues;
    }

    private List<int[]> walkInDirection(Tile[][] grid, int xStart, int yStart, int xShift, int yShift) {
        List<int[]> spaces = new ArrayList<>();
        int xPos = xStart + xShift,
                yPos = yStart + yShift;

        if (xPos < 0 || xPos > 7 || yPos < 0 || yPos > 7)
            return spaces;

        while (xPos >= 0 && xPos <= 7 &&
                yPos >= 0 && yPos <= 7) {
            Tile nextStep = grid[xPos][yPos];
            if (Tile.sameTeam(this.tile, nextStep))
                break;

            spaces.add(PieceType.pos(xPos, yPos));

            if (nextStep.currentPiece.isPresent())
                break;

            xPos += xShift;
            yPos += yShift;
        }

        return spaces;
    }

    private boolean filterPossMoves(int[] arr) {

        if (type.name() == "King") {
            boolean[][] checkedSpots = King.spotsInCheck(board.tiles, team);

            return !checkedSpots[arr[0]][arr[1]];
        }
        Tile[][] possiblity = supposeMoveTo(arr[0], arr[1]);
        Tile kingTile = team.getKing().tile;

        return !King.spotsInCheck(possiblity, team)[kingTile.getX()][kingTile.getY()];
    }

    public void moveTo(Tile t) {
        toggleMoves();
        tile.setCurrentPiece(Optional.empty());
        t.setCurrentPiece(Optional.of(this));
        tile = t;
        type.addMove();
        if (team.isInCheck())
            team.setInCheck(false);
    }

    /*
     * just creates a temporary tile[][] with this piece at the poisition specified,
     * leaving behind a empty tile at its current position. Used for checking how
     * this would affect check.
     */
    public Tile[][] supposeMoveTo(int x, int y) {
        Tile[][] newArr = Arrays.stream(board.tiles).map(el -> el.clone()).toArray($ -> board.tiles.clone());

        newArr[x][y] = tile;
        newArr[tile.getX()][tile.getY()] = new Tile(Color.BLACK, tile.getX(), tile.getY(), board);

        return newArr;
    }

    public Color getTeam() {
        return team;
    }

    public PieceType getType() {
        return type;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public Tile getTile() {
        return tile;
    }
}