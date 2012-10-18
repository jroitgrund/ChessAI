package chess;

import java.util.List;

public class Bishop extends Piece {

  Bishop(pieceColor color) {
    super(color, moveShape.DIAG);
  }

  @Override
  List<Coord> getMoves() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  pieceType getType() {
    return pieceType.B;
  }

  @Override
  void move(Board b, Coord from, Coord to) {
    // TODO Auto-generated method stub
    
  }

}
