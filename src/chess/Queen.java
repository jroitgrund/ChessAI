
package chess;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

  Queen(pieceColor color) {
    super(color, moveShape.EITHER, pieceType.Q, 9);
  }

  List<Coord> getMoves(Board b, Coord from) {
    List<Coord> l = new ArrayList<Coord>();
    for (int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {
        if (i != 0 || j != 0) {
          l.addAll(getMovesDirection(b, from, i, j));
        }
      }
    }
    return l;
  }
}
