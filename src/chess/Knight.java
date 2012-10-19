
package chess;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

  Knight(pieceColor color) {
    super(color, moveShape.L, pieceType.N, 3);
  }

  List<Coord> getMoves(Board b, Coord from) {
    List<Coord> l = new ArrayList<Coord>();
    int doubleIndexes[] = { -2, 2 };
    int singleIndexes[] = { -1, 1 };
    l.addAll(getMovesDirection(b, from, singleIndexes, doubleIndexes));
    l.addAll(getMovesDirection(b, from, doubleIndexes, singleIndexes));
    return l;
  }
}
