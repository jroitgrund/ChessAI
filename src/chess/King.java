
package chess;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

  King(pieceColor color) {
    super(color, moveShape.EITHER, pieceType.K, -1);
  }

  @Override
  protected boolean validDest(Board b, Coord from, Coord to) {
    if (!freeDest(b, from, to)) {
      return false;
    }
    if (Coord.distance(from, to) == 1) {
      return true;
    }
    if (to.getCol() == 2 && b.getInfo(getColor()).canCastleQueen()) {
      Coord rook = new Coord(from, -4, 0);
      return b.getPiece(rook).validMove(b, rook, new Coord(rook, 1, 0))
          && validMove(b, from, new Coord(from, -1, 0));
    }
    if (to.getCol() == 6 && b.getInfo(getColor()).canCastleKing()) {
      Coord rook = new Coord(from, 3, 0);
      return b.getPiece(rook).validMove(b, rook, new Coord(rook, -1, 0))
          && validMove(b, from, new Coord(from, 1, 0));
    }
    return false;
  }

  void move(Board b, Coord from, Coord to) {
    super.move(b, from, to);
    int diffCol = to.getCol() - from.getCol();
    if (diffCol == 2) {
      Coord rook = new Coord(to, 1, 0);
      b.setPiece(new Coord(to, -1, 0), b.getPiece(rook));
      b.clearPiece(rook);
    }
    else if (diffCol == -2) {
      Coord rook = new Coord(to, -2, 0);
      b.setPiece(new Coord(to, 1, 0), b.getPiece(rook));
      b.clearPiece(rook);
    }
    b.getInfo(getColor()).setKing(to);
    b.getInfo(getColor()).noCastleKing().noCastleQueen();
  }

  List<Coord> getMoves(Board b, Coord from) {
    int indexes[] = { -1, 0, 1 };
    int castleIndexes[] = { -2, 2 };
    List<Coord> l = new ArrayList<Coord>();
    l.addAll(getMovesDirection(b, from, indexes, indexes));
    l.addAll(getMovesDirection(b, from, castleIndexes, new int[] { 0 }));
    return l;
  }
}
