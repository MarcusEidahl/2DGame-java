package marcuseidahl.entity.projectile;

import marcuseidahl.entity.spawner.ParticleSpawner;
import marcuseidahl.graphics.Sprite;

public class Pokeball extends Projectile{
	
	public static final int FIRE_RATE = 10;
	
	public Pokeball(double x, double y, double direction) {
		super(x, y, direction);
		speed = 2;
		range = 300;
		damage = 100;
		sprite = Sprite.projectile_Pokeball;
		dx = speed * Math.cos(angle);
		dy = speed * Math.sin(angle);
	}
	
	public void remove() {
		level.add(new ParticleSpawner((int) x + 8 , (int) y + 8 , 30, level, 20, Sprite.particle_MarioFireball));
		removed = true;
	}
	
}
