import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Piece {
    /*
     * validMoves works by taking in a list of positions of possible moves, stored
     * in an int array of 2 elements, storing the shift. Since some moves are of
     * form 'can move any distance in a specific direction', these are represented
     * using a single-element array that holds a MovementType enum's value.
     * This single-element array is then converted on call to toggleMoves() to an
     * array of positions along that direction that can be moved to
     */
    private Board board;
    private Tile tile;
    private List<int[]> validMoves;
    private List<int[]> selectedMoves;
    private PieceType type;
    private final Color team;
    private boolean selected;

    public Piece(Color c, PieceType type, Board board) {
        this.team = c;
        this.type = type;
        this.board = board;
        validMoves = type.getNormalMoves();
        selected = false;
    }

    public void toggleMoves() {
        selected = !selected;
        tile.setPossible(selected);
        int x = tile.getX(),
                y = team.translateY(tile.getY());
        Tile[][] relPositions = team.getRelativeGrid(board);
        // board.printGrid(relPositions);

        selectedMoves = type.getConditionalMoves(relPositions, x, y);
        selectedMoves.addAll(validMoves);
        selectedMoves.stream().flatMap(e -> {
            if (e.length == 2) {
                e[1] = team.translateY(e[1]);
                return Stream.of(e);
            }

            List<int[]> extendingMoves = walkAlong(relPositions, MovementType.fromValue(e[0]), x, y);
            extendingMoves.stream().forEach(arr -> {
                arr[1] = team.translateY(arr[1]);
            });
            return extendingMoves.stream();

        }).forEach(arr -> {
            board.tiles[arr[0]][arr[1]].setPossible(selected);
        });

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

        Tile nextStep = null;

        while (xPos >= 0 && xPos <= 7 &&
                yPos >= 0 && yPos <= 7 &&
                (nextStep == null || nextStep.getCurrentPiece().isEmpty())) {
            nextStep = grid[xPos][yPos];
            int pos[] = { xPos, yPos };
            spaces.add(pos);

            xPos += xShift;
            yPos += yShift;
        }

        if (nextStep != null && nextStep.getCurrentPiece().isPresent()
                && nextStep.getCurrentPiece().get().team == team) {
            spaces.remove(spaces.size() - 1);
        }

        return spaces;
    }

    public void moveTo(Tile t) {
        toggleMoves();
        tile.setCurrentPiece(Optional.empty());
        t.setCurrentPiece(Optional.of(this));
        tile = t;
    }

    public Color getTeam() {
        return team;
    }

    public PieceType getType() {
        return type;
    }

    public List<int[]> getValidMoves() {
        return validMoves;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public Tile getTile() {
        return tile;
    }
}