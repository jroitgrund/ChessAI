
package chess;

public class PlayerInfo {

  private Coord kingCoord;

  private int   enPassant = 10;

  PlayerInfo(Coord c) {
    this.enPassant = 10;
    this.kingCoord = c;
  }

  PlayerInfo(PlayerInfo pi) {
    this.enPassant = pi.getEnPassant();
    this.kingCoord = pi.getKing();
  }

  Coord getKing() {
    return kingCoord;
  }

  int getEnPassant() {
    return enPassant;
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
