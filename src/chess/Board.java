package chess;

public class Board {
	private Piece [][] pieces;
	
	public Board() {
		
		this.pieces = new Piece [8][8];
		
		for(int i = 0; i < 8; i ++) {
			this.pieces[i][1] = new Pawn();
			this.pieces[i][6] = new Pawn();
		}
		
		this.pieces[0][7] = new Roock();
		this.pieces[7][7] = new Roock();
		this.pieces[0][0] = new Roock();
		this.pieces[0][7] = new Roock();
		
		this.pieces[1][7] = new Knight();
		this.pieces[6][7] = new Knight();
		this.pieces[1][0] = new Knight();
		this.pieces[6][0] = new Knight();
		
		this.pieces[2][7] = new Bishop();
		this.pieces[5][7] = new Bishop();
		this.pieces[3][0] = new Bishop();
		this.pieces[4][0] = new Bishop();
		
		this.pieces[3][7] = new Queen();
		this.pieces[4][0] = new Queen();
		
		this.pieces[3][0] = new King();
		this.pieces[4][7] = new King();
		
	}
	
	
}

