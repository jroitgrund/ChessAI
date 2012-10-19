
package chess;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {

  protected enum moveShape {
    DIAG, STRAIGHT, EITHER, NONE, L;
  }

  private pieceColor color;

  private pieceType  type;

  private moveShape  shape;

  private int        value;

  Piece(pieceColor color, moveShape shape, pieceType type, int value) {
    this.color = color;
    this.shape = shape;
    this.type = type;
    this.value = value;
  }

  int getValue() {
    return value;
  }

  pieceType getType() {
    return type;
  }

  pieceColor getColor() {
    return color;
  }

  List<Coord> getMovesDirection(Board b, Coord from, int dCol, int dRow) {
    List<Coord> l = new ArrayList<Coord>();
    Coord to = from;
    while ((to = new Coord(to, dCol, dRow)).inBoard())
      if (validMove(b, from, to)) {
        l.add(to);
      }
      // There is already something on the path of the piece.
      else {
        break;
      }
    return l;
  }

  List<Coord> getMovesDirection(Board b, Coord from, int colIndexes[],
      int rowIndexes[]) {
    List<Coord> l = new ArrayList<Coord>();
    Coord to = from;
    for (int i = 0; i < colIndexes.length; i++) {
      for (int j = 0; j < rowIndexes.length; j++) {
        to = new Coord(from, colIndexes[i], rowIndexes[j]);
        if (to.inBoard() && validMove(b, from, to)) {
          l.add(to);
        }
      }
    }
    return l;
  }

  abstract List<Coord> getMoves(Board b, Coord from);

  protected moveShape getMoveShape() {
    return shape;
  }

  protected boolean validThreat(Board b, Coord from, Coord to) {
    return validDest(b, from, to);
  }

  boolean validDest(Board b, Coord from, Coord to) {
    return (freeDest(b, from, to) && freePath(b, from, to));
  }

  boolean validMove(Board b, Coord from, Coord to) {
    if (!validDest(b, from, to)) {
      return false;
    }
    if (b.getPiece(to) != null && b.getPiece(to).getType() == pieceType.K) {
      return false;
    }
    Board bPrime = new Board(b);
    move(bPrime, from, to);
    if (from.equals(new Coord(4, 7)) && to.equals(new Coord(5, 6))) {
      System.out.println("Checking if king can attack queen:");
    }
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        Coord c = new Coord(i, j);
        if (!bPrime.isEmpty(c)
            && bPrime.getPiece(c).getColor() == getColor().opposite()
            && bPrime.getPiece(c).validThreat(bPrime, c,
                bPrime.getInfo(getColor()).getKing())) {
          if (from.equals(new Coord(4, 7)) && to.equals(new Coord(5, 6))) {
            System.out.println("King would be exposed to [" + c.getCol() + ", "
                + c.getRow() + "]");
          }
          // System.out.println("King would be exposed");
          return false;
        }
      }
    }
    return true;
  }

  protected boolean freeDest(Board b, Coord from, Coord to) {
    if (from.equals(to)) {
      return false;
    }
    if (b.getPiece(to) != null && b.getPiece(to).getColor() == color) {
      return false;
    }
    return true;
  }

  protected boolean freePath(Board b, Coord c1, Coord c2) {
    int colDiff = c2.getCol() - c1.getCol();
    int rowDiff = c2.getRow() - c1.getRow();
    switch (shape) {
      case DIAG:
        if (Math.abs(colDiff) != Math.abs(rowDiff)) {
          return false;
        }
        break;
      case STRAIGHT:
        if (colDiff != 0 && rowDiff != 0) {
          return false;
        }
        break;
      case EITHER:
        if (Math.abs(colDiff) != Math.abs(rowDiff) && colDiff != 0
            && rowDiff != 0) {
          return false;
        }
        break;
      case L:
        return (Math.abs(colDiff) == 1 && Math.abs(rowDiff) == 2 || Math
            .abs(colDiff) == 2 && Math.abs(rowDiff) == 1);
      default:
    }
    int colStep = Integer.signum(colDiff);
    int rowStep = Integer.signum(rowDiff);
    for (Coord c = new Coord(c1, colStep, rowStep); !c.equals(c2); c = new Coord(
        c, colStep, rowStep)) {
      if (!b.isEmpty(c)) {
        /*
         * System.out.println("Coordinate [" + c.getCol() + ", " + c.getRow() +
         * "] is blocking path");
         */
        return false;
      }
    }
    return true;
  }

  void move(Board b, Coord from, Coord to) {
    b.setPiece(to, this);
    b.clearPiece(from);
  }

  public String toString() {
    return getType().toString();
  }
}
