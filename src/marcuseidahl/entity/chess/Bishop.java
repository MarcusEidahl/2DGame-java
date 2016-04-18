package marcuseidahl.entity.chess;

import marcuseidahl.graphics.Sprite;

public class Bishop extends ChessPiece{

	public Bishop(int x, int y, int row, int col, boolean isWhite) {
		super(x, y, row, col, isWhite);
		if(isWhite) sprite = Sprite.chessBishopWhite;
		else sprite = Sprite.chessBishopBlack;
	}

	public boolean move(int dx, int dy, ChessPiece[][] board) {
		return false;
	}

}
