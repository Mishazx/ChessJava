package Chess;

public class Queen extends ChessPiece {
    public Queen(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
    }

    @Override
    public boolean canMove(int toX, int toY, Board board) {
        return board.isClearPath(x, y, toX, toY) &&
                (x == toX || y == toY || Math.abs(x - toX) == Math.abs(y - toY));
    }

    @Override
    public String getSymbol() {
        return isWhite ? "Q" : "q";
    }
}
