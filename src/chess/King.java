
package chess;

public class King extends Piece {

  King(pieceColor color) {
    super(color, moveShape.EITHER, pieceType.K);
  }

  @Override
  protected boolean validDest(Board b, Coord from, Coord to) {
    return (freeDest(b, from, to) && Coord.distance(from, to) == 1);
  }

  @Override
  void move(Board b, Coord from, Coord to) {
    super.move(b, from, to);
    b.getInfo(getColor()).setKing(to);
  }
}
