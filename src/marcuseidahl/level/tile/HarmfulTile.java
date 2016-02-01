package marcuseidahl.level.tile;

import marcuseidahl.graphics.Screen;
import marcuseidahl.graphics.Sprite;

public class HarmfulTile extends Tile {

	public HarmfulTile(Sprite sprite) {
		this.sprite = sprite;
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile(x * screen.TILE_SIZE, y * screen.TILE_SIZE, this);
	}
	
	public boolean isHarmful() { 
		return true;
	}
	
}
