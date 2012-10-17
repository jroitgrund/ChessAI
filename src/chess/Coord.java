package chess;

public class Coord {

  private int row;
  private int col;

  Coord(int col, int row) {
    if (col > 7 || row > 7) {
      System.out.println("bad coord");
    }
    this.row = row & 7;
    this.col = col & 7;
  }

  int getRow() {
    return row;
  }

  int getCol() {
    return col;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Coord other = (Coord) obj;
    if (col != other.col)
      return false;
    if (row != other.row)
      return false;
    return true;
  }
}
