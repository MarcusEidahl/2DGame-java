package marcuseidahl.level;

import java.util.Random;

import marcuseidahl.entity.UI.UIElement;
import marcuseidahl.entity.mob.DinosaurBlock;
import marcuseidahl.graphics.Sprite;

public class DodgeblockLevel extends Level{
	
	private final int INIT_SPAWN_TIMER = 45;
	private final double SPAWN_TIMER_SCALING = 2;
	private final int SPAWN_TIMER_INCREMENT = 90;
	
	Random random = new Random();
	private int spawnTimer = INIT_SPAWN_TIMER;
	
	public DodgeblockLevel(String path){
		super(path);
	}
	
	protected void time() {
		if(time >= Integer.MAX_VALUE - 1000) {
			time = 0;
		}
		time++;

		if(time % spawnTimer == 0) {
			int randomHeight = random.nextInt(height);
			add(new DinosaurBlock(width - 2, randomHeight, "red"));
			randomHeight = random.nextInt(height);
			add(new DinosaurBlock(width - 2, randomHeight, "blue"));
			randomHeight = random.nextInt(height);
			add(new DinosaurBlock(width - 2, randomHeight, "green"));
		}
		
		if(time % SPAWN_TIMER_INCREMENT == 0 && ((int) (spawnTimer - SPAWN_TIMER_SCALING) > 10)) {
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
		
		//add(new UIElement(484, 263, Sprite.mobMudkip_right));
		add(new UIElement(384, 209, Sprite.mobMudkip_right));


	}
	
	
	
}
