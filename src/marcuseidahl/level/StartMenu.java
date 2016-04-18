package marcuseidahl.level;

import marcuseidahl.entity.UI.UIElement;
import marcuseidahl.entity.mob.RandomBear;
import marcuseidahl.graphics.Sprite;

public class StartMenu extends Level{

	public StartMenu(String path) {
		super(path);
		
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
		for (int i = 0; i < 5; i++) {
			add(new RandomBear(4, 4));
			add(new RandomBear(27, 4));
			add(new RandomBear(27, 27));
			add(new RandomBear(4, 27));

		}
		
		add(new UIElement(0, 0, Sprite.playerMario_still));
		add(new UIElement(16, 0, Sprite.playerPacman_right));
		add(new UIElement(32, 0, Sprite.playerDinosaur));
		add(new UIElement(48, 0, Sprite.playerDinosaur));
		
	}
	
	
	

}
