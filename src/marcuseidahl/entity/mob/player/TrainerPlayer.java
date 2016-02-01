package marcuseidahl.entity.mob.player;

import marcuseidahl.Game;
import marcuseidahl.entity.projectile.MarioFireball;
import marcuseidahl.entity.projectile.Pokeball;
import marcuseidahl.entity.projectile.Projectile;
import marcuseidahl.entity.projectile.Shurikan;
import marcuseidahl.graphics.Sprite;
import marcuseidahl.input.Keyboard;
import marcuseidahl.input.Mouse;

public class TrainerPlayer extends Player{

	public TrainerPlayer(int x, int y, Keyboard input) {
		this.x = x;
		this.y = y;
		this.input = input;
		
		speed = 1;
		health = 100;
		leftClickFireRate = MarioFireball.FIRE_RATE;
		rightClickFireRate = Shurikan.FIRE_RATE;
		
		animUp = Sprite.playerTrainer_right;
		stillUp = Sprite.playerTrainer_right;
		animDown = Sprite.playerTrainer_right;
		stillDown = Sprite.playerTrainer_right;
		animLeft = Sprite.playerTrainer_right;
		stillLeft = Sprite.playerTrainer_right;
		animRight = Sprite.playerTrainer_right;
		stillRight = Sprite.playerTrainer_right;
		
	}
	
	protected void updateAbilities() {
		
	}
	
	protected void updateShooting() {
		
		if (leftClickFireRate > 0) leftClickFireRate--;
		if (rightClickFireRate > 0) rightClickFireRate--;
		
		if (Mouse.mouseB() == 1 && leftClickFireRate <= 0) {
			double dx = Mouse.getX() - Game.width * Game.scale / 2;
			double dy = Mouse.getY() - Game.height * Game.scale / 2;
			double projectileDir = Math.atan2(dy, dx);
			Projectile p = new Pokeball(x, y, projectileDir);
			level.add(p);
			leftClickFireRate = Pokeball.FIRE_RATE;
		}
		
		if (Mouse.mouseB() == 3 && rightClickFireRate <= 0) {
			double dx = Mouse.getX() - Game.width * Game.scale / 2;
			double dy = Mouse.getY() - Game.height * Game.scale / 2;
			double projectileDir = Math.atan2(dy, dx);
			Projectile p = new Shurikan(x, y, projectileDir);
			level.add(p);			
			rightClickFireRate = Shurikan.FIRE_RATE;
		}
		
	}
	
	public void remove() {
		//TODO Death animation HERE
		removed = true;
	}
}
