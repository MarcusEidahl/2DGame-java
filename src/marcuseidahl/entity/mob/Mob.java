package marcuseidahl.entity.mob;

import marcuseidahl.entity.Entity;
import marcuseidahl.entity.projectile.Projectile;
import marcuseidahl.graphics.Screen;
import marcuseidahl.graphics.Sprite;

public abstract class Mob extends Entity{

	protected Sprite sprite;
	protected boolean walking = false;
	protected int health;
	protected int meleeDamage;
	protected int lavaDamage = 1;
	protected int time = 0;
	protected double xa = 0; 
	protected double ya = 0;
	protected double speed;

	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}
	public Direction dir;
	
	public void move(double xa, double ya) {

		if(xa != 0 && ya != 0) {
			move(0, ya);
			move(xa, 0);
			return;
		}
		
		if(ya > 0) dir = Direction.DOWN;			//south
		if(ya < 0) dir = Direction.UP;				//north
		if(xa > 0) dir = Direction.RIGHT;			//east	
		if(xa < 0) dir = Direction.LEFT ;			//west
		
		/*if(!collision(xa, ya)) {
			x += xa;
			y += ya;
		}*/
		while(xa != 0) {
			if(harmfulCollision(xa, 0)) {
				health -= lavaDamage;
			}
			if(Math.abs(xa) > 1) {
				if(!collision(abs(xa), ya)) {
					this.x += abs(xa);
				}
				xa -= abs(xa);
			}
			else {
				if(!collision(abs(xa), ya)) {
					this.x += xa;
				}
				xa = 0;
			}

		}
		
		while(ya != 0) {
			if(harmfulCollision(0, ya)) {
				health -= lavaDamage;
			}
			if(Math.abs(ya) > 1) {
				if(!collision(xa, abs(ya))) {
					this.y += abs(ya);
				}
				ya -= abs(ya);
			}
			else{
				if(!collision(xa, abs(ya))) {
					this.y += ya;
				}
				
				ya = 0;

			}
		}
		
		
		
	}
	
	private int abs(double value) {
		if(value < 0) return -1;
		return 1;
	}
	
	public void update() {
		
	}
	
	public void render(Screen screen){
		screen.renderMob((int) x, (int) y, sprite, 0);
	}
	
	public void shoot(double x, double y, Projectile p) {
		level.add(p);
	}
	
	public int getMeleeDamage() {
		return meleeDamage;
	}

	protected boolean collision(double xa, double ya){
		boolean solid = false;
		
		if(level.getTile((int) (x + xa) / 16, (int) (y + ya) / 16).solid()) solid = true;
		if(level.getTile((int) (x + xa) / 16, (int) (y + ya + 15) / 16).solid()) solid = true;
		if(level.getTile((int) (x + xa + 15) / 16, (int) (y + ya) / 16).solid()) solid = true;
		if(level.getTile((int) (x + xa + 15) / 16, (int) (y + ya + 15) / 16).solid()) solid = true;
		
		return solid;
		
	}
	
	protected boolean harmfulCollision(double xa, double ya){
		boolean isHarmful = false;
		
		if(level.getTile((int) (x + xa) / 16, (int) (y + ya) / 16).isHarmful()) isHarmful = true;
		if(level.getTile((int) (x + xa) / 16, (int) (y + ya + 15) / 16).isHarmful()) isHarmful = true;
		if(level.getTile((int) (x + xa + 15) / 16, (int) (y + ya) / 16).isHarmful()) isHarmful = true;
		if(level.getTile((int) (x + xa + 15) / 16, (int) (y + ya + 15) / 16).isHarmful()) isHarmful = true;
		
		return isHarmful;
		
	}
}
