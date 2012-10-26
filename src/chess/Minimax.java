
package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import chess.Board.gameState;

public class Minimax {

  final static int    maxDepth = 3;

  final static Random rand;
  static {
    rand = new Random();
  }

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
            int score = -minimax(bPrime, depth - 1);
            if (score > bestScore) {
              bestScore = score;
            }
          }
        }
      }
    }
    return bestScore;
  }

  public static Move bestMove(Board b) {
    List<Move> moveList = new ArrayList<Move>();
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
              moveList = new ArrayList<Move>();
              bestScore = score;
            }
            if (score == bestScore) {
              moveList.add(new Move(from, to));
            }
          }
        }
      }
    }
    return moveList.get(rand.nextInt(moveList.size()));
  }
}
