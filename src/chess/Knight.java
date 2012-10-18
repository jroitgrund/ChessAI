
package chess;

import java.util.List;

public class Knight extends Piece {

  Knight(pieceColor color) {
    super(color, moveShape.L, pieceType.N);
  }

  List<Coord> getMoves() {
    return null;
  }
}
