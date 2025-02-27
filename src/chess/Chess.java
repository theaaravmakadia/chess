package chess;

import java.util.*;
import pieces.*;

public class Chess {

    enum Player { white, black }
    
    public static HashMap<String, Piece> board = new HashMap<>(70);
    private static boolean is_white_move = true;
    private static boolean draw_proposal = false;

    /**
     * Plays the next move for whichever player has the turn.
     *
     * @param move String for next move, e.g. "a2 a3"
     *
     * @return A ReturnPlay instance that contains the result of the move.
     */
    public static ReturnPlay play(String move) {
        ReturnPlay returnPlay = new ReturnPlay();
        returnPlay.piecesOnBoard = new ArrayList<>();
        
        String[] inputstr_as_arr = move.split(" ");
        if (inputstr_as_arr.length < 2) {
            returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
            return returnPlay;
        }
        
        String oldPos = inputstr_as_arr[0];
        String newPos = inputstr_as_arr[1];
        String promotion = inputstr_as_arr.length > 2 ? inputstr_as_arr[2] : null;
        
        if (draw_proposal && "draw".equals(promotion)) {
            returnPlay.message = ReturnPlay.Message.DRAW;
            return returnPlay;
        }
        
        if ("resign".equals(move)) {
            returnPlay.message = is_white_move ? ReturnPlay.Message.RESIGN_BLACK_WINS : ReturnPlay.Message.RESIGN_WHITE_WINS;
            return returnPlay;
        }
        
        Piece piece_oldPos = board.get(oldPos);
        Piece piece_newPos = board.get(newPos);
        
        if ((is_white_move && piece_oldPos.getvalue().charAt(0) == 'w') ||
            (!is_white_move && piece_oldPos.getvalue().charAt(0) == 'b')) {
            
            boolean is_move_valid = piece_oldPos.isMoveValid(oldPos, newPos);
            if (is_move_valid) {
                piece_oldPos.move(oldPos, newPos, promotion != null ? promotion.charAt(0) : '0');
                piece_oldPos.sethasMoved(true);
                is_white_move = !is_white_move;
                printboard();
                returnPlay.message = ReturnPlay.Message.CHECK;
                return returnPlay;
            }
        }
        returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
        return returnPlay;
    }
    
    /**
     * This method resets the game and starts from scratch.
     */
    public static void start() {
        initboard();
        is_white_move = true;
        draw_proposal = false;
        printboard();
    }
    
    /**
     * Initializes the board with pieces.
     */
    private static void initboard() {
        for (char alpha = 'a'; alpha <= 'h'; alpha++) {
            for (int num = 1; num <= 8; num++) {
                String filerank = alpha + Integer.toString(num);
                board.put(filerank, isBlackBox(alpha, num) ? new EmptySquare("##") : new EmptySquare("  "));
                
                // Fill black side
                if (num == 8 || num == 7) {
                    addPieces(filerank, num, 'b');
                }
                
                // Fill white side
                if (num == 1 || num == 2) {
                    addPieces(filerank, num, 'w');
                }
            }
        }
    }
    
    private static void addPieces(String filerank, int num, char color) {
        String prefix = color == 'b' ? "b" : "w";
        if (num == 8 || num == 1) {
            switch (filerank.charAt(0)) {
                case 'a': case 'h': board.put(filerank, new Rook(prefix + "R")); break;
                case 'b': case 'g': board.put(filerank, new Knight(prefix + "N")); break;
                case 'c': case 'f': board.put(filerank, new Bishop(prefix + "B")); break;
                case 'd': board.put(filerank, new Queen(prefix + "Q")); break;
                case 'e': board.put(filerank, new King(prefix + "K")); break;
            }
        } else if (num == 7 || num == 2) {
            board.put(filerank, new Pawn(prefix + "p"));
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
     * Prints the current board state.
     */
    public static void printboard() {
        for (int num = 8; num >= 1; num--) {
            for (char alpha = 'a'; alpha <= 'h'; alpha++) {
                String filerank = Character.toString(alpha) + num;
                System.out.print(board.get(filerank).getvalue() + " ");
            }
            System.out.println(num);
        }
        System.out.println(" a  b  c  d  e  f  g  h\n");
    }

	
}