package marcuseidahl.entity.chess;

import marcuseidahl.graphics.Sprite;

public class King extends ChessPiece{

	public King(int x, int y, int row, int col, boolean isWhite) {
		super(x, y, row, col, isWhite);
		if(isWhite) sprite = Sprite.chessKingWhite;
		else sprite = Sprite.chessKingBlack;
	}

	public boolean move(int dx, int dy, ChessPiece[][] board) {
		return false;
	}

}
