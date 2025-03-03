package chess;

import java.util.*;

public class Chess {

    enum Player { white, black }
    
    public static HashMap<String, Piece> board = new HashMap<>(70);
    private static boolean is_white_move = true;

    public static ReturnPlay play(String move) {
        ReturnPlay returnPlay = new ReturnPlay();

        String[] inputstr_as_arr = move.split(" ");
        if (inputstr_as_arr.length < 2) {
            if ("resign".equals(move)) {
                returnPlay.message = is_white_move ? ReturnPlay.Message.RESIGN_BLACK_WINS : ReturnPlay.Message.RESIGN_WHITE_WINS;
                returnPlay.piecesOnBoard = getBoardAsList();
                return returnPlay;
            }
            returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
            returnPlay.piecesOnBoard = getBoardAsList();
            return returnPlay;
        }
        
        String oldPos = inputstr_as_arr[0];
        String newPos = inputstr_as_arr[1];
        String promotion = inputstr_as_arr.length > 2 ? inputstr_as_arr[2] : null;

        if ("draw?".equals(promotion)) {
            returnPlay.message = ReturnPlay.Message.DRAW;
            returnPlay.piecesOnBoard = getBoardAsList();
            return returnPlay;
        }
        
        Piece piece_oldPos = board.get(oldPos);

        if ((is_white_move && piece_oldPos.getvalue().charAt(0) == 'w') ||
            (!is_white_move && piece_oldPos.getvalue().charAt(0) == 'b')) {

            boolean is_move_valid = piece_oldPos.isMoveValid(oldPos, newPos);
            if (is_move_valid) {
                piece_oldPos.move(oldPos, newPos, promotion != null ? promotion.charAt(0) : '0');
                piece_oldPos.sethasMoved(true);
                is_white_move = !is_white_move;

                returnPlay.piecesOnBoard = getBoardAsList();
                return returnPlay;
            }
        }
        returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
        returnPlay.piecesOnBoard = getBoardAsList();
        return returnPlay;
    }

    public static ReturnPlay start() {
        initboard();
        is_white_move = true;

        ReturnPlay returnPlay = new ReturnPlay();
        returnPlay.piecesOnBoard = getBoardAsList();
        return returnPlay;
    }

    private static void initboard() {
        for (char alpha = 'a'; alpha <= 'h'; alpha++) {
            for (int num = 1; num <= 8; num++) {
                String filerank = Character.toString(alpha) + num;
                board.put(filerank, isBlackBox(alpha, num) ? new EmptySquare("##") : new EmptySquare("  "));
                
                if (num == 8 || num == 7) {
                    addPieces(filerank, num, 'b');
                }
                if (num == 1 || num == 2) {
                    addPieces(filerank, num, 'w');
                }
            }
        }
    }
    

    private static void addPieces(String filerank, int num, char color) {
        String prefix = color == 'b' ? "b" : "w";
        switch (filerank.charAt(0)) {
            case 'a': case 'h':
                board.put(filerank, new Rook(prefix + "R"));
                break;
            case 'b': case 'g':
                board.put(filerank, new Knight(prefix + "N"));
                break;
            case 'c': case 'f':
                board.put(filerank, new Bishop(prefix + "B"));
                break;
            case 'd':
                board.put(filerank, new Queen(prefix + "Q"));
                break;
            case 'e':
                board.put(filerank, new King(prefix + "K"));
                break;
        }
        if (num == 7 || num == 2) {
            board.put(filerank, new Pawn(prefix + "p"));
        }
    }

    public static boolean isBlackBox(char alpha, int num) {
        return ((num % 2 == 1 && "aceg".indexOf(alpha) >= 0) ||
                (num % 2 == 0 && "bdfh".indexOf(alpha) >= 0));
    }

    public static ArrayList<ReturnPiece> getBoardAsList() {
        ArrayList<ReturnPiece> list = new ArrayList<>();
        for (char file = 'a'; file <= 'h'; file++) {
            for (int rank = 1; rank <= 8; rank++) {
                String filerank = file + Integer.toString(rank);
                Piece piece = board.get(filerank);

                if (piece instanceof EmptySquare) {
                    continue;
                }

                ReturnPiece rp = new ReturnPiece();
                rp.pieceFile = ReturnPiece.PieceFile.valueOf(String.valueOf(file));
                rp.pieceRank = rank;

                switch (piece.getvalue().charAt(1)) {
                    case 'p': rp.pieceType = piece.getvalue().charAt(0) == 'w' ? ReturnPiece.PieceType.WP : ReturnPiece.PieceType.BP; break;
                    case 'R': rp.pieceType = piece.getvalue().charAt(0) == 'w' ? ReturnPiece.PieceType.WR : ReturnPiece.PieceType.BR; break;
                    case 'N': rp.pieceType = piece.getvalue().charAt(0) == 'w' ? ReturnPiece.PieceType.WN : ReturnPiece.PieceType.BN; break;
                    case 'B': rp.pieceType = piece.getvalue().charAt(0) == 'w' ? ReturnPiece.PieceType.WB : ReturnPiece.PieceType.BB; break;
                    case 'Q': rp.pieceType = piece.getvalue().charAt(0) == 'w' ? ReturnPiece.PieceType.WQ : ReturnPiece.PieceType.BQ; break;
                    case 'K': rp.pieceType = piece.getvalue().charAt(0) == 'w' ? ReturnPiece.PieceType.WK : ReturnPiece.PieceType.BK; break;
                }
                list.add(rp);
            }
        }
        return list;
    }
}
