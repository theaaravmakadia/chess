package chess;

import java.lang.Math;

/**
 * The King class is used to implement the King piece in the game of chess.
 * It provides move validation and execution methods specific to the King.
 * 
 * @author Aarav Makadia
 * @author Kuber Kupuriya
 */
public class King extends Piece {

    /**
     * Checks if the path from o to n is empty.
     * For the King, this method is trivial since the King only moves one square.
     * 
     * @param o the starting position in algebraic notation
     * @param n the target position in algebraic notation
     * @return false as path checking is not required for a one-square King move
     */
    //Kuber
    public boolean isPathEmpty(String o, String n) {
        // The King moves one square, so path checking is not needed.
        return false;
    }
    
    /**
     * Moves the King from the source position o to the destination position n.
     * This method updates the board by placing the King in the new position and 
     * replacing the old position with an appropriate empty square.
     * 
     * @param o the current position of the King in algebraic notation
     * @param n the destination position in algebraic notation
     * @param p an unused promotion character (included for signature consistency)
     */
    //Aarav
    public void move(String o, String n, char p) {
        // Retrieve the King from its current square.
        Piece a = Chess.board.get(o);
        // Place the King at the destination square.
        Chess.board.put(n, a);
        // Determine the color of the originating square and clear it.
        if (Chess.isBlackBox(o.charAt(0), o.charAt(1) - '0'))
            Chess.board.put(o, new EmptySquare("##"));
        else
            Chess.board.put(o, new EmptySquare("  "));
    }
    
    /**
     * Checks if the move from o to n is valid for the King.
     * A valid king move is one square in any direction (horizontal, vertical, or diagonal)
     * and must not land on a square occupied by a piece of the same color.
     * 
     * @param o the current position of the King in algebraic notation (e.g., "e1")
     * @param n the target position in algebraic notation (e.g., "e2")
     * @return true if the move is valid; false otherwise
     */
    //Kuber
    public boolean isMoveValid(String o, String n) {
        if (Chess.board.containsKey(n) == false)
            return false;
        
        // Get the piece representations at the source and destination.
        String a = Chess.board.get(o).getvalue();
        String b = Chess.board.get(n).getvalue();
        
        // Calculate horizontal and vertical distances.
        int x = Math.abs(o.charAt(0) - n.charAt(0));
        int y = Math.abs(o.charAt(1) - n.charAt(1));
        
        // Validate that the move is exactly one square in any direction.
        if ((x == 0 && y == 1) || (x == 1 && y == 0) || (x == 1 && y == 1)) {
            // If the destination is empty, the move is allowed.
            if (Chess.board.get(n).getvalue().equals("  ") || 
                Chess.board.get(n).getvalue().equals("##"))
                return true;
            else {
                // For captures, ensure the piece at destination is of the opposite color.
                if (a.charAt(0) == b.charAt(0))
                    return false;
                else
                    return true;
            }
        } else {
            return false;
        }
    }
    
    /**
     * Constructs a new King object with the specified value.
     * 
     * @param v a String representing the King's value (including color and type)
     */
    //Aarav
    public King(String v) {
        super(v);
        // King object initialized.
    }
}