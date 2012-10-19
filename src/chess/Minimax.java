
package chess;

import java.util.List;

import chess.Board.gameState;

public class Minimax {

  public static int minimax(Board b, int depth) {
    if (depth <= 0 || b.getState() != gameState.ONGOING) {
      return b.getScore();
    }
    int bestScore = Integer.MIN_VALUE;
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        Coord from = new Coord(i, j);
        Piece p = b.getPiece(from);
        if (p != null && p.getColor() == b.getCurrentPlayer()) {
          List<Coord> l = p.getMoves(b, from);
          for (Coord to : l) {
            Board bPrime = new Board(b);
            bestScore = Math.max(bestScore,
                minimax(bPrime.move(from, to, false), depth - 1));
          }
        }
      }
    }
    return bestScore;
  }
}
