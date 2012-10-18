package chess;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Board {

  private Piece[][] pieces;
  private PlayerInfo[] players;

  public Board() {

    pieces = new Piece[8][8];
    players = new PlayerInfo[2];
    
    Coord wKing = new Coord(3,0);
    Coord bKing = new Coord(4,7);
    players[0] = new PlayerInfo(wKing);
    players[1] = new PlayerInfo(bKing);

    //enPassant = new int[2];
  //  clearEnPassant(pieceColor.B);
   // clearEnPassant(pieceColor.W);

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
    pieces[3][7] = new Bishop(pieceColor.B);
    pieces[4][7] = new Bishop(pieceColor.B);

    pieces[3][0] = new Queen(pieceColor.W);
    pieces[4][7] = new Queen(pieceColor.B);

    pieces[3][0] = new King(pieceColor.W);
    pieces[4][7] = new King(pieceColor.B);

  }
  
  public PlayerInfo getInfo(pieceColor c) {
    if (c == pieceColor.B) {
      return players[1];
    } else {
      return players[0];
    }
  }

  public Board(Board b) {
    pieces = new Piece[8][];
    for (int i = 0; i < 8; i++) {
      pieces[i] = Arrays.copyOf(b.pieces[i], 8);
    }
  }
    
  boolean isEmpty(Coord position) {
    return this.pieces[position.getCol()][position.getRow()] == null;
  }

  Piece getPiece(Coord c) {
    return pieces[c.getCol()][c.getRow()];
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

  public void clearPiece(Coord c) {
    pieces[c.getCol()][c.getRow()] = null;
  }

  void setPiece(Coord c, Piece p) {
    pieces[c.getCol()][c.getRow()] = p;
  }

}
