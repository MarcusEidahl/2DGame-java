package marcuseidahl.entity.chess;

import marcuseidahl.graphics.Sprite;

public class Pawn extends ChessPiece{

	public Pawn(int x, int y, int row, int col, boolean isWhite) {
		super(x, y, row, col, isWhite);
		if(isWhite) sprite = Sprite.chessPawnWhite;
		else sprite = Sprite.chessPawnBlack;
	}
	
	public boolean move(int dx, int dy, ChessPiece[][] board) {
		return false;
	}


}
