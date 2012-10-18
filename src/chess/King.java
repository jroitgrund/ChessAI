
package chess;

import java.util.List;

public class King extends Piece {

  King(pieceColor color) {
    super(color, moveShape.EITHER);
  }

  @Override
  protected boolean validThreat(Board b, Coord from, Coord to) {
    return (freeDest(b, from, to) && Coord.distance(from, to) == 1);
  }

  @Override
  List<Coord> getMoves() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  pieceType getType() {
    return pieceType.K;
  }

  @Override
  void move(Board b, Coord from, Coord to) {
    // TODO Auto-generated method stub
  }
}
