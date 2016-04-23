package marcuseidahl.entity.chess;

import marcuseidahl.entity.Entity;
import marcuseidahl.graphics.Screen;
import marcuseidahl.graphics.Sprite;

public abstract class ChessPiece extends Entity{
	
	protected Sprite sprite;
	protected boolean isWhite;
	protected int row, col;
	protected String name;
	
	protected ChessPiece(int row, int col, boolean isWhite) {
		this.x = col * 16;
		this.y = row * 16;
		this.row = row;
		this.col = col;
		this.isWhite = isWhite;
	}
	
	public abstract boolean move(int dRow, int dCol, ChessPiece[][] board);
	
	public String getName() {
		return name;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public void render(Screen screen){
		screen.renderMob((int) col * 16, (int) row * 16, sprite, 0);
	}
	
}
