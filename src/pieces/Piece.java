package pieces;

/**
 * The Piece class is used to implement the Piece in the game of chess.
 * It is extended by the other pieces in the game.
 * 
 * @author Aarav Makadia
 * @author Kuber Kupuriya
 */
public abstract class Piece {
    
    String value;
    boolean hasMoved;
    
    //Kuber
    /**
     * Checks if the path between the source and destination is empty.
     * Subclasses override this method to provide their specific path checking.
     * 
     * @param o the source position in algebraic notation
     * @param n the destination position in algebraic notation
     * @return true if the path is empty; false otherwise
     */
    public boolean isPathEmpty(String o, String n) {
        return true;
    }
    
    //Aarav
    /**
     * Determines if the piece can move from the source position to the destination position.
     * Subclasses override this method with specific movement rules.
     * 
     * @param o the source position in algebraic notation
     * @param n the destination position in algebraic notation
     * @return true if the move is valid; false otherwise
     */
    public boolean isMoveValid(String o, String n) {
        return true;
    }
    
    //Kuber
    /**
     * Moves the piece from the source position to the destination position.
     * This method is overridden by subclasses to implement specific movement behavior.
     * 
     * @param o the source position in algebraic notation
     * @param n the destination position in algebraic notation
     * @param p an optional promotion character (used by Pawn)
     */
    public void move(String o, String n, char p) {
        return;
    }
    
    //Aarav
    /**
     * Checks if the piece has moved.
     * 
     * @return true if the piece has moved; false otherwise
     */
    public boolean hasMoved() {
        return this.hasMoved;
    }
    
    //Kuber
    /**
     * Sets the moved flag for the piece.
     * 
     * @param h a boolean indicating whether the piece has moved
     */
    public void sethasMoved(boolean h) {
        this.hasMoved = h;
    }
    
    //Aarav
    /**
     * Retrieves the value of the piece.
     * 
     * @return the value of the piece as a String
     */
    public String getvalue() {
        return this.value;
    }
    
    //Kuber
    /**
     * Constructs a new Piece with the specified value.
     * 
     * @param v the value of the piece
     */
    public Piece(String v) {
        this.value = v;
        hasMoved = false;
    }
}
