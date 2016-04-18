package marcuseidahl.entity.chess;

import marcuseidahl.graphics.Sprite;

public class Rook extends ChessPiece{

	public Rook(int x, int y, int row, int col, boolean isWhite) {
		super(x, y, row, col, isWhite);
		if(isWhite) sprite = Sprite.chessRookWhite;
		else sprite = Sprite.chessRookBlack;	
	}
	
	public boolean move(int dx, int dy, ChessPiece[][] board) {
		board[(int)x + dx][(int)y + dy] = this;
		x += dx * 16;
		y += dy * 16;
		return true;

	}


}
