package chess;

import java.util.List;

public class Rook extends Piece {

  Rook(pieceColor color) {
    super(color, moveShape.STRAIGHT);
  }

  @Override
  boolean validThreat(Board b, Coord from, Coord to) {

    int lengthCol = Math.abs(from.getCol() - to.getCol());
    int lengthRow = Math.abs(from.getRow() - to.getRow());

    if (!freeDest(b, from, to)) {
      return false;
    }

    boolean vertical = from.getRow() != from.getCol();
    boolean left = from.getCol() < to.getCol();
    boolean down = from.getRow() < to.getRow();
    int col;
    int row;
    if (vertical) {
      if (down) {
        row = -1;
        col = 0;
      } else {
        row = 1;
        col = 0;
      }
    } else {
      if (left) {
        col = -1;
        row = 0;
      } else {
        col = 1;
        row = 0;
      }
    }

    // If there is something on the diagonal path from from to to return false
    for (int i = 1; i < lengthCol - 1; i++) {
      Coord step = new Coord(from.getCol() + col, from.getRow() + row);
      if (b.isEmpty(step)) {
        return false;
      }
      col += Integer.signum(col);
      row += Integer.signum(row);
    }

    return true;
  }

  @Override
  pieceType getType() {
    return pieceType.R;
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

  @Override
  void move(Board b, Coord from, Coord to) {
    // TODO Auto-generated method stub
    
  }

}
