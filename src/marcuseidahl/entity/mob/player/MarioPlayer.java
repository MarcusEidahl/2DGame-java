package marcuseidahl.entity.mob.player;

import marcuseidahl.Game;
import marcuseidahl.entity.projectile.MarioFireball;
import marcuseidahl.entity.projectile.Projectile;
import marcuseidahl.entity.projectile.Shurikan;
import marcuseidahl.entity.spawner.ParticleSpawner;
import marcuseidahl.graphics.Sprite;
import marcuseidahl.input.Keyboard;
import marcuseidahl.input.Mouse;

public class MarioPlayer extends Player{

	public MarioPlayer(int x, int y, Keyboard input) {
		this.x = x;
		this.y = y;
		this.input = input;
		
		speed = 1;
		health = 100;
		leftClickFireRate = MarioFireball.FIRE_RATE;
		rightClickFireRate = Shurikan.FIRE_RATE;
		
		animUp = Sprite.playerMario_still;
		stillUp = Sprite.playerMario_still;
		animDown = Sprite.playerMario_still;
		stillDown = Sprite.playerMario_still;
		animLeft = Sprite.playerMario_right_walk;
		stillLeft = Sprite.playerMario_still;
		animRight = Sprite.playerMario_right_walk;
		stillRight = Sprite.playerMario_still;


	}
	
	
	protected void updateAbilities() {
		
		//SpeedBoost
		if(boostCooldown > 0){
			boostCooldown--;
		}
		if(boostTimer > 0) {
			boostTimer--;
		}
		else {
			boost = 0;
		}
		
		if(input.space && boostCooldown <= 0) {
			boostCooldown = 120;
			boostTimer = 20;
			boost = 2;
		}
	}
	

	
	protected void updateShooting() {
		
		if (leftClickFireRate > 0) leftClickFireRate--;
		if (rightClickFireRate > 0) rightClickFireRate--;
		
		if (Mouse.mouseB() == 1 && leftClickFireRate <= 0) {
			double dx = Mouse.getX() - Game.width * Game.scale / 2;
			double dy = Mouse.getY() - Game.height * Game.scale / 2;
			double projectileDir = Math.atan2(dy, dx);
			Projectile p = new MarioFireball(x, y, projectileDir);
			level.add(p);
			leftClickFireRate = MarioFireball.FIRE_RATE;
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
		level.add(new ParticleSpawner((int) x + 8 , (int) y + 8 , 50, level, 60, Sprite.particle_MarioFireball));
		removed = true;
	}
}
