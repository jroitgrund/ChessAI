
package chess;

import java.util.List;

public class Queen extends Piece {

  Queen(pieceColor color) {
    super(color, moveShape.EITHER, pieceType.Q);
  }

  @Override
  List<Coord> getMoves() {
    // TODO Auto-generated method stub
    return null;
  }
}
