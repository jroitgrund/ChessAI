package chess;

import java.util.List;

public class Rook extends Piece {

  Rook(pieceColor color) {
    super(color, moveShape.STRAIGHT);
  }

  @Override
  boolean validThreat(Board b, Coord from, Coord to) {
    return (freeDest(b, from, to) && freePath(b, from, to));
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
