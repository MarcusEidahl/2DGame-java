package marcuseidahl.level;


import java.util.Random;

import marcuseidahl.entity.mob.AStarGoomba;
import marcuseidahl.entity.mob.ChaserBoar;
import marcuseidahl.entity.mob.RandomBear;

public class AndrewSmileyLevel extends Level {

	private final int INIT_SPAWN_TIMER = 80;
	private final double SPAWN_TIMER_SCALING = .95;
	private final int SPAWN_TIMER_INCREMENT = 60;
	private final int SAFE_ZONE_RADIUS = 2;
	
	Random random = new Random();
	private int spawnTimer = INIT_SPAWN_TIMER;
	
	public AndrewSmileyLevel(String path) {
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
		
		if(time % spawnTimer == 0) {
			for(int i = 0; i < 2; i++) {
			int randomWidth = random.nextInt(width);
			int randomHeight = random.nextInt(height);
			if(players.size() > 0){
				if(getTile(randomWidth, randomHeight).solid() == false && 
					Math.abs(randomWidth - getClientPlayer().getX() / 16) > SAFE_ZONE_RADIUS &&
					Math.abs(randomHeight - getClientPlayer().getY() / 16) > SAFE_ZONE_RADIUS)
				add(new ChaserBoar(randomWidth, randomHeight));
				}
			}
		}
		if(time % spawnTimer/2 == 0) {
			for(int i = 0; i < 2; i++) {
			int randomWidth = random.nextInt(width);
			int randomHeight = random.nextInt(height);
			if(players.size() > 0){
				if(getTile(randomWidth, randomHeight).solid() == false &&
						Math.abs(randomWidth - getClientPlayer().getX() / 16) > SAFE_ZONE_RADIUS &&
						Math.abs(randomHeight - getClientPlayer().getY() / 16) > SAFE_ZONE_RADIUS)
					add(new RandomBear(randomWidth, randomHeight));	
				}
			}
		}
		if(time % spawnTimer*2 == 0) {
			for(int i = 0; i < 2; i++) {
			int randomWidth = random.nextInt(width);
			int randomHeight = random.nextInt(height);
			if(players.size() > 0){
				if(getTile(randomWidth, randomHeight).solid() == false &&
						Math.abs(randomWidth - getClientPlayer().getX() / 16) > SAFE_ZONE_RADIUS &&
						Math.abs(randomHeight - getClientPlayer().getY() / 16) > SAFE_ZONE_RADIUS)
					add(new AStarGoomba(randomWidth, randomHeight));	
				}
			}
		}
		if(time % SPAWN_TIMER_INCREMENT == 0 && ((int) (spawnTimer * SPAWN_TIMER_SCALING) > 0)) {
			spawnTimer *= SPAWN_TIMER_SCALING;
			System.out.println(spawnTimer);
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
