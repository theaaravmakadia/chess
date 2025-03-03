package chess;

/**
 * The Pawn class is used to implement the Pawn piece in the game of chess.
 * It handles movement, en passant eligibility, and promotion.
 * 
 * @author Aarav Makadia
 * @author Kuber Kupuriya
 */
public class Pawn extends Piece {

    public boolean canEnPassant;

    //Kuber
    /**
     * Checks if the board square at the specified column and row is empty.
     * 
     * @param a the column character
     * @param b the row number
     * @return true if the square is empty; false otherwise
     */
    private boolean isBoxEmpty(char a, int b) {
        String s = a + "" + b;
        if (Chess.board.get(s).getvalue().equals("##") || 
            Chess.board.get(s).getvalue().equals("  ")) {
            return true;
        }
        return false;
    }

    //Aarav
    /**
     * Performs pawn promotion by replacing the pawn at the new position with the chosen piece.
     * 
     * @param n the new position in algebraic notation
     * @param p the promotion piece character ('R', 'N', 'B', or default Queen)
     */
    private void pawn_promotion(String n, char p) {
        int f = n.charAt(1) - '0';
        Piece q;
        switch (p) {
            case 'R':
                if (f == 8) {
                    q = new Rook("wR");
                } else {
                    q = new Rook("bR");
                }
                break;
            case 'N':
                if (f == 8) {
                    q = new Knight("wN");
                } else {
                    q = new Knight("bN");
                }
                break;
            case 'B':
                if (f == 8) {
                    q = new Bishop("wB");
                } else {
                    q = new Bishop("bB");
                }
                break;
            default:
                if (f == 8) {
                    q = new Queen("wQ");
                } else {
                    q = new Queen("bQ");
                }
                break;
        }
        //Kuber: Replace the pawn with the promoted piece.
        Chess.board.put(n, q);
    }

    //Kuber
    /**
     * Checks if the path from the old position to the new position is clear for the pawn.
     * 
     * @param oldPos the starting position in algebraic notation
     * @param newPos the destination position in algebraic notation
     * @return true if the path is clear; false otherwise
     */
    public boolean isPathEmpty(String oldPos, String newPos) {
        int i;
        int d = oldPos.charAt(1) - '0';
        int f = newPos.charAt(1) - '0';
        // Aarav: Loop through the squares between the positions.
        if (d < f) { // moving forward for white
            for (i = d + 1; i <= f; i++) {
                if (!isBoxEmpty(oldPos.charAt(0), i)) {
                    return false;
                }
            }
        } else { // moving backward for black
            for (i = f; i < d; i++) {
                if (!isBoxEmpty(oldPos.charAt(0), i)) {
                    return false;
                }
            }
        }
        return true;
    }

    //Aarav
    /**
     * Determines if the move from oldPos to newPos is valid for the pawn.
     * Handles one-step moves, two-step moves (if first move), and diagonal captures.
     * 
     * @param oldPos the current position in algebraic notation
     * @param newPos the destination position in algebraic notation
     * @return true if the move is valid; false otherwise
     */
    public boolean isMoveValid(String oldPos, String newPos) {
        if (Chess.board.containsKey(newPos) == false) {
            return false;
        }
        String a = Chess.board.get(oldPos).getvalue();
        String b = Chess.board.get(newPos).getvalue();
        char c = oldPos.charAt(0);
        int d = oldPos.charAt(1) - '0';
        char e = newPos.charAt(0);
        int f = newPos.charAt(1) - '0';
        
        //Kuber: Validate move for a white pawn.
        if (a.charAt(0) == 'w') {
            if (f <= d) {
                return false;
            }
            if (e != c) { // diagonal move for capture
                if ((f - d) != 1 || Math.abs(e - c) != 1 || isBoxEmpty(e, f) == true) {
                    return false;
                }
                if (a.charAt(0) == b.charAt(0)) {
                    return false;
                }
                return true;
            }
            if (Math.abs(f - d) == 2) { // two steps forward
                if (d != 2) {
                    return false;
                }
                if (!(isPathEmpty(oldPos, newPos))) {
                    return false;
                }
                canEnPassant = true;
                return true;
            } else if (Math.abs(f - d) == 1) { // one step forward
                if (isPathEmpty(oldPos, newPos)) {
                    return true;
                }
                return false;
            } else {
                return false;
            }
        }
        //Kuber: Validate move for a black pawn.
        else {
            if (f >= d) {
                return false;
            }
            if (e != c) { // diagonal move for capture
                if ((d - f) != 1 || Math.abs(e - c) != 1 || isBoxEmpty(e, f) == true) {
                    return false;
                }
                return true;
            }
            if (Math.abs(f - d) == 2) { // two steps forward
                if (d != 7) {
                    return false;
                }
                if (!(isPathEmpty(oldPos, newPos))) {
                    return false;
                }
                canEnPassant = true;
                return true;
            } else if (Math.abs(f - d) == 1) { // one step forward
                if (isPathEmpty(oldPos, newPos)) {
                    return true;
                }
                return false;
            } else {
                return false;
            }
        }
    }

    //Kuber
    /**
     * Moves the pawn from the source position to the destination position.
     * Updates the board accordingly and checks for pawn promotion.
     * 
     * @param oldPos the source position in algebraic notation
     * @param newPos the destination position in algebraic notation
     * @param promopiece the promotion piece character (if applicable)
     */
    public void move(String oldPos, String newPos, char promopiece) {
        Piece a = Chess.board.get(oldPos);
        int f = newPos.charAt(1) - '0';
        Chess.board.put(newPos, a);
        if (Chess.isBlackBox(oldPos.charAt(0), oldPos.charAt(1) - '0')) {
            Chess.board.put(oldPos, new EmptySquare("##"));
        } else {
            Chess.board.put(oldPos, new EmptySquare("  "));
        }
        if (f == 1 || f == 8) {
            pawn_promotion(newPos, promopiece);
        }
    }

    //Aarav
    /**
     * Constructs a new Pawn object with the specified value.
     * Initializes en passant capability to false.
     * 
     * @param v a String representing the pawn's value (including color and type)
     */
    public Pawn(String v) {
        super(v);
        canEnPassant = false;
    }
}
