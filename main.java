
/**************************************************
*   Author: Chi
*   Date:  03 May 22
**************************************************/
import java.util.Optional;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class Main extends Application {

    public Main() {

    }

    @Override
    public void init() {
    }

    @Override
    public void start(Stage primary) {
        BorderPane bp = new BorderPane();
        GridPane gamePiece = new GridPane();
        for (int i = 0; i < 8; i++) {
            ColumnConstraints colConstraint = new ColumnConstraints();
            colConstraint.setPercentWidth(100 / 8.0);
            colConstraint.setFillWidth(true);
            gamePiece.getColumnConstraints().add(colConstraint);

            RowConstraints rowConstraint = new RowConstraints();
            rowConstraint.setPercentHeight(100 / 8.0);
            rowConstraint.setFillHeight(true);
            gamePiece.getRowConstraints().add(rowConstraint);
        }
        gamePiece.setGridLinesVisible(true);

        Board game = new Board(gamePiece);
        bp.setCenter(gamePiece);

        game.tiles[7][0]
                .setCurrentPiece(Optional.of(new Piece(Color.BLACK, PieceType.ROOK, game)));
        game.tiles[0][5]
                .setCurrentPiece(Optional.of(new Piece(Color.WHITE, PieceType.ROOK, game)));
        Scene main = new Scene(bp, 800, 500);
        primary.setScene(main);
        primary.show();
    }

    @Override
    public void stop() {
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class Board {
    Tile[][] tiles = new Tile[8][8];
    Color current = Color.WHITE;
    Color turn = Color.WHITE;
    Color start = Color.WHITE;

    GridPane display;

    public Board(GridPane display) {
        this.display = display;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tiles[i][j] = new Tile(current, i, j);
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
        System.out.println(tiles[1][2].getX() + " " + tiles[1][2].getY());
        Tile[][] copy = flip(tiles);
        System.out.println(copy[1][2].getX() + " " + copy[1][2].getY());

    }

    private void swap(Tile[][] board, Tile t1, Tile t2) {
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

    protected Tile[][] flip(Tile[][] board) {

        Tile[][] newArr = java.util.Arrays.stream(board).map(el -> el.clone()).toArray($ -> board.clone());

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length / 2; y++) {
                swap(newArr, newArr[x][y], newArr[x][(board.length - 1) - y]);
            }
        }

        return newArr;

    }

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

enum Color {
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
};

class Tile extends Button {

    private Color color;
    private boolean isPossible;
    private int x, y;
    Optional<Piece> currentPiece;
    // TODO: Add Contains Piece

    public Tile(Color color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
        currentPiece = Optional.empty();
        setOnAction(e -> {
            if (isPossible) {

            }

            if (currentPiece.isPresent()) {
                currentPiece.get().toggleMoves();
            }

        });
        setMaxHeight(Double.MAX_VALUE);
        setMaxWidth(Double.MAX_VALUE);
        setStyle("-fx-background-color:" + color.toString().toLowerCase());
    }

    public Optional<Piece> getCurrentPiece() {
        return currentPiece;
    }

    public void setCurrentPiece(Optional<Piece> piece) {
        currentPiece = piece;
        if (piece.isPresent())
            piece.get().setPlace(this);

        setText(piece.get().getType().name());
    }

    public Color getColor() {
        return color;
    }

    public boolean isPossible() {
        return isPossible;
    }

    public void setPossible(boolean isPossible) {
        this.isPossible = isPossible;
        if (isPossible == true) {

            setStyle("-fx-background-color:BLUE");
        } else {

            setStyle("-fx-background-color:" + color.toString().toLowerCase());
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return (currentPiece.isPresent() ? currentPiece.get().getType().name() : "empty") + " " + getX() + " " + getY();
    }
}
