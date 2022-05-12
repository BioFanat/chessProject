import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public String name() {
        return "Piece";
    }
}

class Pawn extends PieceType {
    @Override
    public List<int[]> getNormalMoves() {
        return new ArrayList<>();
    }

    @Override
    public List<int[]> getConditionalMoves(Tile[][] grid, int x, int y) {
        ArrayList<int[]> moves = new ArrayList<>();
        if (y == 3) {
            if (x > 0 && grid[x - 1][y].getCurrentPiece().isPresent()) {
                Piece p = grid[x - 1][y].getCurrentPiece().get();
                if (p.getType().name() == "Pawn"
                        && p.getTeam() != grid[x][y].getCurrentPiece().get().getTeam()
                        && grid[x - 1][y - 1].getCurrentPiece().isEmpty()) {
                    int[] pos = { x - 1, y - 1 };
                    moves.add(pos);
                }

            }
            System.out.println();
            if (x < 7 && grid[x + 1][y].getCurrentPiece().isPresent()) {
                Piece p = grid[x + 1][y].getCurrentPiece().get();
                if (p.getType().name() == "Pawn"
                        && p.getTeam() != grid[x][y].getCurrentPiece().get().getTeam()
                        && grid[x + 1][y - 1].getCurrentPiece().isEmpty()) {
                    int[] pos = { x + 1, y - 1 };
                    moves.add(pos);
                }

            }
        }
        if (x > 0 && y > 0 && grid[x - 1][y - 1].getCurrentPiece().isPresent()) {
            int[] pos = { x - 1, y - 1 };
            moves.add(pos);
        }
        if (x < 7 && y > 0 && grid[x + 1][y - 1].getCurrentPiece().isPresent()) {
            int[] pos = { x + 1, y - 1 };
            moves.add(pos);
        }

        if (moveCount == 0) {
            int[] pos = { x, y - 2 };
            moves.add(pos);
        }

        if (y > 0 && grid[x][y - 1].getCurrentPiece().isEmpty()) {
            int[] pos = { x, y - 1 };
            moves.add(pos);
        }

        return moves;
    }

    @Override
    public String name() {
        return "Pawn";
    }

}

class Rook extends PieceType {
    @Override
    public List<int[]> getNormalMoves() {
        int[][] moves = { { MovementType.HORIZONTAL.getValue() }, { MovementType.VERTICAL.getValue() } };
        return Arrays.asList(moves);
    }

    @Override
    public String name() {
        return "Rook";
    }
}

class Knight extends PieceType {
    @Override
    public List<int[]> getNormalMoves() {
        int[][] moves = { { 2, 1 }, { 2, -1 }, { 1, 2 }, { 1, -2 }, { -1, 2 }, { -1, -2 }, { -2, 1 }, { -2, -1 } };
        return Arrays.asList(moves);
    }

    @Override
    public String name() {
        return "Knight";
    }
}

class Bishop extends PieceType {
    @Override
    public List<int[]> getNormalMoves() {
        int[][] moves = { { MovementType.DIAG_DECREASING.getValue() },
                { MovementType.DIAG_INCREASING.getValue() } };
        return Arrays.asList(moves);
    }

    @Override
    public String name() {
        return "Bishop";
    }
}

class Queen extends PieceType {
    @Override
    public List<int[]> getNormalMoves() {
        int[][] moves = { { MovementType.HORIZONTAL.getValue() }, { MovementType.VERTICAL.getValue() },
                { MovementType.DIAG_INCREASING.getValue() }, { MovementType.DIAG_DECREASING.getValue() } };
        return Arrays.asList(moves);
    }

    @Override
    public String name() {
        return "Queen";
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
        List<int[]> moves = new ArrayList<>();

        if (moveCount == 0) {
            if (grid[0][y].currentPiece.isPresent() && grid[0][y].currentPiece.get().getType().name() == "Rook") {
                boolean openPath = true;
                for (int i = 1; i < x; i++) {
                    openPath = openPath && grid[i][y].currentPiece.isPresent();

                }
                if (openPath) {
                    // TODO: check if moving would put in check
                    int pos[] = { 0, 0 };
                    moves.add(pos);
                }
            }
            if (grid[7][y].currentPiece.isPresent() && grid[7][y].currentPiece.get().getType().name() == "Rook") {
                boolean openPath = true;
                for (int i = 6; i > x; i--) {
                    openPath = openPath && grid[i][y].currentPiece.isPresent();
                }
                if (openPath) {
                    // TODO: check if moving would put in check
                    int pos[] = { 7, 0 };
                    moves.add(pos);
                }
            }

        }
        return moves;
    }

    @Override
    public String name() {
        return "King";
    }
}