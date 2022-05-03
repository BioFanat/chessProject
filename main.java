
/**************************************************
*   Author: Chi
*   Date:  03 May 22
**************************************************/
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class main extends Application {

    public main()

    {
    }

    @Override
    public void init() {
    }

    @Override
    public void start(Stage primary) {

        primary.show();
    }

    @Override
    public void stop() {
    }
}

class Board {
    Tile[][] tiles = new Tile[8][8];
    Color current = Color.WHITE;
    Color turn = Color.WHITE;

    public Board() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tiles[i][j] = new Tile(current, i, j);
                if (current == Color.WHITE) {
                    current = Color.BLACK;
                } else {
                    current = Color.WHITE;
                }
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
    // TODO: Add Contains Piece

    public Tile(Color color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
        setOnAction(e -> {
            if (isPossible) {

            }

            // TODO: call current piece's show moves
        });
        System.out.println("-fx-background-color:" + color.toString().toLowerCase());
        setStyle("-fx-background-color:" + color.toString().toLowerCase());
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

}
