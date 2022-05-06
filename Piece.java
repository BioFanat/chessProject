import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Piece {
    /*
     * validMoves works by taking in a list of positions of possible moves, stored
     * in an int array of 2 elements, storing the shift. Since some moves are of
     * form 'can move any distance in a specific direction', these are represented
     * using a single-element array that holds a MovementType enum's value.
     */
    private Board board;
    private Tile place;
    private List<int[]> validMoves;
    private List<int[]> selectedMoves;
    private PieceType type;
    private final Color team;

    public Piece(Color c, PieceType type, Board board) {
        this.team = c;
        this.type = type;
        this.board = board;
        validMoves = type.getNormalMoves();
    }

    public void showMoves() {
        int x = place.getX(), y = place.getY();
        Tile[][] relPositions = null; // TODO use method Bo creates to transform board into this based on color
        selectedMoves = type.getConditionalMoves(relPositions, x, y);
        selectedMoves.addAll(validMoves);
        selectedMoves.stream().flatMap(e -> {
            if (e.length == 2)
                return Stream.of(e);

            List<int[]> extendingMoves = walkAlong(relPositions, PieceType.MovementType.fromValue(e[0]), x, y);
            return extendingMoves.stream();

        }).forEach(arr -> {
            if (arr.length == 2) {
                board.tiles[x][y].setPossible(true);
                return;
            }

        });

    }

    private List<int[]> walkAlong(Tile[][] board, PieceType.MovementType direction, int xStart, int yStart) {
        List<int[]> validValues = new ArrayList<>();

        int xPos = xStart + direction.getxShift(),
                yPos = yStart + direction.getyShift();

        Tile nextStep = board[xPos][yPos];

        while (nextStep.getCurrentPiece().isEmpty()) {
            int pos[] = { xPos, yPos };
            validValues.add(pos);

            xPos += direction.getxShift();
            yPos += direction.getyShift();
            nextStep = board[xPos][yPos];
        }
        int endPos[] = { xPos, yPos };
        validValues.add(endPos);

        xPos = xStart - direction.getxShift();
        yPos = yStart - direction.getyShift();
        nextStep = board[xPos][yPos];

        while (nextStep.getCurrentPiece().isEmpty()) {
            int pos[] = { xPos, yPos };
            validValues.add(pos);

            xPos -= direction.getxShift();
            yPos -= direction.getyShift();
            nextStep = board[xPos][yPos];
        }
        int otherPos[] = { xPos, yPos };
        validValues.add(otherPos);

        return validValues;
    }

    public void clearSelected() {
    }

    public void moveTo(Tile t) {

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

    public void setPlace(Tile place) {
        this.place = place;
    }

    public Tile getPlace() {
        return place;
    }
}