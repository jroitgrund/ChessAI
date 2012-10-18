
package chess;

import java.util.List;

public class Knight extends Piece {

  Knight(pieceColor color) {
    super(color, moveShape.NONE, pieceType.N);
  }

  List<Coord> getMoves() {
    return null;
  }
}
