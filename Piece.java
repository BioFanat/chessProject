import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Piece {
    // TODO: Add Board variable to access board when pushed
    private List<int[]> validMoves;
    private PieceType type;
    private final Color team;

    public Piece(Color c, PieceType type) {
        this.team = c;
        this.type = type;
        validMoves = type.getInitialPossMoves();

    }

    public void showMoves() {

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
}

enum PieceType {
    PAWN {
        @Override
        public List<int[]> getInitialPossMoves() {
            int[][] move = { { 1, 0 } };
            return Arrays.asList(move);
        }
    },
    ROOK,
    KNIGHT {
        @Override
        public List<int[]> getInitialPossMoves() {
            int[][] move = { { 1, 0 } };
            return Arrays.asList(move);
        }
    },
    BISHOP,
    QUEEN,
    KING {
        @Override
        public List<int[]> getInitialPossMoves() {
            int[][] move = { { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, 1 }, { 0, -1 }, { -1, 1 }, { -1, 0 }, { -1, -1 } };
            return Arrays.asList(move);
        }
    };

    public List<int[]> getInitialPossMoves() {
        return new ArrayList<>();
    }

    public String getImageLocation() {
        return "";
    }
}