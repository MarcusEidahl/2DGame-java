package marcuseidahl.level;

import java.util.Random;

import marcuseidahl.entity.mob.AStarGoomba;
import marcuseidahl.entity.mob.RandomBear;

public class PracticeLevel1 extends Level{
		
	Random random = new Random();
	
	public PracticeLevel1(String path) {
		super(path);
	}

	
	protected void time() {
		if(time >= Integer.MAX_VALUE - 1000) {
			time = 0;
		}
		time++;
		
		if(time % 60 == 0) {
			int randomWidth = random.nextInt(width);
			int randomHeight = random.nextInt(height);
			if(getTile(randomWidth, randomHeight).solid() == false) add(new RandomBear(randomWidth, randomHeight));
		}
	}
	
	public void reset() {
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).remove();
		}
		
		for(int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).remove();
		}
		
		for(int i = 0; i < particles.size(); i++) {
			particles.get(i).remove();
		}
		
		for(int i = 0; i < players.size(); i++) {
			players.get(i).remove();
		}
		spawnInitialMobs();
		time = 0;
	}
	
	protected void spawnInitialMobs() {
		//Spawn Initial Mobs here
		for (int i = 0; i < 1; i++) {
			add(new AStarGoomba(3, 5));

		}
	}
}
	
