/**
 * 
 */
package chess;

/**
 * Chess contains the main method and is where the game is running from. 
 * @author Deep Kotadia
 * @author Chinmoyi Bhushan
 *
 */
import java.util.*;
import pieces.*;

public class Chess {

	public static HashMap<String, Piece> board = new HashMap<String, Piece>(70);
	
	/**
	 * initboard is a function to setup the board with the pieces on it. 
	 * It takes no parameters and does not have a return type.
	 * 
	 */
	
	public static void initboard() {
		
		for(char alpha = 'a'; alpha <= 'h'; alpha++) {
			for(int num = 1; num <= 8; num++) {
				
				String filerank = Character.toString(alpha) + Integer.toString(num);
				
				if(isBlackBox(alpha, num)) {
					board.put(filerank, new EmptySquare("##"));
				}
				else {
					board.put(filerank, new EmptySquare("  "));
				}
				
				//fill black side
				if(filerank.equals("a8") || filerank.equals("h8")) {
					board.put(filerank, new Rook("bR"));
				}
				else if(filerank.equals("b8") || filerank.equals("g8")) {
					board.put(filerank, new Knight("bN"));
				}
				else if(filerank.equals("c8") || filerank.equals("f8")) {
					board.put(filerank, new Bishop("bB"));
				}
				else if(filerank.equals("d8")) {
					board.put(filerank, new Queen("bQ"));
				}
				else if(filerank.equals("e8")) {
					board.put(filerank, new King("bK"));
				}
				else if(num == 7) {
					board.put(filerank, new Pawn("bp"));
				}
				
				//fill white side
				if(filerank.equals("a1") || filerank.equals("h1")) {
					board.put(filerank, new Rook("wR"));
				}
				else if(filerank.equals("b1") || filerank.equals("g1")) {
					board.put(filerank, new Knight("wN"));
				}
				else if(filerank.equals("c1") || filerank.equals("f1")) {
					board.put(filerank, new Bishop("wB"));
				}
				else if(filerank.equals("d1")) {
					board.put(filerank, new Queen("wQ"));
				}
				else if(filerank.equals("e1")) {
					board.put(filerank, new King("wK"));
				}
				else if(num == 2) {
					board.put(filerank, new Pawn("wp"));
				}
				
			}
		}
	}
	
	/**
	 * isBlackBox is to check if a box on the board is a black box or not. 
	 * @param  alpha  column
	 * @param  num    row
	 * 
	 * @return   true if the box/square is empty and false otherwise
	 */
	public static boolean isBlackBox(char alpha, int num) {
		
		if((num == 1 || num == 3 || num == 5 || num == 7) && (alpha == 'a' || alpha == 'c' || alpha == 'e' || alpha == 'g')) {
			return true;
		}
		else if((num == 2 || num == 4 || num == 6 || num == 8) && (alpha == 'b' || alpha == 'd' || alpha == 'f' || alpha == 'h')) {
			return true;
		}
		return false;
	}
	
	/**
	 * printboard prints the board whenever required.
	 * It has no params and no return type.
	 */
	
	public static void printboard() {
		for(int num = 8; num >= 1; num--) {
			for(char alpha = 'a'; alpha <= 'h'; alpha++) {
				String filerank = Character.toString(alpha) + Integer.toString(num);
				String piece_at_index = board.get(filerank).getvalue();
				System.out.print(piece_at_index + " ");
			}
			System.out.println(num);
		}
		System.out.println(" a" + "  b" + "  c" + "  d" + "  e" + "  f" + "  g" + "  h");
		System.out.println();

	}
	
