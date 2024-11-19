package Chess;

public class Horse  extends ChessPiece {
    public Horse (int x, int y, boolean isWhite) {
        super(x, y, isWhite);
    }

    @Override
    public boolean canMove(int toX, int toY, Board board) {
        int dx = Math.abs(toX - x);
        int dy = Math.abs(toY - y);
        return dx * dy == 2;
    }

    @Override
    public String getSymbol() {
        return isWhite ? "N" : "n";
    }
}
