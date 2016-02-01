package marcuseidahl.level;

import java.util.Random;

import marcuseidahl.level.tile.Tile;

public class RandomLevel extends Level {
	
	private static final Random random = new Random();
	public final int NUM_RANDOM_TILES = 4;
	
	public RandomLevel(int width, int height){
		super(width, height);
	}
	
	protected void generateLevel() {
		int intTiles[] = new int[width * height];
		tiles = new int[width * height];
		for (int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				intTiles[x + y * width] = random.nextInt(NUM_RANDOM_TILES);
			}
		}
		for(int i = 0; i < intTiles.length; i++) {
			if(intTiles[i] == 0) tiles[i] = Tile.color_grass;
			if(intTiles[i] == 1) tiles[i] = Tile.color_water;
			if(intTiles[i] == 2) tiles[i] = Tile.color_sand;
			if(intTiles[i] == 3) tiles[i] = Tile.color_mountain;
		}
	}
}
