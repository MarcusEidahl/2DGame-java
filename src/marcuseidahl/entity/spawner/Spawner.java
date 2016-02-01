package marcuseidahl.entity.spawner;


import marcuseidahl.entity.Entity;
import marcuseidahl.level.Level;

public class Spawner extends Entity {
	
	public Spawner(int x, int y, int amount, Level level) {
		init(level);
		this.x = x;
		this.y = y;
		
	}
}
