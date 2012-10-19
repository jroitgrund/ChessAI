
package chess;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

  Rook(pieceColor color) {
    super(color, moveShape.STRAIGHT, pieceType.R);
  }

  List<Coord> getMoves(Board b, Coord from) {
    List<Coord> l = new ArrayList<Coord>();
    getMovesDirection(l, b, from, -1, 0);
    getMovesDirection(l, b, from, 0, -1);
    getMovesDirection(l, b, from, 0, 1);
    getMovesDirection(l, b, from, 1, 0);
    return l;
  }
}
