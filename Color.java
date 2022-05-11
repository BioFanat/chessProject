public enum Color {
    BLACK {
        @Override
        public Tile[][] getRelativeGrid(Board b) {
            return b.flip(b.tiles);
        }

        @Override
        public int translateY(int initial) {
            return 7 - initial;
        }
    },
    WHITE;

    public Tile[][] getRelativeGrid(Board b) {
        return b.tiles;
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
};
