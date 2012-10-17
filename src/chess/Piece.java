package chess;

import java.util.List;

public abstract class Piece {
  private pieceColor color;

  abstract boolean validThreat(Board b, Coord from, Coord to);

  protected boolean freeDest(Board b, Coord from, Coord to) {
    if (from.equals(to)) {
      return false;
    }

    if (b.getPiece(to) == null) {
      return true;
    }

    if (b.getPiece(to).getColor() == color) {
      return false;
    }

    return true;
  }

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
