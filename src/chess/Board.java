
package chess;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Board {

  private Piece[][]    pieces;

  private PlayerInfo[] players;

  private pieceColor   currentPlayer;

  private pieceColor   currentAdversary;

  protected enum gameState {
    DRAW, CHECKMATE, ONGOING;
  }

  Board(Board b) {
    pieces = new Piece[8][];
    for (int i = 0; i < 8; i++) {
      pieces[i] = Arrays.copyOf(b.pieces[i], 8);
    }
    players = new PlayerInfo[2];
    players[0] = new PlayerInfo(b.players[0]);
    players[1] = new PlayerInfo(b.players[1]);
    currentPlayer = b.currentPlayer;
    currentAdversary = b.currentAdversary;
  }

  Board() {
    currentPlayer = pieceColor.W;
    currentAdversary = pieceColor.B;
    pieces = new Piece[8][8];
    players = new PlayerInfo[2];
    Coord wKing = new Coord(4, 0);
    Coord bKing = new Coord(4, 7);
    players[0] = new PlayerInfo(wKing);
    players[1] = new PlayerInfo(bKing);
    for (int i = 0; i < 8; i++) {
      pieces[i][1] = new Pawn(pieceColor.W);
      pieces[i][6] = new Pawn(pieceColor.B);
    }
    pieces[0][0] = new Rook(pieceColor.W);
    pieces[7][0] = new Rook(pieceColor.W);
    pieces[0][7] = new Rook(pieceColor.B);
    pieces[7][7] = new Rook(pieceColor.B);
    pieces[1][0] = new Knight(pieceColor.W);
    pieces[6][0] = new Knight(pieceColor.W);
    pieces[1][7] = new Knight(pieceColor.B);
    pieces[6][7] = new Knight(pieceColor.B);
    pieces[2][0] = new Bishop(pieceColor.W);
    pieces[5][0] = new Bishop(pieceColor.W);
    pieces[2][7] = new Bishop(pieceColor.B);
    pieces[5][7] = new Bishop(pieceColor.B);
    pieces[3][0] = new Queen(pieceColor.W);
    pieces[3][7] = new Queen(pieceColor.B);
    pieces[4][0] = new King(pieceColor.W);
    pieces[4][7] = new King(pieceColor.B);
  }

  PlayerInfo getInfo(pieceColor c) {
    if (c == pieceColor.B) {
      return players[1];
    }
    else {
      return players[0];
    }
  }

  List<Piece> getPieces(pieceColor c) {
    List<Piece> pieceList = new LinkedList<Piece>();
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (pieces[i][j] != null && pieces[i][j].getColor() == c) {
          pieceList.add(pieces[i][j]);
        }
      }
    }
    return pieceList;
  }

  boolean isEmpty(Coord position) {
    return pieces[position.getCol()][position.getRow()] == null;
  }

  Piece getPiece(Coord c) {
    return pieces[c.getCol()][c.getRow()];
  }

  void setPiece(Coord c, Piece p) {
    pieces[c.getCol()][c.getRow()] = p;
  }

  void clearPiece(Coord c) {
    pieces[c.getCol()][c.getRow()] = null;
  }

  void switchPlayer() {
    currentPlayer = currentAdversary;
    currentAdversary = currentAdversary.opposite();
  }

  pieceColor getCurrentPlayer() {
    return currentPlayer;
  }

  pieceColor getCurrentAdversary() {
    return currentAdversary;
  }

  gameState getState() {
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        Coord c = new Coord(i, j);
        if (getPiece(c) != null && getPiece(c).getColor() == getCurrentPlayer()
            && getPiece(c).getMoves(this, new Coord(i, j)).size() != 0) {
          return gameState.ONGOING;
        }
      }
    }
    if (getInfo(getCurrentPlayer()).isChecked()) {
      return gameState.CHECKMATE;
    }
    else {
      return gameState.DRAW;
    }
  }

  String display() {
    char columns[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
    StringBuilder sb = new StringBuilder(180);
    sb.append("\n");
    for (int i = 7; i >= 0; i--) {
      sb.append(i + 1);
      sb.append(" ");
      sb.append("|");
      for (int j = 0; j < 8; j++) {
        Piece p = pieces[j][i];
        if (p == null) {
          sb.append(" ");
        }
        else {
          if (p.getColor() == pieceColor.B) {
            sb.append(p.toString().toLowerCase());
          }
          else {
            sb.append(p.toString());
          }
        }
        sb.append("|");
      }
      sb.append("\n");
    }
    sb.append("  ");
    for (int i = 0; i < 8; i++) {
      sb.append(" ");
      sb.append(columns[i]);
    }
    sb.append("\n");
    return sb.toString();
  }

  void setCheck() {
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        Coord c = new Coord(i, j);
        if (getPiece(c) != null
            && getPiece(c).getColor() == getCurrentPlayer()
            && getPiece(c).validThreat(this, c,
                getInfo(getCurrentAdversary()).getKing())) {
          getInfo(getCurrentAdversary()).setCheck();
          return;
        }
      }
    }
  }

  boolean move(Coord from, Coord to, boolean checkValid) {
    Piece p = getPiece(from);
    if (p != null && p.getColor() == getCurrentPlayer()) {
      if (!checkValid || p.validMove(this, from, to)) {
        if (getPiece(to) != null) {
          getInfo(getCurrentAdversary()).addScore(-getPiece(to).getValue());
          getInfo(getCurrentPlayer()).addScore(getPiece(to).getValue());
          if (checkValid)
            System.out.println("took a piece! Current scores\nwhite: "
                + getInfo(pieceColor.W).getScore() + "\nblack: "
                + getInfo(pieceColor.B).getScore());
        }
        p.move(this, from, to);
        getInfo(getCurrentPlayer()).clearCheck();
        setCheck();
        switchPlayer();
        getInfo(getCurrentPlayer()).clearEnPassant();
        return true;
      }
    }
    else if (p == null) {
    }
    else if (p.getColor() != getCurrentPlayer()) {
    }
    return false;
  }

  public int getScore() {
    if (getState() == gameState.ONGOING) {
      return getInfo(getCurrentPlayer()).getScore();
    }
    else {
      System.out.println("get score says: you're checkmated, mate");
      return Integer.MIN_VALUE;
    }
  }
}
