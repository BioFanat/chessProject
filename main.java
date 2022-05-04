
/**************************************************
*   Author: Chi
*   Date:  03 May 22
**************************************************/
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ObjectPropertyBase;
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

            // TODO: call current piece's show moves
        });
        System.out.println("-fx-background-color:" + color.toString().toLowerCase());
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
