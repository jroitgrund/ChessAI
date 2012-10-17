package chess;

import java.util.List;

public class Rook extends Piece {

  Rook(pieceColor color) {
    super(color);
    // TODO Auto-generated constructor stub
  }

  @Override
  boolean validMove(Board b, Coord from, Coord to) {
    int length = Math.abs(from.getRow() - to.getRow())
        + Math.abs(from.getCol() - to.getCol());
    // If not a move then illegal
    if (length == 0) {
      return false;
    }
    // If not diagonal then illegal
    if (length != Math.abs(from.getCol() - from.getCol())) {
      return false;
    }
    // If there is already a piece at to and it has the same color as this pawn
    if (b.getColor(to) == this.getColor()) {
      return false;
    }
    // If there is a piece on the line between from and to return false
    for (int i = 1; i < length; i++) {
      Coord nextPos = new Coord(from.getCol() + i, from.getRow() + i);
      if (!b.isEmpty(nextPos)) {
        return false;
      }
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

}
