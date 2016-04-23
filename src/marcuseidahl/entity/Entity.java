package marcuseidahl.entity;

import java.util.Random;

import marcuseidahl.graphics.Screen;
import marcuseidahl.level.Level;

public abstract class Entity {

	protected double x, y;
	protected boolean removed = false;
	protected Level level;
	protected final Random random = new Random();
	
	public void update() {
		
	}
	
	public void render(Screen screen) {
		
	}
	
	public void remove() {
		removed = true;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public void init(Level level) {
		this.level = level;
	}
}
