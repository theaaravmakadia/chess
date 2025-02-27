/**
 * 
 */
package pieces;

import chess.Chess;
import java.util.*;

/**
 * The Knight class is an extension of the Piece class and creates a Knight Piece.
 * @author Deep Kotadia
 * @author Chinmoyi Bhushan
 *
 */
public class Knight extends Piece {

	public Knight(String value) {
		super(value);
	}

	/** isMoveValid takes in the src, destination of the piece's move and returns true if it is a valid move for Knight.
	 * @param oldPos   is the position the piece is trying to move from
	 * @param newPos   is the position the piece is trying to move to
	 * 
	 * @return true if the move is valid and false otherwise.
	 * 
	 */
	
	public boolean isMoveValid(String oldPos, String newPos) {
		
		/*to check if newPos is a box in the bounds of the board*/
		if(Chess.board.containsKey(newPos) == false) {
			return false;
		}
		
		String piece_oldPos = Chess.board.get(oldPos).getvalue();
		String piece_newPos = Chess.board.get(newPos).getvalue();
		
		
		/*Given a knight at a position, valid moves contains fileranks for all possible moves for that knight*/
		ArrayList<String> validmoves = new ArrayList<String>();
		
		String topleftbottom = Character.toString((char)(oldPos.charAt(0) - 2)) + (char)(((oldPos.charAt(1)-'0')+1)+'0');
		String toplefttop = Character.toString((char)(oldPos.charAt(0) - 1)) + (char)(((oldPos.charAt(1)-'0')+2)+'0');
		String toprightbottom = Character.toString((char)(oldPos.charAt(0) + 2)) + (char)(((oldPos.charAt(1)-'0')+1)+'0');
		String toprighttop = Character.toString((char)(oldPos.charAt(0) + 1)) + (char)(((oldPos.charAt(1)-'0')+2)+'0');
		
		String bottomleftbottom = Character.toString((char)(oldPos.charAt(0) - 1)) + (char)(((oldPos.charAt(1)-'0')-2)+'0');
		String bottomlefttop = Character.toString((char)(oldPos.charAt(0) - 2)) + (char)(((oldPos.charAt(1)-'0')-1)+'0');
		String bottomrightbottom = Character.toString((char)(oldPos.charAt(0) + 1)) + (char)(((oldPos.charAt(1)-'0')-2)+'0');
		String bottomrighttop = Character.toString((char)(oldPos.charAt(0) + 2)) + (char)(((oldPos.charAt(1)-'0')-1)+'0');
		
		if(Chess.board.containsKey(topleftbottom)) {
			validmoves.add(topleftbottom);
		}
		if(Chess.board.containsKey(toplefttop)) {
			validmoves.add(toplefttop);
		}
		if(Chess.board.containsKey(toprightbottom)) {
			validmoves.add(toprightbottom);
		}
		if(Chess.board.containsKey(toprighttop)) {
			validmoves.add(toprighttop);
		}
		if(Chess.board.containsKey(bottomleftbottom)) {
			validmoves.add(bottomleftbottom);
		}
		if(Chess.board.containsKey(bottomlefttop)) {
			validmoves.add(bottomlefttop);
		}
		if(Chess.board.containsKey(bottomrightbottom)) {
			validmoves.add(bottomrightbottom);
		}
		if(Chess.board.containsKey(bottomrighttop)) {
			validmoves.add(bottomrighttop);
		}
		
		/*At this point, validmoves has all the possible points the knight can move based on oldPos*/
		
		if(validmoves.contains(newPos)) { 
		/*is a valid Knight move*/
			
			/*to check if newPos is empty*/
			if(Chess.board.get(newPos) instanceof EmptySquare) {
				return true;
			}
			else {
				
				if(piece_oldPos.charAt(0) == piece_newPos.charAt(0)) {
					//piece color at both positions is the same	
					return false;
				}
				else {
					//there is a piece at the new position, we need to move there and kill that piece.
					return true;
				}
			}
		}
		else {
			/*validmoves does not contain newPos, not a valid Knight move*/
			return false;
		}
		
	}
	
	/**
	 * move implements the actual movement, here the Knight moves from its src to the position specified 
	 * @param oldPos   is the src of the current Knight Piece
	 * @param newPos   is the destination for the current Knight Piece
	 * 
	 */
	
	public void move(String oldPos, String newPos, char promopiece) {
		Piece piece_oldPos = Chess.board.get(oldPos);
		
		//move piece to newPos
		Chess.board.put(newPos, piece_oldPos);
		
		//make oldPos an empty box
		if(Chess.isBlackBox(oldPos.charAt(0), oldPos.charAt(1)-'0')) {
			Chess.board.put(oldPos, new EmptySquare("##"));
		}
		else {
			Chess.board.put(oldPos, new EmptySquare("  "));
		}
	}
	

	
	public boolean isPathEmpty(String oldPos, String newPos) {
		// TODO Auto-generated method stub
		return false;
	}

}
