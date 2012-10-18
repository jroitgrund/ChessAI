
package chess;

import java.util.List;

public class Bishop extends Piece {

  Bishop(pieceColor color) {
    super(color, moveShape.DIAG, pieceType.B);
  }

  @Override
  List<Coord> getMoves() {
    // TODO Auto-generated method stub
    return null;
  }
}
