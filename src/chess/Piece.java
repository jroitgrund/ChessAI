package chess;

public abstract class Piece {
  pieceColor color;
  
	abstract boolean validMove(Board b, Coord from, Coord to);
	abstract pieceType getType();
	pieceColor getColor()
	{
	  return color;
	}
}
