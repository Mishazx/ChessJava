package Chess;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board {
    private final List<ChessPiece> pieces;

    public Board() {
        pieces = new ArrayList<>();
        setupPieces();
    }

    private void setupPieces() {
        pieces.add(new Rook(0, 0, true));
        pieces.add(new Horse(1, 0, true));
        pieces.add(new Bishop(2, 0, true));
        pieces.add(new Queen(3, 0, true));
        pieces.add(new King(4, 0, true));
        pieces.add(new Bishop(5, 0, true));
        pieces.add(new Horse(6, 0, true));
        pieces.add(new Rook(7, 0, true));
        for (int i = 0; i < 8; i++) {
            pieces.add(new Pawn(i, 1, true));
        }

        pieces.add(new Rook(0, 7, false));
        pieces.add(new Horse(1, 7, false));
        pieces.add(new Bishop(2, 7, false));
        pieces.add(new Queen(3, 7, false));
        pieces.add(new King(4, 7, false));
        pieces.add(new Bishop(5, 7, false));
        pieces.add(new Horse(6, 7, false));
        pieces.add(new Rook(7, 7, false));
        for (int i = 0; i < 8; i++) {
            pieces.add(new Pawn(i, 6, false));
        }
    }

    public ChessPiece getPieceAt(int x, int y) {
        return pieces.stream().filter(p -> p.isAtPosition(x, y)).findFirst().orElse(null);
    }

    public boolean isEmpty(int x, int y) {
        return getPieceAt(x, y) == null;
    }

    public boolean hasOpponentPiece(int x, int y, boolean isWhite) {
        ChessPiece piece = getPieceAt(x, y);
        return piece != null && piece.isWhite() != isWhite;
    }

    public boolean isClearPath(int fromX, int fromY, int toX, int toY) {
        int dx = Integer.compare(toX, fromX);
        int dy = Integer.compare(toY, fromY);

        int x = fromX + dx;
        int y = fromY + dy;

        while (x != toX || y != toY) {
            if (!isEmpty(x, y)) {
                return false;
            }
            x += dx;
            y += dy;
        }
        return true;
    }

    public boolean canCastle(King king, int toX) {
        if (king.hasMoved) {
            return false;
        }

        int rookX = (toX > king.x) ? 7 : 0;
        ChessPiece rook = getPieceAt(rookX, king.y);

        if (!(rook instanceof Rook) || ((Rook) rook).hasMoved) {
            return false;
        }

        int dx = (toX > king.x) ? 1 : -1;

        for (int x = king.x + dx; x != rookX; x += dx) {
            if (!isEmpty(x, king.y) || isUnderAttack(x, king.y, king.isWhite)) {
                return false;
            }
        }
        return true;
    }

    public boolean performCastle(boolean isWhite, boolean isKingside) {
        int row = isWhite ? 0 : 7;
        int kingX = 4;
        int rookX = isKingside ? 7 : 0;
        int kingTargetX = isKingside ? 6 : 2;
        int rookTargetX = isKingside ? 5 : 3;

        ChessPiece king = getPieceAt(kingX, row);
        ChessPiece rook = getPieceAt(rookX, row);

        if (!(king instanceof King) || !(rook instanceof Rook)) return false;
        if (((King) king).hasMoved || ((Rook) rook).hasMoved) return false;
        if (!isClearPath(kingX, row, rookX, row)) return false;
        if (isUnderAttack(kingX, row, isWhite) || isUnderAttack(kingTargetX, row, isWhite)) return false;

        movePiece(kingX, row, kingTargetX, row);
        movePiece(rookX, row, rookTargetX, row);

        System.out.println((isWhite ? "White" : "Black") +" castling has been completed " + (isKingside ? "On the royal flank" : "on the queen side"));
        return true;
    }


    public void movePiece(int fromX, int fromY, int toX, int toY) {
        ChessPiece piece = getPieceAt(fromX, fromY);
        if (piece != null && piece.canMove(toX, toY, this)) {
            pieces.removeIf(p -> p.isAtPosition(toX, toY));
            piece.x = toX;
            piece.y = toY;
        }
    }

    public void displayBoard() {
        System.out.println("  A B C D E F G H     BLACK");
        for (int i = 7; i >= 0; i--) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = getPieceAt(j, i);
                System.out.print((piece == null ? "." : piece.getSymbol()) + " ");
            }
            System.out.println(" " + (i + 1));
        }
        System.out.println("  A B C D E F G H     WHITE");
    }

    public boolean isUnderAttack(int x, int y, boolean isWhite) {
        for (ChessPiece piece : pieces) {
            if (piece.isWhite() != isWhite && piece.canMove(x, y, this)) {
                return true;
            }
        }
        return false;
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        boolean isWhiteTurn = true;

        while (true) {
            displayBoard();
            System.out.println((isWhiteTurn ? "white" : "black") + ", (example, e2 e4): ");
            String move = scanner.nextLine();

            if (move.equals("0-0")) {
                if (performCastle(isWhiteTurn, true)) {
                    isWhiteTurn = !isWhiteTurn;
                    continue;
                } else {
                    System.out.println("Рокировка невозможна.");
                    continue;
                }
            } else if (move.equals("0-0-0")) {
                if (performCastle(isWhiteTurn, false)) {
                    isWhiteTurn = !isWhiteTurn;
                    continue;
                } else {
                    System.out.println("Рокировка невозможна.");
                    continue;
                }
            }

            int fromX = move.charAt(0) - 'a';
            int fromY = move.charAt(1) - '1';
            int toX = move.charAt(3) - 'a';
            int toY = move.charAt(4) - '1';

            ChessPiece piece = getPieceAt(fromX, fromY);
            if (piece != null && piece.isWhite() == isWhiteTurn && piece.canMove(toX, toY, this)) {
                movePiece(fromX, fromY, toX, toY);
                isWhiteTurn = !isWhiteTurn;
            } else {
                System.out.println("Invalid move");
            }
        }
    }
}
