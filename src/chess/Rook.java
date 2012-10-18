package chess;

import java.util.List;

public class Rook extends Piece {

  Rook(pieceColor color) {
    super(color, moveShape.STRAIGHT);
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
  void move(Board b, Coord from, Coord to) {
    // TODO Auto-generated method stub
    
  }

}
