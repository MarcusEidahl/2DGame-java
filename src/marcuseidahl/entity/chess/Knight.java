package marcuseidahl.entity.chess;

import marcuseidahl.graphics.Sprite;

public class Knight extends ChessPiece{

	public Knight(int x, int y, int row, int col, boolean isWhite) {
		super(x, y, row, col, isWhite);
		if(isWhite) sprite = Sprite.chessKnightWhite;
		else sprite = Sprite.chessKnightBlack;
	}
	
	public boolean move(int dx, int dy, ChessPiece[][] board) {
		return false;
	}


}
