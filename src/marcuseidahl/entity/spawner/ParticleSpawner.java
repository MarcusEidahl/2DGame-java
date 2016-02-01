package marcuseidahl.entity.spawner;

import marcuseidahl.entity.particle.Particle;
import marcuseidahl.graphics.Sprite;
import marcuseidahl.level.Level;

public class ParticleSpawner extends Spawner{
	
	public ParticleSpawner(int x, int y, int amount, Level level, int lifespan, Sprite sprite) {
		super(x, y, amount, level);
		for(int i = 0; i < amount; i++) {
				level.add(new Particle(x, y, lifespan, sprite));
		}
	}
		
}
