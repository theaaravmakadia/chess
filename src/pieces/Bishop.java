package pieces;

import chess.Chess;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

/**
 * The Bishop class is used to implement the Bishop piece in the game of chess.
 * @author Aarav Makadia
 * @author Kuber Kupuriya
 *
 */
public class Bishop extends Piece {

    /**
     * Computes all board positions between two given diagonal squares.
     * 
     * @param s the starting position in algebraic notation (e.g., "a1")
     * @param t the ending position in algebraic notation (e.g., "c3")
     * @return a List of board positions (as strings) that lie between s and t along a diagonal
     */
    public static List<String> getIndicesInBetween(String s, String t) {
		//Aarav
        List<String> l = new ArrayList<>();
        int a = (int) s.charAt(0);   // starting file (as ASCII code)
        int b = (int) t.charAt(0);   // ending file (as ASCII code)
        int c = s.charAt(1) - '0';   // starting rank as number
        int d = t.charAt(1) - '0';   // ending rank as number
        // Determine the direction of movement along the file and rank.
        int e = Math.abs(b - a) / (b - a);
        int f = Math.abs(d - c) / (d - c);
        // Loop through the intermediate steps along the diagonal.
        for (int g = 1; g < Math.abs(b - a); g++) {
            char h = (char) (a + g * e);
            int i = c + g * f;
            l.add(Character.toString(h) + Integer.toString(i));
        }
        return l;
    }

    /**
     * Checks if the diagonal path from s to t is unobstructed.
     * 
     * @param s the starting position in algebraic notation
     * @param t the ending position in algebraic notation
     * @return true if every intermediate square between s and t is empty; false otherwise
     */
    public boolean isPathEmpty(String s, String t) {
		//Kuber
        List<String> l = getIndicesInBetween(s, t);
        // Loop over intermediate squares and verify they are unoccupied.
        for (String x : l) {
            if (!(Chess.board.get(x).getvalue().equals("##") || 
                  Chess.board.get(x).getvalue().equals("  ")))
                return false;
        }
        return true;
    }

    /**
     * Determines whether a move from s to t is valid for a bishop.
     * The move must be diagonal, not staying on the same square, and the path must be clear.
     * 
     * @param s the starting position in algebraic notation
     * @param t the target position in algebraic notation
     * @return true if the move is valid; false otherwise
     */
    public boolean isMoveValid(String s, String t) {
		//Aarav
        // Check if destination is within board boundaries.
        if (!Chess.board.containsKey(t))
            return false;

        String a = Chess.board.get(s).getvalue(); // value at source
        String b = Chess.board.get(t).getvalue();   // value at destination

        // Verify the move is along a diagonal and the bishop isn't staying in place.
        if ((Math.abs(s.charAt(0) - t.charAt(0)) == Math.abs(s.charAt(1) - t.charAt(1))) && !s.equals(t)) {
            // If destination square is empty, check that the path is not obstructed.
            if (Chess.board.get(t).getvalue().equals("  ") || Chess.board.get(t).getvalue().equals("##")) {
                return isPathEmpty(s, t);
            } else {
                // If the destination square is occupied, check for same color.
                if (a.charAt(0) == b.charAt(0))
                    return false;
                else {
                    // Allow capture only if the path is clear.
                    return isPathEmpty(s, t);
                }
            }
        } else {
            return false;
			//why?
        }
    }

    /**
     * Moves the bishop from position s to position t.
     * The method updates the board by placing the bishop at the new position and clearing the source.
     * 
     * @param s the current position of the bishop in algebraic notation
     * @param t the destination position in algebraic notation
     * @param p a promotion character (unused for bishop, but maintained for method signature consistency)
     */
    public void move(String s, String t, char p) {
		//Aarav
        Piece a = Chess.board.get(s);
        Chess.board.put(t, a);
        // Replace the source square with an empty square based on its color.
        if (Chess.isBlackBox(s.charAt(0), s.charAt(1) - '0'))
            Chess.board.put(s, new EmptySquare("##"));
        else
            Chess.board.put(s, new EmptySquare("  "));
    }

    /**
     * Constructs a new Bishop object with the specified value.
     * 
     * @param v a string representing the bishop's value (typically encoding its color and type)
     */
    public Bishop(String v) {
		//Kuber
        super(v);
    }
}