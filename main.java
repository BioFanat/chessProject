
/**************************************************
*   Author: Chi
*   Date:  03 May 22
**************************************************/
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

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
        Board game = new Board(gamePiece);
        bp.setCenter(gamePiece);
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

    private void swap(Tile[][] board, Tile t1, Tile t2){
        
        int x1 = t1.getX();
        int y1 = t1.getY();
        int x2 = t2.getX();
        int y2 = t2.getY();
        board[x1][y1] = t2;
        board[x2][y2] = t1;

    }

    private Tile[][] flip(Tile[][] board){
        
        Tile[][] newArr = java.util.Arrays.stream(board).map(el -> el.clone()).toArray($ -> board.clone());
        
        for (int x = 0; x < board.length; x++){
            for (int y = 0; y < board.length / 2; y++){
                swap(newArr, newArr[x][y], newArr[x][(board.length-1)-y]);
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
    BLACK, WHITE
};

class Tile extends Button {

    private Color color;
    private boolean isPossible;
    private int x, y;
    Optional<PieceObjectProperty> currentPiece;
    // TODO: Add Contains Piece

    public Tile(Color color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
        currentPiece = Optional.empty();
        setOnAction(e -> {
            if (isPossible) {

            }

            if (currentPiece.isPresent()){
                
            }

            // TODO: call current piece's show moves
        });
        //System.out.println("-fx-background-color:" + color.toString().toLowerCase());
        setStyle("-fx-background-color:" + color.toString().toLowerCase());
    }

    public Optional<PieceObjectProperty> getCurrentPiece() {
        return currentPiece;
    }

    public void setCurrentPiece(Optional<PieceObjectProperty> piece) {
        currentPiece = piece;

    }

    public Color getColor() {
        return color;
    }

    public boolean isPossible() {
        return isPossible;
    }

    public void setPossible(boolean isPossible) {
        this.isPossible = isPossible;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    class PieceObjectProperty extends SimpleObjectProperty<Piece> {
        @Override
        public Piece get() {
            return super.get();
        }

        @Override
        public void set(Piece value) {
            super.set(value);
            value.setPlace(Tile.this);
        }
    }

}
