import java.util.Optional;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tile extends Button {

    private Color color;
    private boolean isPossible = false;
    private int x, y;
    Optional<Piece> currentPiece;

    // TODO: Add Contains Piece

    public Tile(Color color, int x, int y, Board board) {
        this.color = color;
        this.x = x;
        this.y = y;

        currentPiece = Optional.empty();
        setOnAction(e -> {

            if (isPossible) {
                board.currentlySelected.currentPiece.get().moveTo(this);
                board.current = board.current.opposite();

            } else if (currentPiece.isPresent()) {
                if (board.current != currentPiece.get().getTeam())
                    return;

                if (board.getCurrentlySelected() != null && board.getCurrentlySelected().currentPiece.isPresent()
                        && !board.getCurrentlySelected().equals(this))
                    board.getCurrentlySelected().currentPiece.get().toggleMoves();
                board.setCurrentTile(this);
                currentPiece.get().toggleMoves();
            }

        });
        setMaxHeight(Double.MAX_VALUE);
        setMaxWidth(Double.MAX_VALUE);
        String colorVal = getTileColor(color);

        setStyle("-fx-background-color:" + colorVal + ";-fx-opacity:1");
    }

    private String getTileColor(Color color){
        String colorVal = switch (color.toString()){
            case "BLACK" -> "#A52A2A";
            case "WHITE" -> "#fff8e7";
            default -> "";
        };
        return colorVal;
    }

    public Optional<Piece> getCurrentPiece() {
        return currentPiece;
    }

    public void setCurrentPiece(Optional<Piece> piece) {
        currentPiece = piece;
        if (piece.isPresent()) {
            piece.get().setTile(this);
            Color currentTeam = piece.get().getTeam();
            Image img = new Image(piece.get().getType().getLightImagePath());
            if (currentTeam == Color.BLACK){
                img = new Image(piece.get().getType().getDarkImagePath());
            }
            ImageView view = new ImageView(img);
            view.setFitHeight(80);
            view.setPreserveRatio(true);
            
            setGraphic(view);
        } else {
            setGraphic(null);
        }
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

            setStyle("-fx-background-color:#91b3b5");
        } else {
            String colorVal = getTileColor(color);
            setStyle("-fx-background-color:" + colorVal);
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
        return (currentPiece.isPresent() ? currentPiece.get().getType().getClass().getName() : "empty") + " " + getX()
                + " " + getY();
    }
}