	public static void main(String[] args) {
		
		initboard();
		printboard();
		String wholestr, oldPos, newPos, kingPos;
		String promotion = null;
		Piece piece_oldPos, piece_newPos;
		char promopiece;
		String[] inputstr_as_arr = new String[3];
		
		boolean is_white_move = true; //true when it's white's move, false when it's black's move
		boolean is_move_valid = false; //return value from is_move_valid method
		boolean castle_success = false; //true if castling was successful, false otherwise
		boolean EnPassant_success = false; //true if EnPassant successful, false otherwise
		boolean draw_proposal = false; //true when one player asks the other for a draw
		
		Scanner sc = new Scanner(System.in);
		
		while(state_of_game() == 0) { //to check if game is still on
			
			
			if(is_white_move == true) {
				System.out.print("White's move: ");
			}
			else {
				System.out.print("Black's move: ");
			}
			wholestr = sc.nextLine(); 
			
			while((wholestr.length() >= 7 && (wholestr.charAt(2) != ' ' || wholestr.charAt(5) != ' ')) || wholestr.length() == 0) {
				System.out.println("Illegal move, try again");
				System.out.println();
				if(is_white_move == true) {
					System.out.print("White's move: ");
				}
				else {
					System.out.print("Black's move: ");
				}
				wholestr = sc.nextLine();
			}
			
			System.out.println();
			
			/*check if player resigned, if yes, end game, opposition wins*/
			if(wholestr.equals("resign")) {
				if(is_white_move) {
					System.out.println("Black wins");
				}
				else {
					System.out.println("White wins");
				}
				sc.close();
				return;
			}
			
			/*check if one player asked for a draw in previous move and the other accepted it, if yes, game over, draw*/
			if(draw_proposal == true) {
				if(wholestr.equals("draw")) { //draw proposal accepted
					System.out.println("draw");
					sc.close();
					return;
				}
				else { //draw proposal denied
					draw_proposal = false;
				}
			}
			
			inputstr_as_arr = wholestr.split(" ");
			
			/*This is for pawn promotion, if applicable*/
			if(inputstr_as_arr.length > 2) {
				promotion = inputstr_as_arr[2];
				promopiece = promotion.charAt(0);
				
				/*check if one player asked for a draw*/
				if(promotion.equals("draw?")) {
					draw_proposal = true;
					promopiece = '0';
				}
				
			}
			else {
				promopiece = '0'; //set promotion string to 0 (no promotion)
			}
			
			if(inputstr_as_arr.length < 2) { //some input that does not make sense, is not an actual chess move
				System.out.println("Illegal move, try again");
				System.out.println();
				continue;
			}
			
			castle_success = false;
			EnPassant_success = false;
			oldPos = inputstr_as_arr[0];
			newPos = inputstr_as_arr[1];
			piece_oldPos = board.get(oldPos);
			piece_newPos = board.get(newPos);
			
			if((is_white_move == true && piece_oldPos.getvalue().charAt(0) == 'w') || (is_white_move == false && piece_oldPos.getvalue().charAt(0) == 'b')) {
				
				/*Test if requested move is valid for the corresponding piece (polymorphism used here)*/
				is_move_valid = piece_oldPos.isMoveValid(oldPos, newPos);
				
				//if this is_move_valid returned true, then we need to see if it resulted in a check
				if(is_move_valid == true) {
					if(is_white_move==true) {
						
						piece_oldPos.move(oldPos, newPos, promopiece); //move temporarily to test if new state of board will result in current player's king to be in check
						kingPos=kingPosition('w');  //if it is white's move, we are checking before executing the move if this will result into the white's king in a check position
						
						if(isCheck("wK",kingPos)) {
							is_move_valid=false; //current player's king is in check; this move should not be allowed! revert
						}
						else {
							is_move_valid=true; //move is allowed, but we cannot move it right away; it's done later, so revert
						}
						//revert
						board.put(oldPos, piece_oldPos);
						board.put(newPos, piece_newPos);
						
					}
					else {
						piece_oldPos.move(oldPos, newPos, promopiece);
						kingPos=kingPosition('b');
						
						if(isCheck("bK",kingPos)) {
							is_move_valid=false;
						}
						
						else {
							is_move_valid=true;
						}
						//revert for black here.
						board.put(oldPos, piece_oldPos);
						board.put(newPos, piece_newPos);
					}
				}
				
				
				/*Check and act if this is a castling move*/
				castle_success = castling(oldPos, newPos);
				
				/*Check and act if an EnPassant move*/
				EnPassant_success = EnPassant(oldPos, newPos, is_white_move);
				
			}
			else {
				
				System.out.println("Illegal move, try again");
				System.out.println();
				continue;
				
			}
			
			if(is_move_valid == true) {
				
				/*move is valid, so now move the piece (update hashmap)*/
				piece_oldPos.move(oldPos, newPos, promopiece);
				piece_oldPos.sethasMoved(true);
				
				printboard();
				
				if(is_white_move==true) {
					kingPos=kingPosition('b');
					if(isCheck("bK",kingPos)) { //my move resulted in enemy king being in check
						if(isCheckmate('b', kingPos)) {
							System.out.println("Checkmate");
							System.out.println();
							System.out.println("White wins");
							sc.close();
							return;
						}
						System.out.println("Check");
						System.out.println();
					}
				}
				
				else {
					kingPos = kingPosition('w');
					if(isCheck("wK",kingPos)) {
						if(isCheckmate('w', kingPos)) {
							System.out.println("Checkmate");
							System.out.println();
							System.out.println("Black wins");
							sc.close();
							return;
						}
						System.out.println("Check");
						System.out.println();
					}
				}
				
				
			}
			else if(castle_success == false && EnPassant_success == false) { //not a valid move, prompt to enter again
				System.out.println("Illegal move, try again");
				System.out.println();
				continue;
			}
			
			/*
			 * check if any appropriate pawns have canEnPassant set to true, if yes, set them to false
			 * if white's move, set black pawns' canEnPassant to false
			 * if black's move, set white pawns' canEnPassant to false
			 * */
			refresh_EnPassant(is_white_move);
			
			/*other person's turn, so switch*/
			if(is_white_move == true) {
				is_white_move = false; //now black's turn
			}
			else {
				is_white_move = true; //now white's turn
			}
						
		}  //state of game loop end
		
		System.out.println("Checkmate");
		System.out.println();
		
		//need to check and print if white or black won
		
		/*for(String currentpiece : board.keySet()) {
			
		}*/
		
		sc.close();

	}
	
