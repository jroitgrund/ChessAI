package chess;

import java.util.List;

public abstract class Piece {
  private pieceColor color;
  protected moveShape shape;

  protected enum moveShape {
    DIAG, STRAIGHT, EITHER;
  }

  abstract boolean validThreat(Board b, Coord from, Coord to);

  abstract boolean validMove(Board b, Coord from, Coord to);

  protected moveShape getMoveShape() {
    return shape;
  }

  // Checks if there is a king at to, if the move is actually a move,
  // if there is a piece at to that has the same color as this.
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

    // If there is a King at to then illegal
    if (b.getPiece(to).getType() == pieceType.K) {
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

  boolean freePath(Board b, Coord c1, Coord c2) {
    int colDiff = c1.getCol() - c2.getCol();
    int rowDiff = c1.getRow() - c2.getRow();
    switch (shape) {
    case DIAG:
      if (colDiff != rowDiff) {
        return false;
      }
      break;
    case STRAIGHT:
      if (colDiff != 0 && rowDiff != 0) {
        return false;
      }
      break;
    case EITHER:
      if (colDiff != rowDiff && colDiff != 0 && rowDiff != 0) {
        return false;
      }
      break;
    }

    int colStep = Integer.signum(colDiff);
    int rowStep = Integer.signum(rowDiff);

    for (Coord c = c1; !c.equals(c2); c = new Coord(c, colStep, rowStep)) {
      if (!b.isEmpty(c)) {
        return false;
      }
    }
    return true;
  }
}