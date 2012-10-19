
package chess;

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

  @Override
  void move(Board b, Coord from, Coord to) {
    setInfo(b, from, to);
    if (rowDiff == 2) {
      b.getInfo(getColor()).setEnPassant(to.getCol());
    }
    else if (rowDiff == 1 && colDiff == 1 && b.getPiece(to) == null) {
      b.clearPiece(new Coord(to.getCol(), to.getRow() - 1 * invert));
    }
    super.move(b, from, to);
    if (realRow + 1 == 7) {
      b.setPiece(to, new Queen(getColor()));
    }
  }

  Pawn(pieceColor color) {
    super(color, moveShape.STRAIGHT, pieceType.P, 1);
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
    return getMovesDirection(b, from, new int[] { 0, 1 }, new int[] { -1, -2,
        1, 2 });
  }
}
