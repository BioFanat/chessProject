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

    public String getLightImagePath() {
        return "";
    }

    public String getDarkImagePath() {
        return "";
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
            if (x > 0 && Tile.hasPiece(grid[x - 1][y], Pawn.class) && Tile.diffTeam(grid[x - 1][y], grid[x][y])) {
                int[] pos = { x - 1, y - 1 };
                moves.add(pos);
                // }

            }
            if (x < 7 && Tile.hasPiece(grid[x + 1][y], Pawn.class) && Tile.diffTeam(grid[x + 1][y], grid[x][y])) {
                int[] pos = { x + 1, y - 1 };
                moves.add(pos);
                // }
            }
        }
        if (x > 0 && y > 0 && Tile.diffTeam(grid[x][y], grid[x - 1][y - 1])) {
            int[] pos = { x - 1, y - 1 };
            moves.add(pos);
        }
        if (x < 7 && y > 0 && Tile.diffTeam(grid[x][y], grid[x + 1][y - 1])) {
            int[] pos = { x + 1, y - 1 };
            moves.add(pos);
        }

        if (moveCount == 0 && grid[x][y - 2].getCurrentPiece().isEmpty()) {
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

    @Override
    public String getLightImagePath() {
        return "images/Chess_plt60.png";
    }

    @Override
    public String getDarkImagePath() {
        return "images/Chess_pdt60.png";
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

    @Override
    public String getLightImagePath() {
        return "images/Chess_rlt60.png";
    }

    @Override
    public String getDarkImagePath() {
        return "images/Chess_rdt60.png";
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

    @Override
    public String getLightImagePath() {
        return "images/Chess_nlt60.png";
    }

    @Override
    public String getDarkImagePath() {
        return "images/Chess_ndt60.png";
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

    @Override
    public String getLightImagePath() {
        return "images/Chess_blt60.png";
    }

    @Override
    public String getDarkImagePath() {
        return "images/Chess_bdt60.png";
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

    @Override
    public String getLightImagePath() {
        return "images/Chess_qlt60.png";
    }

    @Override
    public String getDarkImagePath() {
        return "images/Chess_qdt60.png";
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

        boolean[][] unsafeSpots = King.spotsInCheck(grid, grid[x][y].currentPiece.get().getTeam());

        if (moveCount == 0) {
            if (Tile.hasPiece(grid[0][y], Rook.class)) {
                boolean openPath = true;
                for (int i = 1; i < x; i++) {
                    openPath = openPath && grid[i][y].currentPiece.isEmpty() && !unsafeSpots[i][y];

                }
                if (openPath) {
                    int pos[] = { 0, y };
                    moves.add(pos);
                }
            }
            if (Tile.hasPiece(grid[7][y], Rook.class)) {
                boolean openPath = true;
                for (int i = 6; i > x; i--) {
                    openPath = openPath && grid[i][y].currentPiece.isEmpty() && !unsafeSpots[i][y];
                }
                if (openPath) {
                    int pos[] = { 7, y };
                    moves.add(pos);
                }
            }

        }
        return moves;
    }

    public static boolean[][] spotsInCheck(Tile[][] board, Color target) {
        boolean inCheck[][] = new boolean[8][8]; // default false; will turn it true if can be reached by tile of
                                                 // opposite color
        for (Tile[] row : board) {
            for (Tile tile : row) {
                if (tile.currentPiece.isEmpty() || tile.currentPiece.get().getTeam() == target
                        || tile.currentPiece.get().getType().name() == "King")
                    continue;

                tile.currentPiece.get()
                        .getPossMoves(target.opposite().getRelativeGrid(board), tile.getX(),
                                target.opposite().translateY(tile.getY()))
                        .forEach(arr -> {
                            inCheck[arr[0]][arr[1]] = true;
                        });

            }
        }
        return inCheck;
    }

    private static boolean canPreventCheck;

    public static boolean inCheckMate(Tile[][] board, Color checked) {
        for (Tile[] row : board) {
            for (Tile tile : row) {
                if (tile.currentPiece.isEmpty() || tile.currentPiece.get().getTeam() != checked)
                    continue;
                canPreventCheck = false;
                tile.currentPiece.get()
                        .getPossMoves(checked.getRelativeGrid(board), tile.getX(), checked.translateY(tile.getY()))
                        .forEach(arr -> {
                            Tile[][] outcome = tile.currentPiece.get().supposeMoveTo(arr[0], arr[1]);
                            Tile kingTile = checked.getKing().getTile();
                            int kingX = kingTile.getX(), kingY = kingTile.getY();
                            if (tile.currentPiece.get().getType().getClass() == King.class) {
                                kingX = arr[0];
                                kingY = arr[1];
                            }
                            if (!King.spotsInCheck(outcome, checked)[kingX][kingY])
                                canPreventCheck = true;
                        });
                if (canPreventCheck) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String name() {
        return "King";
    }

    @Override
    public String getLightImagePath() {
        return "images/Chess_klt60.png";
    }

    @Override
    public String getDarkImagePath() {
        return "images/Chess_kdt60.png";
    }
}