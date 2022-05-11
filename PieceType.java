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
        int[][] moves = { { 0, -1 } };
        return Arrays.asList(moves);
    }

    @Override
    public List<int[]> getConditionalMoves(Tile[][] grid, int x, int y) {
        ArrayList<int[]> moves = new ArrayList<>();
        System.out.println("test1 " + y);
        if (y == 3) {
            System.out.println("test2");
            if (x > 0 && grid[x - 1][y].getCurrentPiece().isPresent()) {
                Piece p = grid[x - 1][y].getCurrentPiece().get();
                System.out.println("test3");
                if (p.getType().name() == "Pawn"
                        && p.getTeam() != grid[x][y].getCurrentPiece().get().getTeam()
                        && grid[x - 1][y - 1].getCurrentPiece().isEmpty()) {
                    System.out.println("test4");
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

        return moves;
    }

    @Override
    public String name() {
        return "Pawn";
    }

    @Override
    public String getLightImagePath(){
        return "images/Chess_plt60.png";
    }

    @Override
    public String getDarkImagePath(){
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
    public String getLightImagePath(){
        return "images/Chess_rlt60.png";
    }

    @Override
    public String getDarkImagePath(){
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
    public String getLightImagePath(){
        return "images/Chess_nlt60.png";
    }

    @Override
    public String getDarkImagePath(){
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
    public String getLightImagePath(){
        return "images/Chess_blt60.png";
    }

    @Override
    public String getDarkImagePath(){
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
    public String getLightImagePath(){
        return "images/Chess_qlt60.png";
    }

    @Override
    public String getDarkImagePath(){
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

        if (moveCount == 0) {
            if (grid[0][0].currentPiece.isPresent() && grid[0][0].currentPiece.get().getType().name() == "Rook") {
                boolean openPath = true;
                for (int i = 1; i < x; i++) {
                    openPath = openPath && grid[i][0].currentPiece.isPresent();
                    if (openPath) {
                        // TODO: check if moving would put in castle
                        int pos[] = { 0, 0 };
                        moves.add(pos);
                    }
                }
            }
            if (grid[7][0].currentPiece.isPresent() && grid[7][0].currentPiece.get().getType().name() == "Rook") {
                boolean openPath = true;
                for (int i = 6; i > x; i--) {
                    openPath = openPath && grid[i][0].currentPiece.isPresent();
                    if (openPath) {
                        // TODO: check if moving would put in castle
                        int pos[] = { 7, 0 };
                        moves.add(pos);
                    }
                }
            }

        }
        return moves;
    }

    @Override
    public String name() {
        return "King";
    }

    @Override
    public String getLightImagePath(){
        return "images/Chess_klt60.png";
    }

    @Override
    public String getDarkImagePath(){
        return "images/Chess_kdt60.png";
    }
}