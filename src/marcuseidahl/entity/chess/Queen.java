package marcuseidahl.entity.chess;

import marcuseidahl.graphics.Sprite;

public class Queen extends ChessPiece{

	public Queen(int x, int y, int row, int col, boolean isWhite) {
		super(x, y, row, col, isWhite);
		if(isWhite) sprite = Sprite.chessQueenWhite;
		else sprite = Sprite.chessQueenBlack;
	}
	
	public boolean move(int dx, int dy, ChessPiece[][] board) {
		return false;
	}


}
