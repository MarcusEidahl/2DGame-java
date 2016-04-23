package marcuseidahl.entity.chess;

import marcuseidahl.graphics.Sprite;

public class Knight extends ChessPiece{

	public Knight(int row, int col, boolean isWhite) {
		super(row, col, isWhite);
		if(isWhite) {
			name = "White Knight";
			sprite = Sprite.chessKnightWhite;
		}
		else {
			name = "Black Knight";
			sprite = Sprite.chessKnightBlack;
		}
	}
	
	public boolean move(int dRow, int dCol, ChessPiece[][] board) {
		return false;
	}


}
