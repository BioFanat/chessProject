public enum Color {
    BLACK {
        @Override
        public Tile[][] getRelativeGrid(Tile[][] b) {
            return Board.flip(b);
        }

        @Override
        public int translateY(int initial) {
            return 7 - initial;
        }
    },
    WHITE;

    private boolean inCheck = false;
    // initialized when board is initialized
    private Piece king;

    public Tile[][] getRelativeGrid(Tile[][] b) {
        return b;
    }

    public int translateY(int initial) {
        return initial;
    }

    public Color opposite() {
        if (this == BLACK) {
            return WHITE;
        } else
            return BLACK;
    }

    public void setInCheck(boolean inCheck) {
        this.inCheck = inCheck;
    }

    public boolean isInCheck() {
        return inCheck;
    }

    public void setKing(Piece k) {
        this.king = k;
    }

    public Piece getKing() {
        return king;
    }
};
