
package chess;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {

  Bishop(pieceColor color) {
    super(color, moveShape.DIAG, pieceType.B, 3);
  }

  List<Coord> getMoves(Board b, Coord from) {
    List<Coord> l = new ArrayList<Coord>();
    l.addAll(getMovesDirection(b, from, -1, -1));
    l.addAll(getMovesDirection(b, from, -1, 1));
    l.addAll(getMovesDirection(b, from, 1, -1));
    l.addAll(getMovesDirection(b, from, 1, 1));
    return l;
  }
}
