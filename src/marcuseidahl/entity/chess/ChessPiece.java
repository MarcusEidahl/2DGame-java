package marcuseidahl.entity.chess;

import marcuseidahl.entity.Entity;
import marcuseidahl.graphics.Screen;
import marcuseidahl.graphics.Sprite;

public abstract class ChessPiece extends Entity{
	
	protected Sprite sprite;
	protected boolean isWhite;
	protected int row, col;
	
	protected ChessPiece(double x, double y, int row, int col, boolean isWhite) {
		this.x = x;
		this.y = y;
		this.row = row;
		this.col = col;
		this.isWhite = isWhite;
	}
	
	public abstract boolean move(int dx, int dy, ChessPiece[][] board);
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public void render(Screen screen){
		screen.renderMob((int) x, (int) y, sprite, 0);
	}
}
