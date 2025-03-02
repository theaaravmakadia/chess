package pieces;

import chess.Chess;
import java.util.ArrayList;
import java.util.List;

/**
 * The Knight class is used to implement the Knight piece in the game of chess.
 * It provides move validation and movement methods specific to the Knight.
 * 
 * @author Aarav Makadia
 * @author Kuber Kupuriya
 */
public class Knight extends Piece {

    //Kuber
    /**
     * Checks if the path from o to n is empty.
     * For the Knight, this method is trivial since path checking is not required.
     *
     * @param o the starting position in algebraic notation
     * @param n the destination position in algebraic notation
     * @return false always, as the Knight's move does not require path checking
     */
    public boolean isPathEmpty(String o, String n) {
        return false;
    }

    //Kuberr
    /**
     * Moves the Knight from position o to position n.
     * This method updates the board by placing the Knight at n and replacing o with an empty square.
     *
     * @param o the source position in algebraic notation
     * @param n the destination position in algebraic notation
     * @param p an unused promotion character (for signature consistency)
     */
    public void move(String o, String n, char p) {
        Piece a = Chess.board.get(o);
        Chess.board.put(n, a);
        if (Chess.isBlackBox(o.charAt(0), o.charAt(1) - '0'))
            Chess.board.put(o, new EmptySquare("##"));
        else
            Chess.board.put(o, new EmptySquare("  "));
    }

    //Kuber
    /**
     * Determines if moving from o to n is a valid Knight move.
     * It computes all possible Knight moves from o and returns true if n is one of them.
     * If the destination is occupied by a piece of the same color, the move is invalid.
     *
     * @param o the current position of the Knight in algebraic notation
     * @param n the target position in algebraic notation
     * @return true if the move is valid; false otherwise
     */
    public boolean isMoveValid(String o, String n) {
        if (!Chess.board.containsKey(n))
            return false;
        
        String a = Chess.board.get(o).getvalue();
        String b = Chess.board.get(n).getvalue();
        
        ArrayList<String> l = new ArrayList<>();
        
        // Calculate all possible Knight moves using one-letter variable names.
        String x = Character.toString((char)(o.charAt(0) - 2)) + (char)(((o.charAt(1) - '0') + 1) + '0');
        String y = Character.toString((char)(o.charAt(0) - 1)) + (char)(((o.charAt(1) - '0') + 2) + '0');
        String z = Character.toString((char)(o.charAt(0) + 2)) + (char)(((o.charAt(1) - '0') + 1) + '0');
        String w = Character.toString((char)(o.charAt(0) + 1)) + (char)(((o.charAt(1) - '0') + 2) + '0');
        String u = Character.toString((char)(o.charAt(0) - 1)) + (char)(((o.charAt(1) - '0') - 2) + '0');
        String v = Character.toString((char)(o.charAt(0) - 2)) + (char)(((o.charAt(1) - '0') - 1) + '0');
        String s = Character.toString((char)(o.charAt(0) + 1)) + (char)(((o.charAt(1) - '0') - 2) + '0');
        String t = Character.toString((char)(o.charAt(0) + 2)) + (char)(((o.charAt(1) - '0') - 1) + '0');
        
        if (Chess.board.containsKey(x)) l.add(x);
        if (Chess.board.containsKey(y)) l.add(y);
        if (Chess.board.containsKey(z)) l.add(z);
        if (Chess.board.containsKey(w)) l.add(w);
        if (Chess.board.containsKey(u)) l.add(u);
        if (Chess.board.containsKey(v)) l.add(v);
        if (Chess.board.containsKey(s)) l.add(s);
        if (Chess.board.containsKey(t)) l.add(t);
        
        if (l.contains(n)) {
            if (Chess.board.get(n) instanceof EmptySquare)
                return true;
            else {
                if (a.charAt(0) == b.charAt(0))
                    return false;
                else
                    return true;
            }
        } else {
            return false;
        }
    }

    //Aarav
    /**
     * Constructs a new Knight object with the specified value.
     *
     * @param v a String representing the Knight's value (including color and type)
     */
    public Knight(String v) {
        super(v);
    }
}
