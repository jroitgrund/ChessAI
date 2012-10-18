
package chess;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {

  Bishop(pieceColor color) {
    super(color, moveShape.DIAG, pieceType.B);
  }

  @Override
  List<Coord> getMoves(Board b, Coord from) {
    ArrayList<Coord> l = new ArrayList<Coord>();
    return getMoves_Direction(l, b, from);  
  }
}
