/**
 * 
 */
package pieces;

/**
 * The Piece class is used to implement the Piece in the game of chess. It is extended by the other pieces in the game.
 * @author Aarav Makadia
 * @author Kuber Kupuriya
 *
 */
public abstract class Piece {
	
	String value;
	boolean hasMoved;
	
	/**
	 * defined constructor for Piece class.
	 * @param value     value of the Piece
	 *
	 */
	
	public Piece(String value) {
		this.value = value;
		hasMoved = false;
	}
	
	/**
	 * getValue gets the value of the piece
	 * @return the value of the piece.
	 *
	 */
	
	public String getvalue() {
		return this.value;
	}
	
	/**
	 * hasMoved is a function to check if the Piece has moved or not
	 * @return true or false depending upon if the piece has moved
	 *
	 */
	
	public boolean hasMoved() {
		return this.hasMoved;
	}
	
	/**
	 * sethasMoved is a function that acts as a flag for if the piece has moved.
	 * @param hasMoved it is a boolean that is passed in to see if the piece has moved.
	 *
	 */
	public void sethasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}
	
	/**
	 * move function to move the Piece from one pos to another, it is overriden by subclasses to implement their own moves. 
	 * @param oldPos  old position
	 * @param newPos  new position
	 * @param promopiece  required in Pawn's case. 
	 *
	 */
	public void move(String oldPos, String newPos, char promopiece) {
		return;
	}
	
	/**
	 * isMoveValid function to check if the Piece can move from one pos to another, it is extended to the subclasses to check for the valid move of that particular Piece.
	 * @param oldPos old position
	 * @param newPos new position
	 * 
	 * @return true if valid and false otherwise.
	 *
	 */
	
	public boolean isMoveValid(String oldPos, String newPos) {
		return true;
	}
	
	/**
	 * isPathEmpty function to check if the path b/w the src and dest is empty, it is extended by the subclasses to check their own paths.
	 * @param oldPos  old position
	 * @param newPos  new position
	 * 
	 * @return true if path empty and false otherwise
	 *
	 */	
	
	public boolean isPathEmpty(String oldPos, String newPos) {
		return true;
	}
	


}
