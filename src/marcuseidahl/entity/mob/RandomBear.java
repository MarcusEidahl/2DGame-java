package marcuseidahl.entity.mob;

import java.util.List;

import marcuseidahl.entity.projectile.Projectile;
import marcuseidahl.entity.spawner.ParticleSpawner;
import marcuseidahl.graphics.Sprite;

public class RandomBear extends Mob{
	
	public RandomBear(int x, int y) {
		this.x = x * 16;
		this.y = y * 16;
		meleeDamage = 100;
		speed = .5;
		health = 100;
		sprite = Sprite.mobBear_still;
	}
	
	public void remove(){
		level.add(new ParticleSpawner((int) x + 8 , (int) y + 8 , 15, level, 20, Sprite.particle_MarioFireball));
		level.add(new ParticleSpawner((int) x + 8 , (int) y + 8 , 30, level, 30, Sprite.particle_BearGuts));
		removed = true;
	}
	
	public int getMeleeDamage() {
		remove();
		return meleeDamage;
	}
	
	public void update() {
		
		//checking if hit by a projectile
		List<Projectile> projectiles = level.getProjectiles();
		for(int i = 0; i < projectiles.size(); i++){
			Projectile p = projectiles.get(i);
			if(Math.sqrt((x - p.getX()) * (x - p.getX()) + (y - p.getY()) * (y - p.getY())) < 13){
				health -= p.getDamage();
				p.remove();
			}
		}
		if(health <= 0) {
			remove();
		}
		
		if(time >= Integer.MAX_VALUE - 1000) {
			time = 0;
		}
		time++;

		
		if(time % (30 + random.nextInt(45)) == 0) {
			xa = (random.nextInt(3) - 1) * speed;
			ya = (random.nextInt(3) - 1) * speed;
			if(random.nextInt(3) == 0) {
				xa = 0;
				ya = 0;
			}
		}
		
		
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
