package chess;

import java.util.List;

public class Bishop extends Piece {

  Bishop(pieceColor color) {
    super(color);
  }

  @Override
  boolean validThreat(Board b, Coord from, Coord to) {

    int lengthCol = Math.abs(from.getCol() - to.getCol());
    int lengthRow = Math.abs(from.getRow() - to.getRow());

    // If the move is not diagonal then it's illegal
    if (lengthCol != lengthRow) {
      return false;
    }
    
		if (!freeDest(b, from, to)) {
      return false;
    }

    boolean left = from.getCol() < to.getCol();
    boolean down = from.getRow() < to.getRow();
    int col;
    int row;
    if (left) {
      col = -1;
    } else {
      col = 1;
    }
    if (down) {
      row = -1;
    } else {
      row = 1;
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
  List<Coord> getMoves() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  pieceType getType() {
    // TODO Auto-generated method stub
    return null;
  }

}
