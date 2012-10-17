package chess;

public class Board {

  private Piece[][] pieces;
  private int[] enPassant;

  public Board() {

    this.pieces = new Piece[8][8];

    enPassant = new int[2];
    clearEnPassant(pieceColor.B);
    clearEnPassant(pieceColor.W);

    for (int i = 0; i < 8; i++) {
      this.pieces[i][1] = new Pawn(pieceColor.W);
      this.pieces[i][6] = new Pawn(pieceColor.B);
    }

    this.pieces[0][0] = new Rook(pieceColor.W);
    this.pieces[7][0] = new Rook(pieceColor.W);
    this.pieces[0][7] = new Rook(pieceColor.B);
    this.pieces[7][7] = new Rook(pieceColor.B);

    this.pieces[1][0] = new Knight(pieceColor.W);
    this.pieces[6][0] = new Knight(pieceColor.W);
    this.pieces[1][7] = new Knight(pieceColor.B);
    this.pieces[6][7] = new Knight(pieceColor.B);

    this.pieces[2][0] = new Bishop(pieceColor.W);
    this.pieces[5][0] = new Bishop(pieceColor.W);
    this.pieces[3][7] = new Bishop(pieceColor.B);
    this.pieces[4][7] = new Bishop(pieceColor.B);

    this.pieces[3][0] = new Queen(pieceColor.W);
    this.pieces[4][7] = new Queen(pieceColor.B);

    this.pieces[3][0] = new King(pieceColor.W);
    this.pieces[4][7] = new King(pieceColor.B);

  }

  pieceType getType(Coord position) {
    return this.pieces[position.getCol()][position.getRow()].getType();
  }

  pieceColor getColor(Coord position) {
    return this.pieces[position.getCol()][position.getRow()].getColor();
  }

  boolean isEmpty(Coord position) {
    return this.pieces[position.getCol()][position.getRow()] == null;
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

  void addEnPassant(pieceColor c, int col) {
    enPassant[c.ordinal()] = col;
  }

  void clearEnPassant(pieceColor c) {
    enPassant[c.ordinal()] = 10; // magic number
  }

  int getEnPassant(pieceColor c) {
    return enPassant[c.ordinal()];
  }

}
