package marcuseidahl.entity.mob;

import marcuseidahl.entity.mob.player.Player;
import marcuseidahl.graphics.Sprite;
import marcuseidahl.level.PacmanLevel;

public class PacmanGhost extends Mob{
	
	Player player;
	double prevX, prevY;

	public PacmanGhost(int x, int y, String color) {
		this.x = x * 16;
		this.y = y * 16;
		
		meleeDamage = 100;
		health = 100;
		speed = 1;
		sprite = Sprite.mobMudkip_right;
		
		if(color == "blue") {
			sprite = Sprite.mobBlueGhost;
		}
		else if(color == "red") {
			sprite = Sprite.mobRedGhost;
		}
		else if(color == "yellow") {
			sprite = Sprite.mobYellowGhost;
		}
		else if(color == "pink") {
			sprite = Sprite.mobPinkGhost;
		}
		
	}
	
	public void update() {
		
		
		player = level.getClientPlayer();

		if(health <= 0) {
			remove();
		}
		
		if(time >= Integer.MAX_VALUE - 1000) {
			time = 0;
		}
		time++;
		
		
		if(prevX == x && prevY == y) {
			if(random.nextBoolean() == true) {
				if(random.nextBoolean() == true) xa = speed;
				else xa = -speed;
				if(random.nextBoolean() == true) ya = speed;
				else ya = -speed;
				time = 1;
			}
		}
		
		if(time % 150 == 0 && player != null) {
			if(player.getX() < x) xa = -speed;
			if(player.getX() > x) xa = speed;
			if(player.getY() < y) ya = -speed;
			if(player.getY() > y) ya = speed;
		}
		
		if(level instanceof PacmanLevel) {
			if(x <= 0){
				x = level.getWidth() * 16 - 17;
			}
			if(x >= level.getWidth() * 16 - 16){
				x = 1;
			}
		}
		
		
		prevX = x;
		prevY = y;
		
		if (ya > 0) {
			dir = Direction.UP;
		}
		else if(ya < 0) {
			dir = Direction.DOWN;
		}
		if(xa < 0) {
			dir = Direction.LEFT;
		}
		else if(xa > 0) {
			dir = Direction.RIGHT;
		}
		
		if(xa != 0 || ya != 0) {
				move(xa, ya);
				walking = true;
		}
		else {
			walking = false;
		}
		
	}
	
}
