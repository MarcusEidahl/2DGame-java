package marcuseidahl.entity.UI;

import marcuseidahl.entity.Entity;
import marcuseidahl.graphics.Screen;
import marcuseidahl.graphics.Sprite;

public class UIElement extends Entity{
	
	protected Sprite sprite;
	
	public UIElement(int x, int y, Sprite sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	public int getHeight() {
		return sprite.getHeight();
	}
	
	public int getWidth() {
		return sprite.getWidth();
	}
	
	public void render(Screen screen) {
		screen.renderSprite((int) x, (int) y, sprite, false);
	}
}
