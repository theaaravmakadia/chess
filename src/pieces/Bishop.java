/**
 * 
 */
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
public class Bishop extends Piece{

	public Bishop(String value) {
		super(value);
	}


	/** isMoveValid takes in the src, destination of the piece's move and returns true if it is a valid move for Bishop.
	 * @param oldPos is the position the piece is trying to move from
	 * @param newPos is the position the piece is trying to move to
	 * 
	 * @return true if the move is valid or false if not. 
	 * 
	 */

	public boolean isMoveValid(String oldPos, String newPos) {
		
		/*to check if newPos is a box in the bounds of the board*/
		if(Chess.board.containsKey(newPos) == false) {
			return false;
		}
		
		String piece_oldPos=Chess.board.get(oldPos).getvalue();
		String piece_newPos=Chess.board.get(newPos).getvalue();
		
		//to check if valid move for a bishop:
		if((Math.abs(oldPos.charAt(0) - newPos.charAt(0)) == Math.abs (oldPos.charAt(1) - newPos.charAt(1))) && !(oldPos.equals(newPos))) {
			
			//to check if the newPos is empty:
			if(Chess.board.get(newPos).getvalue().equals("  ") || Chess.board.get(newPos).getvalue().equals("##")) {
				if(isPathEmpty(oldPos, newPos)) {                
					return true;
				}
				
				else {
					//need to prompt user to dry a different valid move
					return false;
				}
			}                               //closing of the if check for newPos being empty. 
			
			/*Color case when newPos is not empty*/
			else {
				if(piece_oldPos.charAt(0)==piece_newPos.charAt(0)) {
					//System.out.println("Illegal move, try again");  //piece color is the same	
					return false;
				}
				
				else {
					if(isPathEmpty(oldPos,newPos)) {     //there is a piece at the new position, we need to move there and kill that piece.
						return true; 	
					}
					else {
						//path is not empty
						return false;
					}
				}
			}
			
		}
		
		else {   //illegal move for Bishop
			return false;
		}	
		
	}
	
	/**
	 * move implements the actual movement, here the Bishop moves from its src to the position specified 
	 * @param oldPos is the src of the current Bishop Piece
	 * @param newPos is the destination for the current Bishop Piece
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

	/**
	 * isPathEmpty checks if the path is clear for the Bishop to move from its src to its destination.
	 * @param oldPos   old position
	 * @param newPos   new position
	 * 
	 * @return true if the path is clear otherwise false
	 * 
	 */
	
	public boolean isPathEmpty(String oldPos, String newPos) {
		List<String> boxes=getIndicesInBetween(oldPos, newPos);
		
		for (String index:boxes) {
			if(!(Chess.board.get(index).getvalue().equals("##") || Chess.board.get(index).getvalue().equals("  "))) //box is empty
				return false;
		
		}
		return true;
	}
	
	/**
	 * getIndicesinBetween is getting the list of indices in between the old position and new position, it is a helper for isPathEmpty.
	 * @param oldPos   old position
	 * @param newPos   new position
	 * 
	 * @return List  containing the indices of the boxes between the src and the dst. 
	 * 
	 */
	
	public static List<String> getIndicesInBetween(String oldPos, String newPos) {
		//@requires oldPos and newPos to form a diagonal path.
		//e.g. (A1, C3) is valid. (E4, C6) is valid. (G6, D3) is valid.
		
		List<String> indicesList = new ArrayList<String>();
		
		int x1 = (int)(oldPos.charAt(0)); int x2 = (int)(newPos.charAt(0));
		int y1 = oldPos.charAt(1) - '0'; int y2 = newPos.charAt(1) - '0';
		
		int xGradient = Math.abs(x2 - x1)/(x2 - x1);
		int yGradient = Math.abs(y2 - y1)/(y2 - y1);
		
		for(int i = 1; i < Math.abs(x2 - x1); i++) {
			char nextX = (char)(x1 + i*xGradient);
			int nextY = y1 + i*yGradient;
			
			indicesList.add(Character.toString(nextX) + Integer.toString(nextY) + "");
		}
		
		return indicesList;
	}

}
