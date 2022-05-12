import java.util.Optional;

import javafx.scene.control.Button;

public class Tile extends Button {

    private Color color;
    private boolean isPossible = false;
    private int x, y;
    Optional<Piece> currentPiece;

    public Tile(Color color, int x, int y, Board board) {
        this.color = color;
        this.x = x;
        this.y = y;

        currentPiece = Optional.empty();
        setOnAction(e -> {

            if (isPossible) {
                Piece p = board.currentlySelected.currentPiece.get();
                // if taking a piece using a pawn diagonally
                if (p.getType().name() == "Rook" && currentPiece.isPresent()
                        && currentPiece.get().getType().name() == "King"
                        && currentPiece.get().getTeam() == p.getTeam()) {
                    int kingX = 5, rookX = 4;
                    if (board.currentlySelected.getX() == 3) {
                        kingX = 1;
                        rookX = 2;
                    }
                    p.moveTo(board.tiles[kingX][board.currentlySelected.getY()]);
                    currentPiece.get().toggleMoves();
                    currentPiece.get().moveTo(board.tiles[rookX][board.currentlySelected.getY()]);
                    board.current = board.current.opposite();
                    board.currentlySelected = null;
                    return;
                }
                if (p.getType().name() == "Pawn" && this.getX() != board.currentlySelected.getX()
                        && this.getY() != board.currentlySelected.getY()) {
                    if (this.currentPiece.isEmpty()) {
                        board.tiles[this.getX()][board.currentlySelected.getY()].setCurrentPiece(Optional.empty());
                        this.setPossible(false);
                    }
                }
                board.currentlySelected.currentPiece.get().moveTo(this);
                board.current = board.current.opposite();
                board.currentlySelected = null;

            } else if (currentPiece.isPresent()) {
                if (board.current != currentPiece.get().getTeam())
                    return;

                if (board.getCurrentlySelected() != null && board.getCurrentlySelected().currentPiece.isPresent()
                        && !board.getCurrentlySelected().equals(this))
                    board.getCurrentlySelected().currentPiece.get().toggleMoves();
                if (board.currentlySelected != this) {
                    board.setCurrentTile(this);
                } else
                    board.setCurrentTile(null);
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
