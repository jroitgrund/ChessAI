
package chess;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {

  Bishop(pieceColor color) {
    super(color, moveShape.DIAG, pieceType.B);
  }

  List<Coord> getMoves(Board b, Coord from) {
    List<Coord> l = new ArrayList<Coord>();
    getMovesDirection(l, b, from, -1, -1);
    getMovesDirection(l, b, from, -1, 1);
    getMovesDirection(l, b, from, 1, -1);
    getMovesDirection(l, b, from, 1, 1);
    return l;
  }
}
