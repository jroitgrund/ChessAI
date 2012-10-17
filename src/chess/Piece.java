package chess;

import java.util.List;

public abstract class Piece {
  pieceColor color;

  abstract boolean validMove(Board b, Coord from, Coord to);

  abstract pieceType getType();

  abstract List<Coord> getMoves();

  pieceColor getColor() {
    return color;
  }
  
  void move(Board b, Coord from, Coord to)
  {
    b.clearPiece(from);
    b.setPiece(to, this);
  }
}
