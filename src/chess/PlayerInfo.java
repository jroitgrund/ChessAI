
package chess;

public class PlayerInfo {

  private Coord   kingCoord;

  private int     enPassant = 10;

  private boolean isChecked;

  PlayerInfo(Coord c) {
    this.enPassant = 10;
    this.kingCoord = c;
    this.isChecked = false;
  }

  PlayerInfo(PlayerInfo pi) {
    this.enPassant = pi.getEnPassant();
    this.kingCoord = pi.getKing();
    this.isChecked = pi.isChecked;
  }

  Coord getKing() {
    return kingCoord;
  }

  int getEnPassant() {
    return enPassant;
  }

  boolean isChecked() {
    return isChecked;
  }

  void setChess() {
    this.isChecked = true;
  }

  void unsetChess() {
    this.isChecked = false;
  }

  void setKing(Coord c) {
    kingCoord = c;
  }

  void setEnPassant(int enPassant) {
    this.enPassant = enPassant;
  }

  void clearEnPassant() {
    enPassant = 10;
  }
}
