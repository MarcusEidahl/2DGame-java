package marcuseidahl.entity.chess;

import marcuseidahl.graphics.Sprite;

public class Bishop extends ChessPiece{

	public Bishop(int row, int col, boolean isWhite) {
		super(row, col, isWhite);
		if(isWhite) {
			name = "White Bishop";
			sprite = Sprite.chessBishopWhite;
		}
		else {
			name = "Black Bishop";
			sprite = Sprite.chessBishopBlack;
		}
		
	}

	public boolean move(int dRow, int dCol, ChessPiece[][] board) {
		return false;
	}

}
