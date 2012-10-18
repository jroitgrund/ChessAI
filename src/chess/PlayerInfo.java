package chess;

public class PlayerInfo {
  private Coord kingCoord;
  private int enPassant = 10;
  
  PlayerInfo(Coord c) {
    this.enPassant = 10;
    this.kingCoord = c;
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
  
  public void cleaEnPassant() {
    enPassant = 10;
  }
  
  public void setEnPassant(int enPassant) {
    enPassant = enPassant;
  }

}
