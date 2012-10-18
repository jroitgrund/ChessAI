package chess;

import java.util.List;

public class Queen extends Piece {

  Queen(pieceColor color) {
    super(color, moveShape.EITHER);
  }

  @Override
  List<Coord> getMoves() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  pieceType getType() {
    return pieceType.Q;
  }

  @Override
  void move(Board b, Coord from, Coord to) {
    // TODO Auto-generated method stub

  }

}
