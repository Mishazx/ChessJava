package Chess;

public class Pawn extends ChessPiece {
    public Pawn(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
    }

    @Override
    public boolean canMove(int toX, int toY, Board board) {
        int direction = isWhite ? 1 : -1;

        if (toX == x && toY - y == direction && board.isEmpty(toX, toY)) {
            return true;
        }
        return Math.abs(toX - x) == 1 && toY - y == direction && board.hasOpponentPiece(toX, toY, isWhite);
    }

    @Override
    public String getSymbol() {
        return isWhite ? "P" : "p";
    }
}

