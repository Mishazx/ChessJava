package Chess;

public class King extends ChessPiece {
    public boolean hasMoved = false;

    public King(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
    }

    @Override
    public boolean canMove(int toX, int toY, Board board) {
        int dx = Math.abs(toX - x);
        int dy = Math.abs(toY - y);

        if (!hasMoved && dx == 2 && dy == 0) {
            return board.canCastle(this, toX);
        }
        return dx <= 1 && dy <= 1;
    }

    @Override
    public String getSymbol() {
        return isWhite ? "K" : "k";
    }
}
