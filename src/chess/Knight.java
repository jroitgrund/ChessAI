package chess;

import java.util.List;

public class Knight extends Piece {

  Knight(pieceColor color) {
    super(color, moveShape.NONE);
  }

  List<Coord> getMoves() {
    return null;
  }

  pieceType getType() {
    return pieceType.N;
  }

  @Override
  void move(Board b, Coord from, Coord to) {
    // TODO Auto-generated method stub

  }

}
