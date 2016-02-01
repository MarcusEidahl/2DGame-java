package marcuseidahl.entity.mob.player;


import java.util.List;

import marcuseidahl.entity.Entity;
import marcuseidahl.entity.mob.Mob;
import marcuseidahl.entity.projectile.Projectile;
import marcuseidahl.graphics.Screen;
import marcuseidahl.graphics.Sprite;
import marcuseidahl.input.Keyboard;

public abstract class Player extends Mob {
	
	protected Keyboard input;
	protected Sprite sprite;
	protected int animationTimer = 0;
	protected double speed = 0;
	protected double boost = 0;
	protected int boostTimer = 0;
	protected int boostCooldown = 0;
	protected int lifeTime = 0;
	
	protected Sprite animUp;
	protected Sprite stillUp;
	protected Sprite animDown;
	protected Sprite stillDown;
	protected Sprite animLeft;
	protected Sprite stillLeft;
	protected Sprite animRight;
	protected Sprite stillRight;


	
	Projectile p;
	protected int leftClickFireRate = 0;
	protected int rightClickFireRate = 0;
	
	
	public void update() {
		updateHealth();
		updateMovement();
		updateAbilities();
		updateShooting();
	}
	
	
	protected void updateHealth() {
		//System.out.println(health);
		
		//Melee Damage from mobs
		List<Projectile> projectiles = level.getProjectiles();
		List<Entity> mobs = level.getEntities();
		for(int i = 0; i < mobs.size(); i++) {
			Entity e = mobs.get(i);
			if(e instanceof Mob){
				if(Math.sqrt((x - e.getX()) * (x - e.getX()) + (y - e.getY()) * (y - e.getY())) < 13) {
					health -=  ((Mob) e).getMeleeDamage();
				}
			}
		}
		
		//Projectile Damage from mobs
		for(int i = 0; i < projectiles.size(); i++){
			Projectile p = projectiles.get(i);
			if(p.getX() == x && p.getY() == y){
				health -= projectiles.get(i).getDamage();
			}
		}
		if(health <= 0) {
			remove();
		}
		
		lifeTime++;
		
	}
	
	protected void updateMovement() {
		
		double xa = 0, ya = 0;
		if (animationTimer < 7500) animationTimer++;
		else animationTimer = 0;
		if (input.up) ya -= (speed + boost);
		if (input.down) ya += (speed + boost);
		if (input.left) xa -= (speed + boost);
		if (input.right) xa += (speed + boost);

		if(xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		}
		else {
			walking = false;
		}
		
	}
	
	protected void updateAbilities() {
		
	}
	
	protected void updateShooting() {
		
	}

	public void render(Screen screen) {
		sprite = stillRight;
		//no flip = 0, xflip = 1, yflip = 2, both flip = 3
		int flip = 0;	
		if(dir == Direction.UP) {
			flip = 0;
			sprite = stillUp;
			if(walking) {
				if(animationTimer % 20 > 10) {
					sprite = animUp;
				}
			}
		}
		if(dir == Direction.RIGHT) {
			flip = 0;
			sprite = stillRight;
			if(walking) {
				if(animationTimer % 20 > 10) {
					sprite = animRight;
				}
			}
		}
		if(dir == Direction.DOWN) {
			flip = 0;
			sprite = stillDown;
			if(walking) {
				if(animationTimer % 20 > 10) {
					sprite = animDown;
				}
			}
		}
		if(dir == Direction.LEFT) {
			flip = 1;
			sprite = stillLeft;
			if(walking) {
				if(animationTimer % 20 > 10) { 
					sprite = animLeft;
				}
			}
		}

		screen.renderMob((int) x,(int) y, sprite, flip);
	}
	
	public int getScore() {
		return lifeTime / 60;
	}
	
}
