
package chess;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

  Rook(pieceColor color) {
    super(color, moveShape.STRAIGHT, pieceType.R, 5);
  }

  List<Coord> getMoves(Board b, Coord from) {
    List<Coord> l = new ArrayList<Coord>();
    l.addAll(getMovesDirection(b, from, -1, 0));
    l.addAll(getMovesDirection(b, from, 0, -1));
    l.addAll(getMovesDirection(b, from, 0, 1));
    l.addAll(getMovesDirection(b, from, 1, 0));
    return l;
  }

  void move(Board b, Coord from, Coord to) {
    super.move(b, from, to);
    if (from.equals(new Coord(0, 0)) || from.equals(new Coord(0, 7))) {
      b.getInfo(getColor()).noCastleQueen();
    }
    else if (from.equals(new Coord(7, 0)) || from.equals(new Coord(7, 7))) {
      b.getInfo(getColor()).noCastleKing();
    }
  }
}
