package Chess;

public class Bishop extends ChessPiece {
    public Bishop(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
    }

    @Override
    public boolean canMove(int toX, int toY, Board board) {
        return Math.abs(x - toX) == Math.abs(y - toY) && board.isClearPath(x, y, toX, toY);
    }

    @Override
    public String getSymbol() {
        return isWhite ? "B" : "b";
    }
}
