package chess;

import java.util.List;

public class Pawn extends Piece {

  boolean enPassant;

  Pawn(pieceColor color) {
    super(color);
    // TODO Auto-generated constructor stub
  }

  @Override
  boolean validMove(Board b, Coord from, Coord to) {
    int length = Math.abs(from.getRow() - to.getRow())
        + Math.abs(from.getCol() - to.getCol());
    // If length > 2 the move is obviously illegal
    if (length > 2) {
      return false;
    }
    // If not a move then illegal
    if (length == 0) {
      return false;
    }
    // If length == 2 check for the special case
    if (length == 2 && Math.abs(from.getRow() - to.getRow()) < 2) {
      return false;
    }
    // If there is already a piece at to and it has the same color as this pawn
    if (b.getColor(to) == this.getColor()) {
      return false;
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

}
