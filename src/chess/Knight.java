package chess;

import java.util.List;

public class Knight extends Piece {

  Knight(pieceColor color) {
    super(color);
    // TODO Auto-generated constructor stub
  }

  boolean validThreat(Board b, Coord from, Coord to) {

    int lengthCol = Math.abs(from.getCol() - to.getCol());
    int lengthRow = Math.abs(from.getRow() - to.getRow());

    // If the move is not a l, then it is illegal
    if (lengthCol != 2 && lengthRow != 1 || lengthCol != 1 && lengthRow != 2) {
      return false;
    }
    return false;
  }

  pieceType getType() {
    return null;
  }

  List<Coord> getMoves() {
    return null;
  }

}
