package chess;

import java.util.List;

public class Pawn extends Piece {

  boolean enPassant;

  Pawn(pieceColor color) {
    super(color);
    // TODO Auto-generated constructor stub
  }

  @Override
  boolean validThreat(Board b, Coord from, Coord to) {
    int lengthCol = Math.abs(from.getCol() - to.getCol());
    int lengthRow = Math.abs(from.getRow() - to.getRow());
    // If lengthCol > 0 the move is illegal
    if (lengthCol > 0) {
      return false;
    }
    // If lengthRow > 2 the move is illegal
    if (lengthRow > 0) {
      return false;
    }
    
    // If not a move then illegal
    if (!freeDest(b, from, to)) {
      return false;
    }
    
    if (from.equals(to)) {
      return false;
    }
    
    // If length == 2 check for the special case
    if (lengthCol == 2) {
      if (!(from.getRow() == 1 || from.getRow() == 6)) {
        return false;
      }
      // If there is something on the vertical path from from to to return false
      Coord step = new Coord(from.getCol(), from.getRow() + 1);
      if (b.getPiece(step) != null) {
        return false;
      }
    }
    return true;
  }

  @Override
  pieceType getType() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  List<Coord> getMoves() {
    // TODO Auto-generated method stub
    return null;
  }

}
