import java.util.Optional;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
                if (p.getType().name() == "King" && Tile.hasPiece(currentPiece, Rook.class)
                        && currentPiece.get().getTeam() == p.getTeam()) {
                    int kingX = 6, rookX = 5;
                    if (getX() == 0) {
                        kingX = 2;
                        rookX = 3;
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
                // TODO: check if this puts opposite king in check
                this.currentPiece.get().getPossMoves(color.getRelativeGrid(board.tiles), x, color.translateY(y))
                        .forEach(arr -> {
                            if (Tile.hasPiece(board.tiles[arr[0]][arr[1]], King.class)) {
                                System.out.println("King in check");
                                color.opposite().setInCheck(true);
                            }
                        });

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
        String colorVal = getTileColor(color);

        setStyle("-fx-background-color:" + colorVal + ";-fx-opacity:1");
    }

    private String getTileColor(Color color) {
        String colorVal = switch (color.toString()) {
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
            if (currentTeam == Color.BLACK) {
                img = new Image(piece.get().getType().getDarkImagePath());
            }
            ImageView view = new ImageView(img);
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
            if (color == Color.WHITE) {
                setStyle("-fx-background-color:#b6cac6");
            } else {
                setStyle("-fx-background-color:#988587");
            }

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

    public static boolean hasPiece(Optional<Piece> piece, Class<? extends PieceType> cl) {
        return piece.isPresent() && piece.get().getType().getClass() == cl;
    }

    public static boolean hasPiece(Tile tile, Class<? extends PieceType> cl) {
        return hasPiece(tile.currentPiece, cl);
    }

    public static boolean sameTeam(Tile t1, Tile t2) {
        return t1.currentPiece.isPresent() && t2.currentPiece.isPresent()
                && t1.currentPiece.get().getTeam() == t2.currentPiece.get().getTeam();
    }

    public static boolean diffTeam(Tile t1, Tile t2) {
        return t1.currentPiece.isPresent() && t2.currentPiece.isPresent()
                && t1.currentPiece.get().getTeam() != t2.currentPiece.get().getTeam();
    }
}
