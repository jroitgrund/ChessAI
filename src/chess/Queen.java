
package chess;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

  Queen(pieceColor color) {
    super(color, moveShape.EITHER, pieceType.Q);
  }

  List<Coord> getMoves(Board b, Coord from) {
    ArrayList<Coord> l = new ArrayList<Coord>();
    int first_indexes[] = new int[7];
    int second_indexes[] = new int[7];
    int third_indexes[] = new int[1];
    for (int i = 1; i < 8; i++)
      first_indexes[i] = i;
    for (int i = -1; i > -8; i--)
      second_indexes[i] = i;
    third_indexes[0] = 0;
    this.getMoves_Direction(l, b, from, first_indexes, third_indexes);
    this.getMoves_Direction(l, b, from, second_indexes, third_indexes);
    this.getMoves_Direction(l, b, from, third_indexes, first_indexes);
    this.getMoves_Direction(l, b, from, third_indexes, second_indexes);
    this.getMoves_Direction(l, b, from, first_indexes, second_indexes);
    this.getMoves_Direction(l, b, from, first_indexes, first_indexes);
    this.getMoves_Direction(l, b, from, second_indexes, first_indexes);
    this.getMoves_Direction(l, b, from, second_indexes, second_indexes);
    return l;
  }
}
