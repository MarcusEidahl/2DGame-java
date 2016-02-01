package marcuseidahl.level.tile;

import marcuseidahl.graphics.Sprite;

public class SolidTile extends Tile{

	public SolidTile(Sprite sprite) {
		this.sprite = sprite;
	}

	public boolean solid() {
		return true;
	}
}