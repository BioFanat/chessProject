import java.util.Optional;

import javafx.scene.control.Button;

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
        setStyle("-fx-background-color:" + color.toString().toLowerCase());
    }

    public Optional<Piece> getCurrentPiece() {
        return currentPiece;
    }

    public void setCurrentPiece(Optional<Piece> piece) {
        currentPiece = piece;
        if (piece.isPresent()) {
            piece.get().setTile(this);
            setText(piece.get().getType().getClass().getName());
        } else {
            setText("");
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
        return (currentPiece.isPresent() ? currentPiece.get().getType().getClass().getName() : "empty") + " " + getX()
                + " " + getY();
    }
}
