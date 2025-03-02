package pieces;

import chess.Chess;
import java.util.ArrayList;
import java.util.List;

/**
 * The Rook class is used to implement the Rook piece in the game of chess.
 * It provides move validation, movement execution, and path checking methods specific to the Rook.
 * 
 * @author Aarav Makadia
 * @author Kuber Kupuriya
 */
public class Rook extends Piece {

    /**
     * Constructs a new Rook object with the specified value.
     * 
     * @param v a String representing the Rook's value (including color and type)
     */
    public Rook(String v) {
        super(v);
    }

    /**
     * Determines if the move from o to n is valid for the Rook.
     * The move is valid if it is along a straight line (vertical or horizontal),
     * the source and destination are not the same, and the path is clear.
     * 
     * @param o the starting position in algebraic notation
     * @param n the destination position in algebraic notation
     * @return true if the move is valid; false otherwise
     */
    public boolean isMoveValid(String o, String n) {
        if (Chess.board.containsKey(n) == false)
            return false;
        
        String p = Chess.board.get(o).getvalue();
        String q = Chess.board.get(n).getvalue();
        
        if ((o.charAt(0) == n.charAt(0) || o.charAt(1) == n.charAt(1)) && !o.equals(n)) {
            if (Chess.board.get(n).getvalue().equals("  ") || 
                Chess.board.get(n).getvalue().equals("##")) {
                if (isPathEmpty(o, n))
                    return true;
                else
                    return false;
            } else {
                if (p.charAt(0) == q.charAt(0))
                    return false;
                else {
                    if (isPathEmpty(o, n))
                        return true;
                    else
                        return false;
                }
            }
        } else {
            return false;
        }
    }

    /**
     * Moves the Rook from the source position o to the destination position n.
     * Updates the board by placing the Rook at the destination and replacing the source with an empty square.
     * 
     * @param o the current position of the Rook in algebraic notation
     * @param n the destination position in algebraic notation
     * @param x an unused promotion character (for signature consistency)
     */
    public void move(String o, String n, char x) {
        Piece p = Chess.board.get(o);
        Chess.board.put(n, p);
        if (Chess.isBlackBox(o.charAt(0), o.charAt(1) - '0'))
            Chess.board.put(o, new EmptySquare("##"));
        else
            Chess.board.put(o, new EmptySquare("  "));
    }

    /**
     * Checks if the path from the source position o to the destination position n is clear for the Rook's move.
     * For vertical moves, it checks the squares between the two ranks.
     * For horizontal moves, it checks the squares between the two files.
     * 
     * @param o the starting position in algebraic notation
     * @param n the destination position in algebraic notation
     * @return true if the path is clear; false otherwise
     */
    public boolean isPathEmpty(String o, String n) {
        if (o.charAt(0) == n.charAt(0)) {
            int i;
            int a = o.charAt(1) - '0';
            int b = n.charAt(1) - '0';
            
            if (a < b) {
                for (i = a + 1; i < b; i++) {
                    if (!isBoxEmpty(o.charAt(0), i))
                        return false;
                }
            } else {
                for (i = b + 1; i < a; i++) {
                    if (!isBoxEmpty(o.charAt(0), i))
                        return false;
                }
            }
        } else if (o.charAt(1) == n.charAt(1)) {
            char c;
            char d = o.charAt(0);
            char e = n.charAt(0);
            
            if (d < e) {
                for (c = (char)(d + 1); c < e; c++) {
                    if (!isBoxEmpty(c, o.charAt(1) - '0'))
                        return false;
                }
            } else {
                for (c = (char)(e + 1); c < d; c++) {
                    if (!isBoxEmpty(c, o.charAt(1) - '0'))
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * Helper function that checks if a specific board square is empty.
     * 
     * @param a the column character
     * @param b the row number
     * @return true if the square is empty; false otherwise
     */
    private static boolean isBoxEmpty(char a, int b) {
        String s = a + "" + b;
        if (Chess.board.get(s).getvalue().equals("##") || 
            Chess.board.get(s).getvalue().equals("  "))
            return true;
        return false;
    }
}
