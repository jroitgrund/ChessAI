
package chess;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

  Rook(pieceColor color) {
    super(color, moveShape.STRAIGHT, pieceType.R);
  }

  @Override
  List<Coord> getMoves() {
  }
  
}
