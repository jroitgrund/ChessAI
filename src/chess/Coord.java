package chess;

public class Coord {
  
  private int row;
  private int col;
  
  Coord(int row, int col)
  {
    this.row = row;
    this.col = col;
  }
  
  int getRow()
  {
    return row;
  }
  
  int getCol()
  {
    return col;
  }
}
