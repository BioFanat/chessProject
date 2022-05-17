
/**************************************************
*   Author: Chi
*   Date:  03 May 22
**************************************************/
import java.util.Optional;

import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;

public class Main extends Application {

    Board game;

    public Main() {

    }

    @Override
    public void init() {
    }

    @Override
    public void start(Stage primary) {
        BorderPane bp = new BorderPane();
        HBox hb = new HBox();
        GridPane gamePiece = new GridPane();
        hb.getChildren().add(gamePiece);
        for (int i = 0; i < 8; i++) {
            ColumnConstraints colConstraint = new ColumnConstraints();
            colConstraint.setPercentWidth(100 / 8.0);
            colConstraint.setMinWidth(Double.MIN_VALUE);
            colConstraint.setMaxWidth(Screen.getPrimary().getBounds().getWidth() - 250);
            colConstraint.setFillWidth(true);
            gamePiece.getColumnConstraints().add(colConstraint);

            RowConstraints rowConstraint = new RowConstraints();
            rowConstraint.setPercentHeight(100 / 8.0);
            rowConstraint.setMinHeight(Double.MIN_VALUE);
            rowConstraint.setMaxHeight(Screen.getPrimary().getBounds().getHeight() - 250);
            rowConstraint.setFillHeight(true);
            gamePiece.getRowConstraints().add(rowConstraint);
        }

        gamePiece.setGridLinesVisible(true);

        game = new Board(gamePiece);
        bp.setCenter(gamePiece);

        initializeWhite();
        initializeBlack();

        // game.tiles[7][5]
        // .setCurrentPiece(Optional.of(new Piece(Color.BLACK, new Rook(), game)));
        // game.tiles[3][5]
        // .setCurrentPiece(Optional.of(new Piece(Color.BLACK, new Bishop(), game)));
        // game.tiles[0][5]
        // .setCurrentPiece(Optional.of(new Piece(Color.WHITE, new Rook(), game)));
        Scene main = new Scene(bp, Screen.getPrimary().getBounds().getHeight() * .75,
                Screen.getPrimary().getBounds().getHeight() * .75);

        primary.setMinHeight(Screen.getPrimary().getBounds().getHeight() * .6);
        primary.setMinWidth(Screen.getPrimary().getBounds().getWidth() * .5);

        primary.setScene(main);
        primary.show();

    }

    public void initializeWhite() {
        // Pawns
        for (int i = 0; i < 8; i++) {
            game.tiles[i][6].setCurrentPiece(Optional.of(new Piece(Color.WHITE, new Pawn(), game)));
        }

        // Rooks
        game.tiles[0][7].setCurrentPiece(Optional.of(new Piece(Color.WHITE, new Rook(), game)));
        game.tiles[7][7].setCurrentPiece(Optional.of(new Piece(Color.WHITE, new Rook(), game)));
        // Knights
        game.tiles[1][7].setCurrentPiece(Optional.of(new Piece(Color.WHITE, new Knight(), game)));
        game.tiles[6][7].setCurrentPiece(Optional.of(new Piece(Color.WHITE, new Knight(), game)));
        // Bishops
        game.tiles[2][7].setCurrentPiece(Optional.of(new Piece(Color.WHITE, new Bishop(), game)));
        game.tiles[5][7].setCurrentPiece(Optional.of(new Piece(Color.WHITE, new Bishop(), game)));
        // Royalty
        game.tiles[3][7].setCurrentPiece(Optional.of(new Piece(Color.WHITE, new Queen(), game)));
        game.tiles[4][7].setCurrentPiece(Optional.of(new Piece(Color.WHITE, new King(), game)));

        Color.WHITE.setKing(game.tiles[4][7].currentPiece.get());

    }

    public void initializeBlack() {
        // Pawns
        for (int i = 0; i < 8; i++) {
            game.tiles[i][1].setCurrentPiece(Optional.of(new Piece(Color.BLACK, new Pawn(), game)));
        }

        // Rooks
        game.tiles[0][0].setCurrentPiece(Optional.of(new Piece(Color.BLACK, new Rook(), game)));
        game.tiles[7][0].setCurrentPiece(Optional.of(new Piece(Color.BLACK, new Rook(), game)));
        // Knights
        game.tiles[1][0].setCurrentPiece(Optional.of(new Piece(Color.BLACK, new Knight(), game)));
        game.tiles[6][0].setCurrentPiece(Optional.of(new Piece(Color.BLACK, new Knight(), game)));
        // Bishops
        game.tiles[2][0].setCurrentPiece(Optional.of(new Piece(Color.BLACK, new Bishop(), game)));
        game.tiles[5][0].setCurrentPiece(Optional.of(new Piece(Color.BLACK, new Bishop(), game)));
        // Royalty
        game.tiles[3][0].setCurrentPiece(Optional.of(new Piece(Color.BLACK, new Queen(), game)));
        game.tiles[4][0].setCurrentPiece(Optional.of(new Piece(Color.BLACK, new King(), game)));

        Color.BLACK.setKing(game.tiles[4][0].currentPiece.get());

    }

    @Override
    public void stop() {
    }

    public static void main(String[] args) {
        launch(args);
    }
}
