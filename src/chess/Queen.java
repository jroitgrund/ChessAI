package chess;

import java.util.List;

public class Queen extends Piece {

  Queen(pieceColor color) {
    super(color);
    // TODO Auto-generated constructor stub
  }

  @Override
  boolean validMove(Board b, Coord from, Coord to) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  pieceType getType() {
    return pieceType.Q;
  }

  @Override
  List<Coord> getMoves() {
    // TODO Auto-generated method stub
    return null;
  }

}
