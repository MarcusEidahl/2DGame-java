package marcuseidahl.util;

public class Vector2i {
	
	private int x, y;
	
	public Vector2i() {
		set(0, 0);
	}
	
	public Vector2i(Vector2i vector) {
		set(vector.x, vector.y);
	}
	
	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Vector2i add(Vector2i vector) {
		this.x += vector.x;
		this.y += vector.y;
		return this;
	}
	
	public Vector2i subract(Vector2i vector) {
		this.x -= vector.x;
		this.y -= vector.y;
		return this;
	}
	
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2i setX(int x) {
		this.x = x;
		return this;
	}
	
	public Vector2i setY(int y) {
		this.y = y;
		return this;
	}
	
	public double getDistance(Vector2i goal) {
		double dx = x - goal.x;
		double dy = y - goal.y;
		return Math.sqrt(dx*dx + dy*dy);
	}
	
	public boolean equals(Object object) {
		if(!(object instanceof Vector2i)) return false;
		Vector2i vec = (Vector2i) object;
		if(vec.getX() == getX() && vec.getY() == getY()) return true;
		return false;
	}
	
}
