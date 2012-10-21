
package chess;

import com.sun.media.sound.InvalidFormatException;

public class Coord {

  private int row;

  private int col;

  static int distance(Coord from, Coord to) {
    return Math.max(Math.abs(from.getRow() - to.getRow()),
        Math.abs(from.getCol() - to.getCol()));
  }

  Coord(String s) throws InvalidFormatException {
    if (s.length() != 3) {
      throw new InvalidFormatException();
    }
    char col = s.charAt(0);
    int row = Character.getNumericValue(s.charAt(2));
    if (row < 1 || row > 8 || col < 'a' || col > 'h') {
      throw new InvalidFormatException();
    }
    this.row = row - 1;
    this.col = col - 97;
  }

  Coord(int col, int row) {
    this.row = row;
    this.col = col;
  }

  Coord(Coord c, int col, int row) {
    this.row = c.getRow() + row;
    this.col = c.getCol() + col;
  }
  
  public String toString()
  {
    return "[" + getCol() + ", " + getRow() + "]";
  }

  boolean inBoard() {
    return (row >= 0 && row < 8 && col >= 0 && col < 8);
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
