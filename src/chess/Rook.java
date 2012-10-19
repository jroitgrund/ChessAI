
package chess;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

  Rook(pieceColor color) {
    super(color, moveShape.STRAIGHT, pieceType.R);
  }

  List<Coord> getMoves(Board b, Coord from) {
    List<Coord> l = new ArrayList<Coord>();
    l.addAll(getMovesDirection(b, from, -1, 0));
    l.addAll(getMovesDirection(b, from, 0, -1));
    l.addAll(getMovesDirection(b, from, 0, 1));
    l.addAll(getMovesDirection(b, from, 1, 0));
    return l;
  }
}
