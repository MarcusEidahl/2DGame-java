package marcuseidahl.level;


import java.util.Random;

import marcuseidahl.entity.mob.ChaserBoar;
import marcuseidahl.entity.mob.RandomBear;

public class FriendTestLevel2 extends Level{
	
	private final int INIT_SPAWN_TIMER = 120;
	private final int SPAWN_TIMER_SCALING = 4;
	private final int SPAWN_TIMER_TIMING = 120;
	
	int spawnTimer = SPAWN_TIMER_TIMING;
	
	Random random = new Random();
	
	public FriendTestLevel2(String path) {
		super(path);
	}
		
	
	protected void generateLevel() { 
	
	}
	
	//Spawn Stuff via time here
	protected void time() {
		if(time >= Integer.MAX_VALUE - 1000) {
			time = 0;
		}
		time++;
		
		if(time % spawnTimer == 0 && time > 60) {
			for(int i = 0; i < 2; i++) {
			int randomWidth = random.nextInt(width);
			int randomHeight = random.nextInt(height);
			if(getTile(randomWidth, randomHeight).solid() == false) add(new ChaserBoar(randomWidth, randomHeight));
			}
		}
		if(time % spawnTimer/2 == 0 && time > 60) {
			for(int i = 0; i < 2; i++) {
			int randomWidth = random.nextInt(width);
			int randomHeight = random.nextInt(height);
			if(getTile(randomWidth, randomHeight).solid() == false) add(new RandomBear(randomWidth, randomHeight));
			}
		}
		if(time % SPAWN_TIMER_TIMING == 0 && spawnTimer > SPAWN_TIMER_SCALING) {
			spawnTimer -= SPAWN_TIMER_SCALING;
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
		
		time = 0;
		spawnTimer = INIT_SPAWN_TIMER;
		spawnInitialMobs();
		
	}
	
	protected void spawnInitialMobs() {
		//Spawn Initial Mobs here
		for (int i = 0; i < 5; i++) {
			add(new RandomBear(4, 4));
			add(new RandomBear(27, 4));
			add(new RandomBear(27, 27));
			add(new RandomBear(4, 27));

		}
		
		add(new ChaserBoar(27, 4));
		add(new ChaserBoar(27, 27));
		add(new ChaserBoar(4, 27));
	}
}
