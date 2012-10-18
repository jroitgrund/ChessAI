package chess;

import java.util.List;

public class Knight extends Piece {

  Knight(pieceColor color) {
    super(color, moveShape.NONE);
  }

  pieceType getType() {
    return pieceType.N;
  }

  List<Coord> getMoves() {
    return null;
  }

  @Override
  void move(Board b, Coord from, Coord to) {
    // TODO Auto-generated method stub

  }

}