	/*function to check state of the game after every legal move */
	public static int state_of_game() {
		return 0;
	}
	
	/**
	 * isCheckmate is a function to check if the king at its current position can be captured by the opponent and has no escape route. 
	 * @param   color       to specify white king or black king
	 * @param   kingPos     is the position of the King that can be captured
	 * 
	 * @return   true if the no escape for the king and it can be captured, else false. 
	 */
	
	private static boolean isCheckmate(char color, String kingPos) {
		ArrayList<String> possiblemoves = new ArrayList<String>();
		
		String top = Character.toString((char)(kingPos.charAt(0) + 0)) + (char)(((kingPos.charAt(1)-'0')+1)+'0');
		String topleft = Character.toString((char)(kingPos.charAt(0) - 1)) + (char)(((kingPos.charAt(1)-'0')+1)+'0');
		String topright = Character.toString((char)(kingPos.charAt(0) + 1)) + (char)(((kingPos.charAt(1)-'0')+1)+'0');
		String right = Character.toString((char)(kingPos.charAt(0) + 1)) + (char)(((kingPos.charAt(1)-'0')+0)+'0');
		String left = Character.toString((char)(kingPos.charAt(0) - 1)) + (char)(((kingPos.charAt(1)-'0')+0)+'0');
		String bottomleft = Character.toString((char)(kingPos.charAt(0) - 1)) + (char)(((kingPos.charAt(1)-'0')-1)+'0');
		String bottomright = Character.toString((char)(kingPos.charAt(0) + 1)) + (char)(((kingPos.charAt(1)-'0')-1)+'0');
		String bottom = Character.toString((char)(kingPos.charAt(0) + 0)) + (char)(((kingPos.charAt(1)-'0')-1)+'0');
		
		if(Chess.board.containsKey(top) && board.get(top).getvalue().charAt(0) != color) {
			possiblemoves.add(top);
		}
		if(Chess.board.containsKey(topleft) && board.get(topleft).getvalue().charAt(0) != color) {
			possiblemoves.add(topleft);
		}
		if(Chess.board.containsKey(topright) && board.get(topright).getvalue().charAt(0) != color) {
			possiblemoves.add(topright);
		}
		if(Chess.board.containsKey(right) && board.get(right).getvalue().charAt(0) != color) {
			possiblemoves.add(right);
		}
		if(Chess.board.containsKey(left) && board.get(left).getvalue().charAt(0) != color) {
			possiblemoves.add(left);
		}
		if(Chess.board.containsKey(bottomleft) && board.get(bottomleft).getvalue().charAt(0) != color) {
			possiblemoves.add(bottomleft);
		}
		if(Chess.board.containsKey(bottomright) && board.get(bottomright).getvalue().charAt(0) != color) {
			possiblemoves.add(bottomright);
		}
		if(Chess.board.containsKey(bottom) && board.get(bottom).getvalue().charAt(0) != color) {
			possiblemoves.add(bottom);
		}
		
		/*At this point, possiblemoves has all the possible points the king can move based on its position. Now test if any of these do not result in check
		 * Otherwise, Checkmate!
		 * */
		
		Piece Kingpiece  = board.get(kingPos);
		Piece blockingPiece= null;
		board.put(kingPos, new EmptySquare("##")); //temporarily remove actual king from its position (replace with empty square, so does not interfere)
		
		if(color == 'w') {
			for(int i = 0; i<possiblemoves.size(); i++) {
				
				if(board.get(possiblemoves.get(i)).getvalue().charAt(0)=='b') {
					blockingPiece=board.get(possiblemoves.get(i));
					board.put(possiblemoves.get(i), new EmptySquare("##"));
					if(!(isCheck("wK", possiblemoves.get(i)))) {
						board.put(kingPos, Kingpiece);
						board.put(possiblemoves.get(i), blockingPiece);
						return false;
					}
					board.put(possiblemoves.get(i), blockingPiece);
				}
				
				if(!(isCheck("wK", possiblemoves.get(i)))) {
					board.put(kingPos, Kingpiece); //put actual king back to its position
					return false; //there is at least one escape for the king, not checkmate yet!
				}
				
				//to deal with both the ifs being skipped; that current index will result in check
				else {
					continue;
				}
			}
		}
		
		else {
			for(int i = 0; i<possiblemoves.size(); i++) {
				
				if(board.get(possiblemoves.get(i)).getvalue().charAt(0)=='w') {
					blockingPiece=board.get(possiblemoves.get(i));
					board.put(possiblemoves.get(i), new EmptySquare("##"));
					if(!(isCheck("bK", possiblemoves.get(i)))) {
						board.put(kingPos, Kingpiece);
						board.put(possiblemoves.get(i), blockingPiece);
						return false;
					}
					board.put(possiblemoves.get(i), blockingPiece);
				}
				
				if(!(isCheck("bK", possiblemoves.get(i)))) {
					board.put(kingPos, Kingpiece); //put actual king back to its position
					return false; //there is at least one escape for the king, not checkmate yet!
				}
				
				else {
					return true;
				}
			}
		}
		return true; //no escape routes, checkmate!!!
	}
	
	
	/**
	 * isCheck is a function to check if the king at its current position can be captured by the opponent if not moved
	 * @param  kingvalue       which king it is
	 * @param  kingposition    is the position of that king
	 * 
	 * @return  true if the has an escape but if not moved will be captured, else false. 
	 */
	
