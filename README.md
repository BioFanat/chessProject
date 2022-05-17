# Design Document:

## TODO 5/13/2022

- Game Over Window/Pop-up
- Timer/Game Mechanics
- Display of Turn
- 

## Board 8x8, alternating black and white
 - Optional<Piece> selectedPiece
 - Enum Color turn
  
## Tile Class
 - Enum color (black, white, gray)
 - Optional<Piece> contains
 - Position: x,y
 - setOnAction:
   - If color of tile is grey, call MoveTo on selectedPiece
   - If piece’s color is set to the turn’s color, then showMoves of Piece

  
## Piece Class
  - ArrayList<Integer[]> validMoves - should have x and y
  - public moveTo(x, y)
  - Public showMoves
    - Set selected piece to this
    - Checks for special Moves
    - Clear gray squares
    - Sets possible possible move options to gray
  - Enum pieceType:
    - Public getInitialPossMoves()
      - Returns possible moves of this pieceType
    - Public getImageLoc()
      - Returns path to image location
    - Public ArrayListCheckifSpecialMoves

## GUI

  - Regular 8x8 black and white alterna
  - Moves counter
  - Timer
  - structure/interface
    - Menubar top
      - New game
      - Show Moves
      - Close




