import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

enum PieceType {
    // DONE
    PAWN {
        @Override
        public List<int[]> getNormalMoves() {
            int[][] move = { { 1, 0 } };
            return Arrays.asList(move);
        }

        @Override
        public List<int[]> getConditionalMoves(Tile[][] grid, int x, int y) {
            ArrayList<int[]> moves = new ArrayList<>();

            if (y == 4) {
                if (x > 0 && grid[x - 1][y].getCurrentPiece().isPresent()) {
                    Piece p = grid[x - 1][y].getCurrentPiece().get();
                    if (p.getType() == PAWN && p.getTeam() != grid[x][y].getCurrentPiece().get().getTeam()
                            && grid[x - 1][y + 1].getCurrentPiece().isEmpty()) {
                        int[] pos = { x - 1, y + 1 };
                        moves.add(pos);
                    }

                }
                if (x < 7 && grid[x + 1][y].getCurrentPiece().isPresent()) {
                    Piece p = grid[x + 1][y].getCurrentPiece().get();
                    if (p.getType() == PAWN && p.getTeam() != grid[x][y].getCurrentPiece().get().getTeam()
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

    },

    // DONE
    ROOK {

        @Override
        public List<int[]> getNormalMoves() {
            int[][] move = { { MovementType.HORIZONTAL.getValue() }, { MovementType.VERTICAL.getValue() } };
            return Arrays.asList(move);
        }
    },

    // DONE
    KNIGHT {
        @Override
        public List<int[]> getNormalMoves() {
            int[][] move = { { 2, 1 }, { 2, -1 }, { 1, 2 }, { 1, -2 }, { -1, 2 }, { -1, -2 }, { -2, 1 }, { -2, -1 } };
            return Arrays.asList(move);
        }
    },

    // DONE
    BISHOP {
        @Override
        public List<int[]> getNormalMoves() {
            int[][] move = { { MovementType.DIAG_DECREASING.getValue() }, { MovementType.DIAG_INCREASING.getValue() } };
            return Arrays.asList(move);
        }
    },

    // DONE
    QUEEN {
        @Override
        public List<int[]> getNormalMoves() {
            int[][] move = { { MovementType.HORIZONTAL.getValue() }, { MovementType.VERTICAL.getValue() },
                    { MovementType.DIAG_INCREASING.getValue() }, { MovementType.DIAG_DECREASING.getValue() } };
            return Arrays.asList(move);
        }
    },

    KING {
        @Override
        public List<int[]> getNormalMoves() {
            int[][] move = { { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, 1 }, { 0, -1 }, { -1, 1 }, { -1, 0 }, { -1, -1 } };
            return Arrays.asList(move);
        }
    };

    public List<int[]> getNormalMoves() {
        return new ArrayList<>();
    }

    public List<int[]> getConditionalMoves(Tile[][] grid, int x, int y) {
        return new ArrayList<>();
    }

    public String getImageLocation() {
        return "";
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