	private static boolean isCheck(String kingvalue, String kingposition) {
		Piece opponentPiece=null;
		if(kingvalue.charAt(0)=='w') {  //white piece's king
			for (String opponentPiecePos: board.keySet()) {
				 opponentPiece = board.get(opponentPiecePos);
				if(opponentPiece.getvalue().charAt(0)=='b') {
					if(opponentPiece.isMoveValid(opponentPiecePos, kingposition)) {
						return true;
						
					}
				}
				
			}
			//at this point, none of the opponent's pieces target the king
			return false;
				
		}
		
		else {  //black piece's king
			for (String opponentPiecePos: board.keySet()) {
				opponentPiece= board.get(opponentPiecePos);
				if(opponentPiece.getvalue().charAt(0)=='w') {
					if(opponentPiece.isMoveValid(opponentPiecePos, kingposition)) {
						return true;
					}
				}
			}
			return false;
		}
		
	}
	
	
	//to get king's position:
	private static String kingPosition(char color) {
		Piece kingPiece=null;
		for(String kingpos:board.keySet()) {
			kingPiece=board.get(kingpos);
			
			if(color=='b') {
				if(kingPiece.getvalue().charAt(0)=='b' && kingPiece.getvalue().charAt(1)=='K') {
					return kingpos;
					
				}
				
			}
			else {
				if(kingPiece.getvalue().charAt(0)=='w' && kingPiece.getvalue().charAt(1)=='K') {
					return kingpos;
					
				}
				
			}
				
		}
		
		return null;		

	}
	
	
	private static void refresh_EnPassant(boolean is_white_move) {
		
		Piece pawns = null;
		Pawn singlepawn = null;
		
		if(is_white_move) { //set black pawns' canEnPassant to false
			for(String currentpiece : board.keySet()) {
				pawns = board.get(currentpiece);
				if(currentpiece.charAt(1) == '5' && pawns.getvalue().equals("bp")) {
					singlepawn = (Pawn) pawns;
					singlepawn.canEnPassant = false;
				}
				else {
					continue;
				}
			}
		}
		else {
			for(String currentpiece : board.keySet()) {
				pawns = board.get(currentpiece);
				if(currentpiece.charAt(1) == '4' && pawns.getvalue().equals("wp")) {
					singlepawn = (Pawn) pawns;
					singlepawn.canEnPassant = false;
				}
				else {
					continue;
				}
			}
		}
		
	}
	
