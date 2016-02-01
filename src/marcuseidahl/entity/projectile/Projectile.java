package marcuseidahl.entity.projectile;

import java.util.Random;

import marcuseidahl.entity.Entity;
import marcuseidahl.graphics.Screen;
import marcuseidahl.graphics.Sprite;

public abstract class Projectile extends Entity{
	
	protected final double xOrigin, yOrigin;
	protected double angle;
	protected Sprite sprite;
	protected double x, y;
	protected double dx, dy;
	protected double distance;
	protected int speed, range, damage;
	
	protected final Random random = new Random();
	
	public Projectile(double x, double y, double direction) {
		xOrigin = x;
		yOrigin = y;
		angle = direction;
		this.x = x;
		this.y = y;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public int getSpriteSize() {
		return sprite.SIZE;
	}
	
	public void update() {
		if (level.tileCollision((int)(x + dx), (int)(y + dy), 16, 0, 0)) {
			remove();
		}
			move();
	}
	
	protected void move() {
		x += dx;
		y += dy;
		if(distance() > range) remove();
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double distance() {
		double dist = 0;
		dist = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y)));
		return dist;
	}
	
	public int getDamage(){
		return damage;
	}
	
	public void render(Screen screen) {
		screen.renderProjectile((int) x, (int) y, this);
	}
}
