package marcuseidahl.entity.chess;

import marcuseidahl.graphics.Sprite;

public class Queen extends ChessPiece{

	public Queen(int row, int col, boolean isWhite) {
		super(row, col, isWhite);
		if(isWhite) {
			name = "White Queen";
			sprite = Sprite.chessQueenWhite;
		}
		else {
			name = "Black Queen";
			sprite = Sprite.chessQueenBlack;
		}
	}
	
	public boolean move(int dRow, int dCol, ChessPiece[][] board) {
		return false;
	}


}
