package chess;

import java.util.List;

public class Rook extends Piece {

  Rook(pieceColor color) {
    super(color, moveShape.STRAIGHT);
  }

  @Override
  List<Coord> getMoves() {
    // TODO Auto-generated method stub
    return null;
  }

  pieceType getType() {
    return pieceType.R;
  }

  @Override
  void move(Board b, Coord from, Coord to) {
    // TODO Auto-generated method stub

  }

}
