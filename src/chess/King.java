
package chess;

import java.util.List;

public class King extends Piece {

  King(pieceColor color) {
    super(color, moveShape.EITHER, pieceType.K);
  }

  @Override
  protected boolean validDest(Board b, Coord from, Coord to) {
    return (freeDest(b, from, to) && Coord.distance(from, to) == 1);
  }

  List<Coord> getMoves(Board b, Coord from) {
    int indexes[] = { -1, 0, 1 };
    return getMovesDirection(b, from, indexes, indexes);
  }
}
