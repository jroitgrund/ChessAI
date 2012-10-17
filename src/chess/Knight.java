package chess;

import java.util.List;

public class Knight extends Piece {

  Knight(pieceColor color) {
    super(color);
    // TODO Auto-generated constructor stub
  }

  boolean validThreat(Board b, Coord from, Coord to) {
    return true;
  }

  pieceType getType() {
    return null;
  }

  List<Coord> getMoves() {
    return null;
  }

}
