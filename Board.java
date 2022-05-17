import javafx.scene.layout.GridPane;

public class Board {
    Tile[][] tiles = new Tile[8][8];
    Color current = Color.WHITE;
    Color turn = Color.WHITE;
    Color start = Color.WHITE;
    Tile currentlySelected;
    GridPane display;

    public Board(GridPane display) {
        this.display = display;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tiles[i][j] = new Tile(current, i, j, this);
                if (current == Color.WHITE) {
                    current = Color.BLACK;
                } else {
                    current = Color.WHITE;
                }
                display.add(tiles[i][j], i, j);

            }
            if (current == Color.WHITE) {
                current = Color.BLACK;
            } else {
                current = Color.WHITE;
            }
        }

    }

    public void setCurrentTile(Tile selected) {
        currentlySelected = selected;
    }

    public Tile getCurrentlySelected() {
        return currentlySelected;
    }

    private static void swap(Tile[][] board, Tile t1, Tile t2) {
        int x1 = t1.getX();
        int y1 = t1.getY();
        int x2 = t2.getX();
        int y2 = t2.getY();
        board[x1][y1] = t2;
        board[x2][y2] = t1;

    }

    public void printGrid(Tile[][] board) {
        for (int i = 0; i < board[0].length; i++) {
            for (Tile[] tiles : board) {
                System.out.print(tiles[i] + "\t");
            }
            System.out.println();
        }
    }

    protected static Tile[][] flip(Tile[][] board) {

        Tile[][] newArr = java.util.Arrays.stream(board).map(el -> el.clone()).toArray($ -> board.clone());

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length / 2; y++) {
                swap(newArr, newArr[x][y], newArr[x][(board.length - 1) - y]);
            }
        }

        return newArr;

    }

    // Probably deprecated: ShowMoves is replaced with toggleMoves, which handles
    // this for us
    public void clearGrey() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tiles[i][j].isPossible()) {
                    tiles[i][j].setPossible(false);
                }
            }
        }
    }
}
