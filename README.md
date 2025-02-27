# Chess Game

## Overview
This project is a Java-based command-line chess game that allows two players to play against each other. It includes support for move validation, special chess rules like castling and en passant, and maintains the game state efficiently.

## Features
- **Full Chess Implementation**: All standard chess rules are implemented.
- **Move Validation**: Ensures only legal moves are allowed.
- **Special Moves**: Castling, en passant, and pawn promotion.
- **Game State Management**: Detects check, checkmate, and stalemate.
- **Command-Line Interface**: Players can input their moves via the console.

## Project Structure
```
chess/
│── src/
│   ├── chess/
│   │   ├── Chess.java
│   │   ├── PlayChess.java
│   │   ├── ReturnPlay.java
│   │   ├── ReturnPiece.java
│   ├── pieces/
│   │   ├── Piece.java
│   │   ├── King.java
│   │   ├── Queen.java
│   │   ├── Rook.java
│   │   ├── Bishop.java
│   │   ├── Knight.java
│   │   ├── Pawn.java
│   │   ├── EmptySquare.java
│── README.md
```

## Installation
1. **Clone the Repository**:
   ```sh
   git clone https://github.com/your-repo/chess.git
   cd chess
   ```
2. **Compile the Java Files**:
   ```sh
   cd src
   javac chess/*.java

   To remove the .class files:
   rm chess/*.class pieces/*.class
   ```
3. **Run the Game**:
   ```sh
   java chess.PlayChess
   ```

## How to Play
- The game starts by displaying the chessboard.
- Players take turns entering their moves in the format:
  ```
  a2 a3
  ```
- To promote a pawn, specify the piece:
  ```
  e7 e8 Q
  ```
- To resign, type:
  ```
  resign
  ```
- To propose a draw, type:
  ```
  draw?
  ```

## Special Rules Implemented
- **Castling**: Allows the king to move two squares with the rook under special conditions.
- **En Passant**: Capturing a pawn that moves two squares forward from its starting position.
- **Check & Checkmate**: The game automatically detects check and checkmate.
- **Stalemate**: The game detects when no legal moves are possible, leading to a draw.

## Authors
Developed by Aarav Makadia, Kuber Kupuriya.