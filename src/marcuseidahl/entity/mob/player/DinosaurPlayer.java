package marcuseidahl.entity.mob.player;

import marcuseidahl.graphics.Sprite;
import marcuseidahl.input.Keyboard;

public class DinosaurPlayer extends Player{
	
	public DinosaurPlayer(int x, int y, Keyboard input) {
		this.x = x;
		this.y = y;
		this.input = input;
		
		speed = 2;
		health = 100;
		
		animUp = Sprite.playerDinosaur;
		stillUp = Sprite.playerDinosaur;
		animDown = Sprite.playerDinosaur;
		stillDown = Sprite.playerDinosaur;
		animLeft = Sprite.playerDinosaur;
		stillLeft = Sprite.playerDinosaur;
		animRight = Sprite.playerDinosaur;
		stillRight = Sprite.playerDinosaur;
	}
	
	protected void updateMovement() {
		
		double xa = 0, ya = 0;
		if (animationTimer < 7500) animationTimer++;
		else animationTimer = 0;
		if (input.space) ya -= (speed + boost);
		else ya += (speed + boost);

		if(xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		}
		else {
			walking = false;
		}
		
	}
	
}
