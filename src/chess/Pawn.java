package chess;

import java.util.List;

public class Pawn extends Piece {

  boolean enPassant;

  Pawn(pieceColor color) {
    super(color);
    shape = moveShape.STRAIGHT;
  }

  @Override
  boolean validThreat(Board b, Coord from, Coord to) {
    int lengthCol = Math.abs(from.getCol() - to.getCol());
    int lengthRow = Math.abs(from.getRow() - to.getRow());
    // If lengthCol > 0 the move is illegal
    if (lengthCol > 0) {
      return false;
    }
    // If lengthRow > 2 the move is illegal
    if (lengthRow > 0) {
      return false;
    }

    if (!freeDest(b, from, to)) {
      return false;
    }

    // If there is a piece that has the same color as this at to then
    // illegal
    if (b.getPiece(to).getColor() == getColor()) {
      return false;
    }

    boolean down = from.getRow() < to.getRow();
    int row;
    if (down) {
      row = -1;
    } else {
      row = 1;
    }

    // If length == 2 check for the special case
    if (lengthCol == 2) {
      if (!(from.getRow() == 1 || from.getRow() == 6)) {
        return false;
      }
      // If there is something on the vertical path from from to to return false
      Coord step = new Coord(from.getCol(), from.getRow() + row);
      if (b.isEmpty(step)) {
        return false;
      }
    }
    return true;
  }

  @Override
  pieceType getType() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  List<Coord> getMoves() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  boolean validMove(Board b, Coord from, Coord to) {
    // TODO Auto-generated method stub
    return false;
  }

}
