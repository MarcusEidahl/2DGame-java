package marcuseidahl.entity.projectile;

import marcuseidahl.entity.spawner.ParticleSpawner;
import marcuseidahl.graphics.Sprite;

public class Shurikan extends Projectile{
	
	public static final int FIRE_RATE = 10;
	
	public Shurikan(double x, double y, double direction) {
		super(x, y, direction);
		speed = 4;
		range = 180;
		damage = 34;
		sprite = Sprite.projectile_Shurikan;
		dx = speed * Math.cos(angle);
		dy = speed * Math.sin(angle);
	}
	
	
	public void remove() {
		level.add(new ParticleSpawner((int) x + 8 , (int) y + 8 , 7, level, 8, Sprite.particle_Shurikan));
		removed = true;
	}
	
}
