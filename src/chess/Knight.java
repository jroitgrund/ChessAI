
package chess;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

  Knight(pieceColor color) {
    super(color, moveShape.L, pieceType.N);
  }

  List<Coord> getMoves(Board b, Coord from) {
    ArrayList<Coord> l = new ArrayList<Coord>();
    int first_indexes[] = { -2, 2 };
    int second_indexes[] = { -1, 1 };
    this.getMoves_Direction(l, b, from, first_indexes, second_indexes);
    this.getMoves_Direction(l, b, from, first_indexes, first_indexes);
    this.getMoves_Direction(l, b, from, second_indexes, first_indexes);
    this.getMoves_Direction(l, b, from, second_indexes, second_indexes);
    return l;
  }
}
