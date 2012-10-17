package chess;

public class Board {
  private Piece[][] pieces;
  private int[] enPassant;

  public Board() {

    pieces = new Piece[8][8];
    enPassant = new int[2];
    clearEnPassant(pieceColor.B);
    clearEnPassant(pieceColor.W);

    for (int i = 0; i < 8; i++) {
      this.pieces[i][1] = new Pawn();
      this.pieces[i][6] = new Pawn();
    }

    this.pieces[0][7] = new Rook();
    this.pieces[7][7] = new Rook();
    this.pieces[0][0] = new Rook();
    this.pieces[0][7] = new Rook();

    this.pieces[1][7] = new Knight();
    this.pieces[6][7] = new Knight();
    this.pieces[1][0] = new Knight();
    this.pieces[6][0] = new Knight();

    this.pieces[2][7] = new Bishop();
    this.pieces[5][7] = new Bishop();
    this.pieces[3][0] = new Bishop();
    this.pieces[4][0] = new Bishop();

    this.pieces[3][7] = new Queen();
    this.pieces[4][0] = new Queen();

    this.pieces[3][0] = new King();
    this.pieces[4][7] = new King();

  }



  Piece getPiece(Coord c) {
    return pieces[c.getCol()][c.getRow()];
  }
  
  public void clearPiece(Coord c) {
    pieces[c.getCol()][c.getRow()] = null;    
  }

  void setPiece(Coord c, Piece p) {
    pieces[c.getCol()][c.getRow()] = p;
  }
  
  void addEnPassant(pieceColor c, int col)
  {
    enPassant[c.ordinal()] = col;
  }
  
  void clearEnPassant(pieceColor c) {
    enPassant[c.ordinal()] = 10; // magic number
  }
  
  int getEnPassant(pieceColor c)
  {
    return enPassant[c.ordinal()];
  }





}
