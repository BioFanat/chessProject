/**************************************************
*   Author: Chi
*   Date:  03 May 22
**************************************************/
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
public class main extends Application
{
    enum Color{BLACK,WHITE};

    public main()
    {
        
    }

    @Override
    public void init()
    {
    }

    @Override
    public void start(Stage primary)
    {
        
        BorderPane bp = new BorderPane();
        GridPane gamePiece = new GridPane();
        Board game = new Board(gamePiece);
        bp.setCenter(gamePiece);
        Scene main = new Scene(bp, 800, 500);
        primary.setScene(main);
        primary.show();
    }

    @Override
    public void stop()
    {

    }

    class Board {
        Tile[][] tiles = new Tile[8][8];
        Color start = Color.WHITE;
        Color current = start;
        Color turn = Color.WHITE;
        GridPane display;

        public Board(GridPane display){
            this.display = display; 
            for (int i = 0; i < 8; i++){
                for (int j = 0; j < 8; j++){
                    tiles[i][j] = new Tile(current, i, j);
                    if (current == Color.WHITE) {
                        current = Color.BLACK;
                    }
                    else {
                        current = Color.WHITE;
                    }
                    display.add(tiles[i][j], i, j);

                }
                if (current == Color.WHITE) {
                    current = Color.BLACK;
                }
                else {
                    current = Color.WHITE;
                }
            }
        }

        public void clearGrey(){
            for (int i = 0; i < 8; i++){
                for (int j = 0; j < 8; j++) {
                    if (tiles[i][j].isPossible){
                        tiles[i][j].isPossible = false;
                    }
                }
            }
        }
    }


    class Tile extends Button{
        
        private Color color;
        private boolean isPossible;
        private int x, y;
        Optional<SimpleObjectProperty<Piece>> currentPiece;
        //TODO: Add Contains Piece
        
        public Tile(Color color, int x, int y){
            this.color = color;
            this.x = x;
            this.y=y;
            currentPiece = Optional.empty();
            setOnAction(e -> {
                if (isPossible){
                    
                }

               //TODO: call current piece's show moves 
            });
            System.out.println("-fx-background-color:" + color.toString().toLowerCase());
            setStyle("-fx-background-color:" + color.toString().toLowerCase());
        }

        public Optional<SimpleObjectProperty<Piece>> getCurrentPiece(){
            return currentPiece;
        }

        public void setCurrentPiece(SimpleObjectProperty<Piece> piece){
            currentPiece = Optional.of(piece);
        }
        
    }

}
