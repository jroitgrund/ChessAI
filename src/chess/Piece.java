package chess;

import java.util.List;

public abstract class Piece {
  pieceColor color;

  abstract boolean validMove(Board b, Coord from, Coord to);

  abstract pieceType getType();

  pieceColor getColor() {
    return color;
  }

  Piece(pieceColor color) {
    this.color = color;
  }

  abstract List<Coord> getMoves();

  void move(Board b, Coord from, Coord to) {
    b.clearPiece(from);
    b.setPiece(to, this);
  }
}
