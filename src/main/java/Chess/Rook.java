package Chess;

public class Rook extends ChessPiece {
    public boolean hasMoved = false;

    public Rook(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
    }

    @Override
    public boolean canMove(int toX, int toY, Board board) {
        return (x == toX || y == toY) && board.isClearPath(x, y, toX, toY);
    }

    @Override
    public String getSymbol() {
        return isWhite ? "R" : "r";
    }
}

