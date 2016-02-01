package marcuseidahl.level;

import marcuseidahl.entity.UI.UIElement;
import marcuseidahl.entity.mob.PacmanGhost;
import marcuseidahl.entity.mob.PacmanOrb;
import marcuseidahl.graphics.Sprite;
import marcuseidahl.level.tile.OpenTile;

public class PacmanLevel extends Level{
	
	public final int SAFE_ZONE_RADIUS = 3;
	int placed = 0;

	public PacmanLevel(String path){
		super(path);
	}
	
	protected void spawnInitialMobs() {
		
		placeGhost("red");
		placeGhost("blue");
		placeGhost("yellow");
		placeGhost("pink");
		for(int i = 0; i < 10; i++) {
			placeOrb();
		}
		//add(new UIElement(484, 263, Sprite.mobMudkip_right));
		add(new UIElement(384, 209, Sprite.mobMudkip_right));
	}
	
	protected void time() {
		if(time >= Integer.MAX_VALUE - 1000) {
			time = 0;
		}
		time++;
		
		if(time % 300 == 0) { 
			placeOrb();
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
		
	}
	
	protected void placeGhost(String color) {
		placed = 0;
		int randomWidth;
		int randomHeight;
		while(placed != 1){
			randomWidth = random.nextInt(width);
			randomHeight = random.nextInt(height);
			if(getTile(randomWidth, randomHeight) instanceof OpenTile) {
				add(new PacmanGhost(randomWidth, randomHeight, color));
				placed = 1;
			}
		}
	}
	
	protected void placeOrb() {
		placed = 0;
		int randomWidth;
		int randomHeight;
		while(placed != 1){
			randomWidth = random.nextInt(width);
			randomHeight = random.nextInt(height);
			if(getTile(randomWidth, randomHeight) instanceof OpenTile) {
				add(new PacmanOrb(randomWidth, randomHeight));
				placed = 1;
			}
		}
	}
	
}
