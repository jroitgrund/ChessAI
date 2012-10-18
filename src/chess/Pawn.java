
package chess;

import java.util.List;

public class Pawn extends Piece {

  boolean enPassant;

  Pawn(pieceColor color) {
    super(color, moveShape.STRAIGHT);
  }

  @Override
  protected boolean validThreat(Board b, Coord from, Coord to) {
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

  @Override
  void move(Board b, Coord from, Coord to) {
    // TODO Auto-generated method stub
  }
}
