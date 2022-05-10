import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class PieceType {
    protected int moveCount = 0;

    public List<int[]> getNormalMoves() {
        return new ArrayList<>();
    }

    public List<int[]> getConditionalMoves(Tile[][] grid, int x, int y) {
        return new ArrayList<>();
    }

    public String getImageLocation() {
        return "";
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void addMove() {
        moveCount++;
    }
}

class Pawn extends PieceType {
    @Override
    public List<int[]> getNormalMoves() {
        int[][] moves = { { 1, 0 } };
        return Arrays.asList(moves);
    }

    @Override
    public List<int[]> getConditionalMoves(Tile[][] grid, int x, int y) {
        ArrayList<int[]> moves = new ArrayList<>();

        if (y == 4) {
            if (x > 0 && grid[x - 1][y].getCurrentPiece().isPresent()) {
                Piece p = grid[x - 1][y].getCurrentPiece().get();
                if (Piece.class.isAssignableFrom(p.getType().getClass())
                        && p.getTeam() != grid[x][y].getCurrentPiece().get().getTeam()
                        && grid[x - 1][y + 1].getCurrentPiece().isEmpty()) {
                    int[] pos = { x - 1, y + 1 };
                    moves.add(pos);
                }

            }
            if (x < 7 && grid[x + 1][y].getCurrentPiece().isPresent()) {
                Piece p = grid[x + 1][y].getCurrentPiece().get();
                if (Piece.class.isAssignableFrom(p.getType().getClass())
                        && p.getTeam() != grid[x][y].getCurrentPiece().get().getTeam()
                        && grid[x + 1][y + 1].getCurrentPiece().isEmpty()) {
                    int[] pos = { x + 1, y + 1 };
                    moves.add(pos);
                }

            }
        }
        if (x > 0 && y < 7 && grid[x - 1][y + 1].getCurrentPiece().isPresent()) {
            int[] pos = { x - 1, y + 1 };
            moves.add(pos);
        }
        if (x < 7 && y < 7 && grid[x + 1][y + 1].getCurrentPiece().isPresent()) {
            int[] pos = { x + 1, y + 1 };
            moves.add(pos);
        }

        return moves;
    }

}

class Rook extends PieceType {

    @Override
    public List<int[]> getNormalMoves() {
        int[][] moves = { { MovementType.HORIZONTAL.getValue() }, { MovementType.VERTICAL.getValue() } };
        return Arrays.asList(moves);
    }
}

class Knight extends PieceType {
    @Override
    public List<int[]> getNormalMoves() {
        int[][] moves = { { 2, 1 }, { 2, -1 }, { 1, 2 }, { 1, -2 }, { -1, 2 }, { -1, -2 }, { -2, 1 }, { -2, -1 } };
        return Arrays.asList(moves);
    }
}

class Bishop extends PieceType {
    @Override
    public List<int[]> getNormalMoves() {
        int[][] moves = { { MovementType.DIAG_DECREASING.getValue() },
                { MovementType.DIAG_INCREASING.getValue() } };
        return Arrays.asList(moves);
    }
}

class Queen extends PieceType {
    @Override
    public List<int[]> getNormalMoves() {
        int[][] moves = { { MovementType.HORIZONTAL.getValue() }, { MovementType.VERTICAL.getValue() },
                { MovementType.DIAG_INCREASING.getValue() }, { MovementType.DIAG_DECREASING.getValue() } };
        return Arrays.asList(moves);
    }
}

class King extends PieceType {

    @Override
    public List<int[]> getNormalMoves() {
        int[][] moves = { { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, 1 }, { 0, -1 }, { -1, 1 }, { -1, 0 }, { -1, -1 } };
        return Arrays.asList(moves);
    }

    @Override
    public List<int[]> getConditionalMoves(Tile[][] grid, int x, int y) {
        // if (moveCount > 0 || )
        return super.getConditionalMoves(grid, x, y);
    }
}

enum MovementType {
    HORIZONTAL(1, 1, 0),
    VERTICAL(2, 0, 1),
    DIAG_INCREASING(3, 1, 1),
    DIAG_DECREASING(4, 1, -1);

    private final int value, xShift, yShift;

    private MovementType(int value, int xShift, int yShift) {
        this.value = value;
        this.xShift = xShift;
        this.yShift = yShift;
    }

    public int getValue() {
        return value;
    }

    public int getxShift() {
        return xShift;
    }

    public int getyShift() {
        return yShift;
    }

    public static MovementType fromValue(int value) {
        return Stream.of(MovementType.values()).filter(e -> e.getValue() == value).findFirst().orElse(null);
    }
}