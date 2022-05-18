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
                .peek(this::filterPossMoves)
                .flatMap(list -> list.stream()) // will remove this mapper for animations
                .forEach(arr -> board.tiles[arr[0]][arr[1]].setPossible(selected));
    }

    public Stream<List<int[]>> getPossMoves(Tile[][] relPositions, int x, int y) {
        List<List<int[]>> moves = new ArrayList<>();
        type.getConditionalMoves(relPositions, x, y).forEach(arr -> {
            List<int[]> singleMove = new ArrayList<>();
            singleMove.add(arr);
            moves.add(singleMove);
        });
        moves.addAll(type.getNormalMoves().stream().flatMap(e -> {
            if (e.length == 1) {
                List<List<int[]>> extendingMoves = walkAlong(relPositions, MovementType.fromValue(e[0]), x, y);
                return extendingMoves.stream();
            }

            // not set as possible since checks if is in bounds before using
            int arr[] = { x + e[0], y + e[1] };
            if (arr[0] > 7 || arr[0] < 0 || arr[1] > 7 || arr[1] < 0)
                return Stream.empty();

            if (!Tile.sameTeam(this.tile, relPositions[arr[0]][arr[1]])) {

                List<int[]> singleMove = new ArrayList<>();
                singleMove.add(arr);
                return Stream.of(singleMove);
            }

            return Stream.empty();

        }).toList());
        return moves.stream().peek(list -> list.forEach(arr -> arr[1] = team.translateY(arr[1])));

    }

    private List<List<int[]>> walkAlong(Tile[][] grid, MovementType direction, int xStart, int yStart) {
        List<List<int[]>> validValues = new ArrayList<>();
        validValues.add(walkInDirection(grid, xStart, yStart, direction.getxShift(), direction.getyShift()));
        validValues.add(walkInDirection(grid, xStart, yStart, -direction.getxShift(), -direction.getyShift()));

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

            int pos[] = { xPos, yPos };
            spaces.add(pos);

            if (nextStep.currentPiece.isPresent())
                break;

            xPos += xShift;
            yPos += yShift;
        }

        return spaces;
    }

    private List<int[]> filterPossMoves(List<int[]> list) {
        return list.stream().filter(arr -> {

            if (type.name() == "King") {
                boolean[][] checkedSpots = King.spotsInCheck(board.tiles, team);

                return !checkedSpots[arr[0]][arr[1]];
            } else if (team.isInCheck()) {
                Tile[][] possiblity = supposeMoveTo(arr[0], arr[1]);
                Tile kingTile = team.getKing().tile;
                if (King.spotsInCheck(possiblity, team)[kingTile.getX()][kingTile.getY()])
                    return false;
            }
            return true;
        }).toList();
    }

    // started but abandoned due to concurrency issues
    @SuppressWarnings("unused")
    private void runAnimation(List<List<int[]>> movePatterns) {
        ParallelTransition full = new ParallelTransition();

        double scale1 = 1.2, scale2 = 1.01;
        int duration1 = 100, duration2 = 50;
        for (List<int[]> moves : movePatterns) {
            SequentialTransition moveAnimation = new SequentialTransition();

            for (int[] pos : moves) {
                ScaleTransition grow = new ScaleTransition(Duration.millis(duration1), board.tiles[pos[0]][pos[1]]);
                grow.setByX(scale1);
                grow.setByY(scale1);
                grow.setAutoReverse(true);
                grow.setCycleCount(2);
                grow.setInterpolator(Interpolator.EASE_BOTH);
                grow.setOnFinished(e -> board.tiles[pos[0]][pos[1]].setPossible(selected));
                moveAnimation.getChildren().add(grow);
                ScaleTransition shrink = new ScaleTransition(Duration.millis(duration2), board.tiles[pos[0]][pos[1]]);
                shrink.setByY(scale2);
                shrink.setByX(scale2);
                shrink.setAutoReverse(true);
                shrink.setCycleCount(2);
                shrink.setInterpolator(Interpolator.EASE_BOTH);
                moveAnimation.getChildren().add(shrink);
            }
            if (moveAnimation.getChildren().size() > 0)
                full.getChildren().add(moveAnimation);
        }
        full.play();
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