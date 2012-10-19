
package chess;

public class PlayerInfo {

  private Coord   kingCoord;

  private int     enPassant = 10;

  private boolean isChecked;

  private boolean canCastleQueen;

  private boolean canCastleKing;

  private int     scoreValue;

  PlayerInfo(Coord c) {
    scoreValue = 0;
    enPassant = 10;
    kingCoord = c;
    isChecked = false;
    canCastleQueen = true;
    canCastleKing = true;
  }

  void addScore(int value) {
    scoreValue += value;
  }

  int getScore() {
    return scoreValue;
  }

  boolean canCastleKing() {
    return canCastleKing;
  }

  boolean canCastleQueen() {
    return canCastleQueen;
  }

  PlayerInfo noCastleQueen() {
    canCastleQueen = false;
    return this;
  }

  PlayerInfo noCastleKing() {
    canCastleKing = false;
    return this;
  }

  PlayerInfo(PlayerInfo pi) {
    enPassant = pi.getEnPassant();
    kingCoord = pi.getKing();
    isChecked = pi.isChecked;
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

  void setCheck() {
    isChecked = true;
  }

  void clearCheck() {
    isChecked = false;
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
