package marcuseidahl.entity.chess;

import marcuseidahl.graphics.Sprite;

public class Pawn extends ChessPiece{

	public Pawn(int row, int col, boolean isWhite) {
		super(row, col, isWhite);
		if(isWhite) {
			name = "White Pawn";
			sprite = Sprite.chessPawnWhite;
		}
		else {
			name = "Black Pawn";
			sprite = Sprite.chessPawnBlack;
		}
	}
	
	public boolean move(int dRow, int dCol, ChessPiece[][] board) {
		row += dRow;
		col += dCol;
		board[row][col] = this;
		return true;
	}


}
