
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
    rowDiff = invert * (from.getRow() - to.getRow());
    realRow = getColor().ordinal() == 0 ? 7 : 0;
    realRow += from.getRow() * invert;
  }

  boolean enPassant;

  Pawn(pieceColor color) {
    super(color, moveShape.STRAIGHT, pieceType.P);
  }

  @Override
  protected boolean validThreat(Board b, Coord from, Coord to) {
    setInfo(b, from, to);
    return (colDiff == 1 && rowDiff == 1 && b.getPiece(to).getColor() == getColor()
        .opposite());
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
    if (realRow == 5
        && b.getInfo(getColor().opposite()).getEnPassant() == to.getCol()
        && rowDiff == 1 && colDiff == 1) {
      return true;
    }
    return false;
  }

  @Override
  List<Coord> getMoves() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  void move(Board b, Coord from, Coord to) {
    super.move(b, from, to);
    setInfo(b, from, to);
    if (rowDiff == 2) {
      b.getInfo(getColor()).setEnPassant(to.getCol());
    }
    else if (rowDiff == 1 && colDiff == 1 && b.getPiece(to) == null) {
      b.clearPiece(new Coord(to.getCol(), to.getRow() - 1 * invert));
    }
    else if (realRow + 1 == 7) {
      b.setPiece(to, new Queen(getColor()));
    }
  }
}
