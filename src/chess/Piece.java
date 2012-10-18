
package chess;

import java.util.List;

public abstract class Piece {

  protected enum moveShape {
    DIAG, STRAIGHT, EITHER, NONE, L;
  }

  private pieceColor color;

  private moveShape  shape;

  Piece(pieceColor color, moveShape shape) {
    this.color = color;
    this.shape = shape;
  }

  abstract pieceType getType();

  pieceColor getColor() {
    return color;
  }

  abstract List<Coord> getMoves();

  protected moveShape getMoveShape() {
    return shape;
  }

  protected boolean validThreat(Board b, Coord from, Coord to) {
    return (freeDest(b, from, to) && freePath(b, from, to));
  }

  boolean validMove(Board b, Coord from, Coord to) {
    if (b.getPiece(to).getType() == pieceType.K) {
      return false;
    }
    Board bPrime = new Board(b);
    move(bPrime, from, to);
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        Coord c = new Coord(i, j);
        if (!bPrime.isEmpty(c)
            && bPrime.getPiece(c).validThreat(bPrime, c,
                bPrime.getInfo(color).getKing())) {
          return false;
        }
      }
    }
    return true;
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
    return true;
  }

  protected boolean freePath(Board b, Coord c1, Coord c2) {
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
      case L:
        if (colDiff != 1 && rowDiff != 2 || colDiff != 2 && rowDiff != 1) {
          return false;
        }
        else {
          return true;
        }
      default:
    }
    int colStep = Integer.signum(colDiff);
    int rowStep = Integer.signum(rowDiff);
    for (Coord c = new Coord(c1, colStep, rowStep); !c.equals(c2); c = new Coord(
        c, colStep, rowStep)) {
      if (!b.isEmpty(c)) {
        return false;
      }
    }
    return true;
  }

  abstract void move(Board b, Coord from, Coord to);

  public String toString() {
    return getType().toString();
  }
}
