package marcuseidahl.entity.chess;

import marcuseidahl.graphics.Sprite;

public class Rook extends ChessPiece{

	public Rook(int row, int col, boolean isWhite) {
		super(row, col, isWhite);
		if(isWhite) {
			name = "White Rook";
			sprite = Sprite.chessRookWhite;
		}
		else {
			name = "Black Rook";
			sprite = Sprite.chessRookBlack;	
		}
	}
	
	public boolean move(int dRow, int dCol, ChessPiece[][] board) {
		return false;

	}


}
