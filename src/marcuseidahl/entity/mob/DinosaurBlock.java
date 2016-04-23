package marcuseidahl.entity.mob;

import marcuseidahl.graphics.Sprite;

public class DinosaurBlock extends Mob{
	
	
	public DinosaurBlock(int x, int y, String color) {
		this.x = x * 16;
		this.y = y * 16;
		meleeDamage = 100;
		health = 100;
		speed = 3.5;
		sprite = Sprite.mobMudkip_right;
		
		if(color == "red") {
			sprite = Sprite.dinosaurBlockRed;
		}
		else if(color == "green") {
			sprite = Sprite.dinosaurBlockGreen;
		}
		else{
			sprite = Sprite.dinosaurBlockPurple;
		}
		
	}
	
	public void update() {
		
		xa = -speed;
		
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
