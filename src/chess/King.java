package chess;

import java.util.List;

public class King extends Piece {
  King(pieceColor color) {
    super(color);
    // TODO Auto-generated constructor stub
  }

  @Override
  boolean validThreat(Board b, Coord from, Coord to) {
    
    if (!freeDest(b, from, to)) {
      return false;
    }
    
    if (b.getPiece(to).getType() == pieceType.K) {
      return false;
    }
    
    if (from.equals(to)) {
      return false;
    }
    
    Board bPrime = new Board(b);
    bPrime.setPiece(to, this);
    bPrime.clearPiece(from);

    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        Coord fromPrime = new Coord(i, j);
        Piece threat = bPrime.getPiece(fromPrime);
        if (threat != null && threat.getColor() == getColor().opposite()) {
          if (threat.validThreat(bPrime, fromPrime, to)) {
            return false;
          }
        }
      }
    }
    return true;
  }


  @Override
  pieceType getType() {
    return pieceType.K;
  }

  @Override
  List<Coord> getMoves() {
    // TODO Auto-generated method stub
    return null;
  }

}
