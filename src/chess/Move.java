
package chess;

public class Move {

  private Coord from;

  private Coord to;

  Move(Coord from, Coord to) {
    this.from = from;
    this.to = to;
  }
  
  public String toString()
  {
    return from.toString() + " to " + to.toString();
  }

  Coord getFrom() {
    return from;
  }

  Coord getTo() {
    return to;
  }
}
