
package chess;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {

  Bishop(pieceColor color) {
    super(color, moveShape.DIAG, pieceType.B);
  }

  List<Coord> getMoves(Board b, Coord from) {
    ArrayList<Coord> l = new ArrayList<Coord>();
    int first_indexes[] = new int[7];
    int second_indexes[] = new int[7];
    for (int i = 1; i < 8; i++)
      first_indexes[i] = i;
    for (int i = -1; i > -8; i--)
      second_indexes[i] = i;
    this.getMoves_Direction(l, b, from, first_indexes, second_indexes);
    this.getMoves_Direction(l, b, from, first_indexes, first_indexes);
    this.getMoves_Direction(l, b, from, second_indexes, first_indexes);
    this.getMoves_Direction(l, b, from, second_indexes, second_indexes);
    return l;
  }
}
