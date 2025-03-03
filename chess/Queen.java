package chess;

import java.util.ArrayList;
import java.util.List;

/**
 * The Queen class is used to implement the Queen piece in the game of chess.
 * It combines the movement patterns of both the Rook and the Bishop.
 * 
 * @author Aarav Makadia
 * @author Kuber Kupuriya
 */
public class Queen extends Piece {

    //Kuber
    /**
     * Constructs a new Queen object with the specified value.
     * 
     * @param v a String representing the Queen's value (including color and type)
     */
    public Queen(String v) {
        super(v);
    }

    //Aarav
    /**
     * Determines if moving from oldPos to newPos is valid for the Queen.
     * The move is valid if it is either along a straight line or a diagonal, and if the path is clear.
     * 
     * @param oldPos the source position in algebraic notation
     * @param newPos the destination position in algebraic notation
     * @return true if the move is valid; false otherwise
     */
    public boolean isMoveValid(String oldPos, String newPos) {
        if (Chess.board.containsKey(newPos) == false)
            return false;
        
        // Get piece representations at source and destination.
        String a = Chess.board.get(oldPos).getvalue();
        String b = Chess.board.get(newPos).getvalue();
        
        // Check for valid Queen move: straight or diagonal and not staying in place.
        if (((oldPos.charAt(0) == newPos.charAt(0) || oldPos.charAt(1) == newPos.charAt(1)) ||
             (Math.abs(oldPos.charAt(0) - newPos.charAt(0)) == Math.abs(oldPos.charAt(1) - newPos.charAt(1))))
             && !(oldPos.equals(newPos))) {
            // If destination is empty, verify the path is clear.
            if (Chess.board.get(newPos).getvalue().equals("  ") || 
                Chess.board.get(newPos).getvalue().equals("##")) {
                if (isPathEmpty(oldPos, newPos))
                    return true;
                else
                    return false;
            } else {
                // If destination is occupied, check that it is an opponent's piece.
                if (a.charAt(0) == b.charAt(0))
                    return false;
                else {
                    if (isPathEmpty(oldPos, newPos))
                        return true;
                    else
                        return false;
                }
            }
        } else {
            return false;
        }
    }

    //Kuber
    /**
     * Moves the Queen from the source position to the destination position.
     * Updates the board by placing the Queen at the new position and clearing the source.
     * 
     * @param oldPos the source position in algebraic notation
     * @param newPos the destination position in algebraic notation
     * @param p an unused promotion character (for signature consistency)
     */
    public void move(String oldPos, String newPos, char p) {
        Piece a = Chess.board.get(oldPos);
        Chess.board.put(newPos, a);
        if (Chess.isBlackBox(oldPos.charAt(0), oldPos.charAt(1) - '0'))
            Chess.board.put(oldPos, new EmptySquare("##"));
        else
            Chess.board.put(oldPos, new EmptySquare("  "));
    }

    //Aarav
    /**
     * Checks if the path from oldPos to newPos is clear for the Queen's move.
     * For diagonal moves, it uses the bishop-like path checking; for straight moves, it uses rook-like path checking.
     * 
     * @param oldPos the source position in algebraic notation
     * @param newPos the destination position in algebraic notation
     * @return true if the path is clear; false otherwise
     */
    public boolean isPathEmpty(String oldPos, String newPos) {
        if (Math.abs(oldPos.charAt(0) - newPos.charAt(0)) == Math.abs(oldPos.charAt(1) - newPos.charAt(1))) {
            return isBishopPathEmpty(oldPos, newPos);
        } else if (oldPos.charAt(0) == newPos.charAt(0) || oldPos.charAt(1) == newPos.charAt(1)) {
            return isRookPathEmpty(oldPos, newPos);
        }
        return false;
    }

    //Kuber
    /**
     * Checks if the diagonal (bishop-like) path from oldPos to newPos is clear.
     * 
     * @param oldPos the starting position in algebraic notation
     * @param newPos the ending position in algebraic notation
     * @return true if all intermediate squares are empty; false otherwise
     */
    public static boolean isBishopPathEmpty(String oldPos, String newPos) {
        List<String> l = getBoxesInBetween(oldPos, newPos);
        for (String s : l) {
            if (!(Chess.board.get(s).getvalue().equals("##") || Chess.board.get(s).getvalue().equals("  ")))
                return false;
        }
        return true;
    }

    //Aarav
    /**
     * Retrieves a list of all board positions between oldPos and newPos along a diagonal.
     * 
     * @param oldPos the starting position in algebraic notation
     * @param newPos the ending position in algebraic notation
     * @return a List of positions (as Strings) that lie between oldPos and newPos
     */
    private static List<String> getBoxesInBetween(String oldPos, String newPos) {
        List<String> l = new ArrayList<>();
        int a = (int) oldPos.charAt(0);
        int b = (int) newPos.charAt(0);
        int c = oldPos.charAt(1) - '0';
        int d = newPos.charAt(1) - '0';
        int e = Math.abs(b - a) / (b - a);
        int f = Math.abs(d - c) / (d - c);
        for (int g = 1; g < Math.abs(b - a); g++) {
            char h = (char) (a + g * e);
            int i = c + g * f;
            l.add(Character.toString(h) + Integer.toString(i));
        }
        return l;
    }

    //Kuber
    /**
     * Checks if the straight-line (rook-like) path from oldPos to newPos is clear.
     * It handles both vertical and horizontal movements.
     * 
     * @param oldPos the source position in algebraic notation
     * @param newPos the destination position in algebraic notation
     * @return true if the path is clear; false otherwise
     */
    public static boolean isRookPathEmpty(String oldPos, String newPos) {
        if (oldPos.charAt(0) == newPos.charAt(0)) {
            int a = oldPos.charAt(1) - '0';
            int b = newPos.charAt(1) - '0';
            if (a < b) {
                for (int g = a + 1; g < b; g++) {
                    if (!isBoxEmpty(oldPos.charAt(0), g)) {
                        return false;
                    }
                }
            } else {
                for (int g = b + 1; g < a; g++) {
                    if (!isBoxEmpty(oldPos.charAt(0), g)) {
                        return false;
                    }
                }
            }
        } else if (oldPos.charAt(1) == newPos.charAt(1)) {
            char a = oldPos.charAt(0);
            char b = newPos.charAt(0);
            if (a < b) {
                for (char g = (char) (a + 1); g < b; g++) {
                    if (!isBoxEmpty(g, oldPos.charAt(1) - '0')) {
                        return false;
                    }
                }
            } else {
                for (char g = (char) (b + 1); g < a; g++) {
                    if (!isBoxEmpty(g, oldPos.charAt(1) - '0')) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //Aarav
    /**
     * Checks if the board square at the specified column and row is empty.
     * 
     * @param a the column character
     * @param b the row number
     * @return true if the square is empty; false otherwise
     */
    private static boolean isBoxEmpty(char a, int b) {
        String s = a + "" + b;
        if (Chess.board.get(s).getvalue().equals("##") || 
            Chess.board.get(s).getvalue().equals("  ")) {
            return true;
        }
        return false;
    }
}