	/**
	 * EnPassant performs the En Passant rule of chess, where the pawn can be killed by the opponent pawn if it uses the 2 boxes move to prevent the capture.
	 * @param oldPos
	 * @param newPos
	 * 
	 * @return  true    if En Passant occurs and false otherwise. 
	 */
	
	private static boolean EnPassant(String oldPos, String newPos, boolean is_white_move) {
		
		if(!(board.containsKey(oldPos)) || !(board.containsKey(newPos))) { //either of the entered positions are not in the bounds of the board
			return false;
		}
		
		Piece piece_at_oldPos = board.get(oldPos);
		Piece piece_at_capturePos = null; //Pawn piece to be captured due to enpassant
		Pawn pawn_at_capturePos = null;
		String position = null; //position of piece to be captured (NOT equal to newPos in EnPassant case)
		
		
		if(is_white_move) {
			
			if(oldPos.charAt(1) != '5' || newPos.charAt(1) != '6') { //only a white pawn in row 5 can do an enpassant capture
				return false;
			}
			
			if((newPos.charAt(0)-oldPos.charAt(0) == 1) && (newPos.charAt(1)-oldPos.charAt(1) == 1)) { //check if a diagonal move on right side
				position = Character.toString(newPos.charAt(0)) + oldPos.charAt(1);
				if(board.containsKey(position) == false) {
					return false;
				}
				piece_at_capturePos = board.get(position);
			} 
			else if((oldPos.charAt(0)-newPos.charAt(0) == 1) && (newPos.charAt(1)-oldPos.charAt(1) == 1)) { //check if diagonal move on left side
				position = Character.toString(newPos.charAt(0)) + oldPos.charAt(1);
				if(board.containsKey(position) == false) {
					return false;
				}
				piece_at_capturePos = board.get(position);
			}
			else {
				return false;
			}
			
			if(!(piece_at_oldPos instanceof Pawn) || !(piece_at_capturePos instanceof Pawn)) {
				return false; //EnPassant can only involve two pawns
			}
			pawn_at_capturePos = (Pawn) piece_at_capturePos;
			
			if(pawn_at_capturePos.canEnPassant == false) {
				return false;
			}
			else {
				//move piece to newPos
				Chess.board.put(newPos, piece_at_oldPos);
				
				//make oldPos an empty box
				if(Chess.isBlackBox(oldPos.charAt(0), oldPos.charAt(1)-'0')) {
					Chess.board.put(oldPos, new EmptySquare("##"));
				}
				else {
					Chess.board.put(oldPos, new EmptySquare("  "));
				}
				
				//make capturePos an empty box (capture the pawn: EnPassant!!!)
				if(Chess.isBlackBox(position.charAt(0), position.charAt(1)-'0')) {
					Chess.board.put(position, new EmptySquare("##"));
				}
				else {
					Chess.board.put(position, new EmptySquare("  "));
				}
				printboard();
				return true;
			}
			
		}
		else { //black move
			
			if(oldPos.charAt(1) != '4' || newPos.charAt(1) != '3') { //only a black pawn in row 4 can do an enpassant capture
				return false;
			}
			
			if((newPos.charAt(0)-oldPos.charAt(0) == 1) && (oldPos.charAt(1)-newPos.charAt(1) == 1)) {
				position = Character.toString(newPos.charAt(0)) + oldPos.charAt(1);
				if(board.containsKey(position) == false) {
					return false;
				}
				piece_at_capturePos = board.get(position);
			}
			else if((oldPos.charAt(0)-newPos.charAt(0) == 1) && (oldPos.charAt(1)-newPos.charAt(1) == 1)) {
				position = Character.toString(newPos.charAt(0)) + oldPos.charAt(1);
				if(board.containsKey(position) == false) {
					return false;
				}
				piece_at_capturePos = board.get(position);
			}
			else {
				return false;
			}
			
			if(!(piece_at_oldPos instanceof Pawn) || !(piece_at_capturePos instanceof Pawn)) {
				return false; //EnPassant can only involve two pawns
			}
			pawn_at_capturePos = (Pawn) piece_at_capturePos;
			
			if(pawn_at_capturePos.canEnPassant == false) {
				return false;
			}
			else {
				//move piece to newPos
				Chess.board.put(newPos, piece_at_oldPos);
				
				//make oldPos an empty box
				if(Chess.isBlackBox(oldPos.charAt(0), oldPos.charAt(1)-'0')) {
					Chess.board.put(oldPos, new EmptySquare("##"));
				}
				else {
					Chess.board.put(oldPos, new EmptySquare("  "));
				}
				
				//make capturePos an empty box (capture the pawn: EnPassant!!!)
				if(Chess.isBlackBox(position.charAt(0), position.charAt(1)-'0')) {
					Chess.board.put(position, new EmptySquare("##"));
				}
				else {
					Chess.board.put(position, new EmptySquare("  "));
				}
				printboard();
				return true;
			}
			
		}
		
	}
	
