
package chess;

import java.util.List;

import chess.Board.gameState;

public class Minimax {

  final static int maxDepth = 1;

  public static int minimax(Board b, int depth) {
    if (depth <= 0 || b.getState() != gameState.ONGOING) {
      return b.getScore();
    }
    int bestScore = -5000;
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        Coord from = new Coord(i, j);
        Piece p = b.getPiece(from);
        if (p != null && p.getColor() == b.getCurrentPlayer()) {
          List<Coord> l = p.getMoves(b, from);
          for (Coord to : l) {
            Board bPrime = new Board(b);
            bPrime.move(from, to, false);
            bestScore = Math.max(bestScore, -minimax(bPrime, depth - 1));
          }
        }
      }
    }
    System.out.println("white's best score is " + bestScore);
    return bestScore;
  }

  public static Move bestMove(Board b) {
    Move bestMove = null;
    int bestScore = -5000;
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        Coord from = new Coord(i, j);
        Piece p = b.getPiece(from);
        if (p != null && p.getColor() == b.getCurrentPlayer()) {
          List<Coord> l = p.getMoves(b, from);
          for (Coord to : l) {
            Board bPrime = new Board(b);
            bPrime.move(from, to, false);
            int score = -minimax(bPrime, maxDepth);
            if (score > bestScore) {
              bestMove = new Move(from, to);
              bestScore = score;
            }
          }
        }
      }
    }
    System.out.println("best move has score " + bestScore);
    return bestMove;
  }
}
