package Chess;

public abstract class ChessPiece {
    protected int x, y;
    protected boolean isWhite;

    public ChessPiece(int x, int y, boolean isWhite) {
        this.x = x;
        this.y = y;
        this.isWhite = isWhite;
    }
    public abstract String getSymbol();
    public abstract boolean canMove(int toX, int toY, Board board);
    public boolean isWhite() { return isWhite; }
    public boolean isAtPosition(int x, int y) { return this.x == x && this.y == y; }
}