	/**
	 * castling performs the castling rule of chess for between the King and Rook. 
	 * @param oldPos
	 * @param newPos
	 * 
	 * @return  true if castled and false otherwise.
	 */
	
	
	private static boolean castling(String oldPos, String newPos) {
		
		Piece KingPiece, RookPiece;
		String oldRookpos, newRookpos;
		
		KingPiece = board.get(oldPos);
		if(!(KingPiece instanceof King)) {
			return false;       //castling cannot be done on any other piece apart from King. 
		}
		
		String kingvalue = KingPiece.getvalue();
		
		if((((oldPos.equals("e1")) && (newPos.equals("c1") || newPos.equals("g1"))) || ((oldPos.equals("e8")) && (newPos.equals("c8") || newPos.equals("g8")))) && KingPiece instanceof King) {
						
			if(newPos.equals("c1")) {
				oldRookpos = "a1";
				RookPiece = board.get(oldRookpos);
				newRookpos = "d1";
				if(isCheck(kingvalue, "e1") || isCheck(kingvalue, "d1") || isCheck(kingvalue, "c1")) { return false; }
			}
			else if(newPos.equals("g1")) {
				oldRookpos = "h1";
				RookPiece = board.get(oldRookpos);
				newRookpos = "f1";
				if(isCheck(kingvalue, "e1") || isCheck(kingvalue, "f1") || isCheck(kingvalue, "g1")) { return false; }
			}
			else if(newPos.equals("c8")) {
				oldRookpos = "a8";
				RookPiece = board.get(oldRookpos);
				newRookpos = "d8";
				if(isCheck(kingvalue, "e8") || isCheck(kingvalue, "d8") || isCheck(kingvalue, "c8")) { return false; }
			}
			else { //newPos is "g8"
				oldRookpos = "h8";
				RookPiece = board.get(oldRookpos);
				newRookpos = "f8";
				if(isCheck(kingvalue, "e8") || isCheck(kingvalue, "f8") || isCheck(kingvalue, "g8")) { return false; }
			}
			
			if(RookPiece instanceof Rook) {	
				if(KingPiece.hasMoved() == false && RookPiece.hasMoved() == false) {
					if(RookPiece.isPathEmpty(oldRookpos, oldPos) == true) { //check if path between king and rook is empty
						/*all conditions met, now castle*/
						KingPiece.move(oldPos, newPos, '0');
						KingPiece.sethasMoved(true);
						RookPiece.move(oldRookpos, newRookpos, '0');
						RookPiece.sethasMoved(true);
						printboard();
						return true;
					}
					else {
						return false; //cannot castle if path between king and rook not empty
					}
				}
				else {
					return false; //cannot castle if king or rook have moved before
				}
			}
			else {
				return false; //cannot castle king with any piece other than rook
			}
		}
		else {
			return false;
		}
	}
	

}
