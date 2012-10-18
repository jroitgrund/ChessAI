
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

  Piece(pieceColor color, moveShape shape, pieceType type) {
    this.color = color;
    this.shape = shape;
    this.type = type;
  }

  pieceType getType() {
    return type;
  }

  pieceColor getColor() {
    return color;
  }

  void getMoves_Direction(ArrayList<Coord> l, Board b, Coord from,
      int first_indexes[], int second_indexes[]) {
    for (int i = 0; i + from.getCol() < 8 && i + from.getCol() >= 0
        && i + from.getRow() < 8 && i + from.getRow() >= 0
        && i < first_indexes.length; i++) {
      for (int j = 0; j + from.getCol() < 8 && j + from.getCol() >= 0
          && j + from.getRow() < 8 && j + from.getRow() >= 0
          && j < first_indexes.length; j++) {
        Coord to = new Coord(from, first_indexes[i], first_indexes[j]);
        if (this.validMove(b, from, to)) {
          l.add(to);
        }
      }
    }
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
      System.out.println("valid dest returned false");
      return false;
    }
    if (b.getPiece(to) != null && b.getPiece(to).getType() == pieceType.K) {
      System.out.println("king in destination square");
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
          System.out.println("King would be exposed");
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
          System.out.println("Incorrect diagonal move: rowDiff != colDiff");
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
        System.out.println("Coordinate [" + c.getCol() + ", " + c.getRow()
            + "] is blocking path");
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
