
package chess;

import com.sun.media.sound.InvalidFormatException;
import java.lang.Character;

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
    int col = Character.getNumericValue(s.charAt(0));
    char row = s.charAt(2);
    if (col < 1 || col > 8 || row < 'a' || row > 'h') {
      throw new InvalidFormatException();
    }
    this.col = col - 1;
    this.row = row - 97;
  }

  Coord(int col, int row) {
    if (col > 7 || row > 7) {
      System.out.println("bad coord");
    }
    this.row = row & 7;
    this.col = col & 7;
  }

  Coord(Coord c, int col, int row) {
    this.row = c.getRow() + row;
    this.col = c.getCol() + col;
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
