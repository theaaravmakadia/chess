package chess;

import java.util.ArrayList;

/**
 * The ReturnPlay class is used to return the result of the move that is being played in the game.
 * @author Aarav Makadia
 * @author Kuber Kupuriya
 *
 */
class ReturnPlay {
	enum Message {ILLEGAL_MOVE, DRAW, 
				  RESIGN_BLACK_WINS, RESIGN_WHITE_WINS, 
				  CHECK, CHECKMATE_BLACK_WINS,	CHECKMATE_WHITE_WINS, 
				  STALEMATE};
	
	ArrayList<ReturnPiece> piecesOnBoard;
	Message message;
}