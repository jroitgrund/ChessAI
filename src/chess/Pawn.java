
package chess;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

  int invert;

  int colDiff;

  int rowDiff;

  int realRow;

  private void setInfo(Board b, Coord from, Coord to) {
    invert = getColor().ordinal() == 0 ? 1 : -1;
    colDiff = Math.abs(from.getCol() - to.getCol());
    rowDiff = invert * (to.getRow() - from.getRow());
    realRow = getColor().ordinal() == 0 ? 0 : 7;
    realRow += from.getRow() * invert;
  }

  boolean enPassant;

  Pawn(pieceColor color) {
    super(color, moveShape.STRAIGHT, pieceType.P);
  }

  @Override
  protected boolean validThreat(Board b, Coord from, Coord to) {
    setInfo(b, from, to);
    return (colDiff == 1 && rowDiff == 1 && b.getPiece(to) != null && b
        .getPiece(to).getColor() == getColor().opposite());
  }

  protected boolean validDest(Board b, Coord from, Coord to) {
    setInfo(b, from, to);
    if (validThreat(b, from, to)) {
      return true;
    }
    if (!freeDest(b, from, to)) {
      return false;
    }
    if (rowDiff == 2 && realRow == 1 && colDiff == 0) {
      return freePath(b, from, to);
    }
    if (rowDiff == 1 && colDiff == 0) {
      return true;
    }
    if (realRow == 4
        && b.getInfo(getColor().opposite()).getEnPassant() == to.getCol()
        && rowDiff == 1 && colDiff == 1) {
      return true;
    }
    return false;
  }

  List<Coord> getMoves(Board b, Coord from) {
    ArrayList<Coord> l = new ArrayList<Coord>();
    int first_indexes[] = { 1, 2 };
    int second_indexes[] = { 0 };
    this.getMoves_Direction(l, b, from, first_indexes, second_indexes);
    this.getMoves_Direction(l, b, from, second_indexes, first_indexes);
    return l;
  }
}
