package marcuseidahl.level.tile;

import marcuseidahl.graphics.Screen;
import marcuseidahl.graphics.Sprite;

public abstract class Tile {

	public int x, y;
	public Sprite sprite;
	
	public static Tile voidTile = new VoidTile(Sprite.voidSprite);
	public static Tile grass = new OpenTile(Sprite.grass);
	public static Tile water = new WaterTile(Sprite.water);
	public static Tile sand = new OpenTile(Sprite.sand);
	public static Tile mountain = new SolidTile(Sprite.mountain);
	public static Tile lava = new HarmfulTile(Sprite.lava);
	public static Tile dinosaurFloor = new OpenTile(Sprite.dinosaurFloor);
	
	public static final int color_grass = 0xFF00FF00;
	public static final int color_water = 0xFF7092BE;
	public static final int color_sand = 0xFFFFFF99;
	public static final int color_mountain = 0xFF716117;
	public static final int color_lava = 0xFFFF0000;

	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x * screen.TILE_SIZE, y * screen.TILE_SIZE, this);
	}
	
	public boolean solid(){
		return false;
	}
	
	public boolean isHarmful() {
		return false;
	}
	
	
}
