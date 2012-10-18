package chess;

public class PlayerInfo {
  private Coord kingCoord;
  private int enPassant = 10;

  PlayerInfo(Coord c) {
    this.enPassant = 10;
    this.kingCoord = c;
  }
  
  PlayerInfo(PlayerInfo pi)
  {
    this.enPassant = pi.getEnPassant();
    this.kingCoord = pi.getKing();
  }

  public Coord getKing() {
    return kingCoord;
  }

  public int getEnPassant() {
    return enPassant;
  }

  public void setKing(Coord c) {
    kingCoord = c;
  }

  public void clearEnPassant() {
    enPassant = 10;
  }

  public void setEnPassant(int enPassant) {
    this.enPassant = enPassant;
  }

}
