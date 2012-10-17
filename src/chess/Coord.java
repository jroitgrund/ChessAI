package chess;

public class Coord {

  private int row;
  private int col;

  Coord(int col, int row) {
    this.row = row;
    this.col = col;
  }

  int getRow() {
    return row;
  }

  int getCol() {
    return col;
  }
}
