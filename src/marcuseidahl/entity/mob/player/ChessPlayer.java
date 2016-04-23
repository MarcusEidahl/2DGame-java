package marcuseidahl.entity.mob.player;

import marcuseidahl.graphics.Screen;
import marcuseidahl.input.Keyboard;

public class ChessPlayer extends Player{

	private boolean isWhite;
	
	public ChessPlayer(int x, int y, Keyboard input, boolean isWhite) {
		this.x = x;
		this.y = y;
		this.input = input;
		this.isWhite = isWhite;
	}
	
	public boolean isWhite() {
		return isWhite;
	}
	
	public void render(Screen screen) {
		
	}
	
	public void update() {
		
	}
	
	public void updateMovement() {
		
	}

}
