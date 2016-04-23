package marcuseidahl.entity.chess;

import marcuseidahl.graphics.Sprite;

public class King extends ChessPiece{

	public King(int row, int col, boolean isWhite) {
		super(row, col, isWhite);
		if(isWhite) {
			name = "White King";
			sprite = Sprite.chessKingWhite;
		}
		else {
			name = "Black King";
			sprite = Sprite.chessKingBlack;
		}
	}

	public boolean move(int dRow, int dCol, ChessPiece[][] board) {
		return false;
	}

}
