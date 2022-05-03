import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Piece {
    /*
     * validMoves works by taking in a list of positions of possible moves, stored
     * in an int array of 2 elements, storing the shift. Since some moves are of
     * form 'can move any distance in a specific direction', these are represented
     * using a single-element array.
     *
     * code:
     * '1': any vertical movement
     * '2': any horizontal movement
     * '3':
     */
    private List<int[]> validMoves;
    private PieceType type;
    private final Color team;

    public Piece(Color c, PieceType type, Board board) {
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
    ROOK {
        @Override
        public List<int[]> getInitialPossMoves() {
            int[][] move = {
                    { MovementType.HORIZONTAL.value() },
                    { MovementType.VERTICAL.value() }
            };
            return Arrays.asList(move);
        }
    },
    KNIGHT {
        @Override
        public List<int[]> getInitialPossMoves() {
            int[][] move = { { 1, 0 } };
            return Arrays.asList(move);
        }
    },
    BISHOP {
        @Override
        public List<int[]> getInitialPossMoves() {
            int[][] move = {
                    { MovementType.DIAG_DECREASING.value() },
                    { MovementType.DIAG_INCREASING.value() }
            };
            return Arrays.asList(move);
        }
    },
    QUEEN {
        @Override
        public List<int[]> getInitialPossMoves() {
            int[][] move = {
                    { MovementType.HORIZONTAL.value() },
                    { MovementType.VERTICAL.value() },
                    { MovementType.DIAG_DECREASING.value() },
                    { MovementType.DIAG_INCREASING.value() }
            };
            return Arrays.asList(move);
        }
    },
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

enum MovementType {
    HORIZONTAL(1),
    VERTICAL(2),
    // increasing slope diagonal
    DIAG_INCREASING(3),
    // decreasing slope diagonal
    DIAG_DECREASING(4);

    private final int direction;

    private MovementType(int direction) {
        this.direction = direction;
    }

    public int value() {
        return direction;
    }
}