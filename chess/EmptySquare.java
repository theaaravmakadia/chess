package chess;

/**
 * The EmptySquare class is used to implement the EmptySquare piece in the game of chess.
 * @author Aarav Makadia
 * @author Kuber Kupuriya
 *
 */
public class EmptySquare extends Piece {

	/**
	 * Constructs an EmptySquare instance with the specified display value.
	 * <p>
	 * This constructor calls the parent class constructor to initialize the
	 * visual representation of the empty square.
	 * </p>
	 *
	 * @param value a String representing the display pattern for the empty square.
	 */
	public EmptySquare(String value) {
		//Aarav
		// Initialize the empty square using the superclass constructor.
		super(value);
	}
}
