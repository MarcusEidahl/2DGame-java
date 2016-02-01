package marcuseidahl.graphics;

import java.util.Random;

import marcuseidahl.entity.projectile.Projectile;
import marcuseidahl.level.tile.Tile;

public class Screen {
	
	public final int MAP_SIZE = 64;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;
	public final int TILE_SIZE = 16;
	public final int FILLER_COLOR = 0xFFFFAAFF;
	public int xOffset, yOffset;
	public int width, height;
	public int[] pixels;
	public int[] tiles = new int[MAP_SIZE * MAP_SIZE];
	
	private Random random = new Random();
	
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height]; //50400 @ 300 width
		
		for(int i = 0; i < MAP_SIZE * MAP_SIZE; i++){
			tiles[i] = random.nextInt(0xFFFFFF);
		}
	}
	
	public void clear(){
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed) {
		if(fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for(int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for( int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				int color = sprite.pixels[x + y * sprite.getWidth()];
				if(color != FILLER_COLOR) pixels[xa + ya * width] = color;			}
		}
		
	}
	
	public void renderTile(int xp, int yp, Tile tile) {
		xp -= xOffset;
		yp -= yOffset;
		for(int y = 0; y < tile.sprite.SIZE; y++) {
			int ya = y + yp;
			for(int x = 0; x < tile.sprite.SIZE; x++) {
				int xa = x + xp;
				if(xa < -tile.sprite.SIZE || xa >= width || ya < 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				pixels[xa + ya * width] = tile.sprite.pixels[x + y * tile.sprite.SIZE];
			}
		}
	}
	
	public void renderProjectile(int xp, int yp, Projectile p) {
		xp -= xOffset;
		yp -= yOffset;
		for(int y = 0; y < p.getSpriteSize(); y++) {
			int ya = y + yp;
			for(int x = 0; x < p.getSpriteSize(); x++) {
				int xa = x + xp;
				if(xa < -p.getSpriteSize() || xa >= width || ya < 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				int color = p.getSprite().pixels[x + y * p.getSpriteSize()];
				if(color != FILLER_COLOR) pixels[xa + ya * width] = color;
			}
		}
	}
	
	public void renderProjectile(int xp, int yp, Projectile p, double angle) {
		xp -= xOffset;
		yp -= yOffset;
		for(int y = 0; y < p.getSpriteSize(); y++) {
			int ya = y + yp;
			for(int x = 0; x < p.getSpriteSize(); x++) {
				int xa = x + xp;
				if(xa < -p.getSpriteSize() || xa >= width || ya < 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				int[] rPixels = rotate(p.getSprite().pixels, p.getSpriteSize(), p.getSpriteSize(), angle);
				int color = rPixels[x + y * p.getSpriteSize()];
				if(color != FILLER_COLOR) pixels[xa + ya * width] = color;
			}
		}
	}
	
	private int[] rotate(int[] pixels, int width, int height, double angle) {
		int[] result = new int[width * height];
		
		double nx_x = rotationX(-angle, 1.0, 0.0);
		double nx_y = rotationY(-angle, 1.0, 0.0);
		double ny_x = rotationX(-angle, 0.0, 1.0);
		double ny_y = rotationY(-angle, 0.0, 1.0);

		double x0 = rotationX(-angle, -width / 2.0, -height / 2.0) + width / 2.0;
		double y0 = rotationY(-angle, -width / 2.0, -height / 2.0) + height / 2.0;

		for (int y = 0; y < height; y++) {
			double x1 = x0;
			double y1 = y0;
			for (int x = 0; x < width; x++) { 
				int xx = (int) x1;
				int yy = (int) y1;
				int color = 0;
				if(xx < 0 || xx >= width || yy < 0 || yy >= height) color = FILLER_COLOR;
				else color = pixels[xx + yy * width];
				result[x + y * width] = color;
				x1 += nx_x;
				y1 += nx_y;
			}
			x0 += ny_x;
			y0 += ny_y;
		}
		
		return result;
	}
	
	private double rotationX(double angle, double x, double y) {
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		return x * cos + y * -sin;
	}
	
	private double rotationY(double angle, double x, double y) {
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		return x * sin + y * cos;
	}
	
	public void renderMob(int xp, int yp, Sprite sprite, int flip) {
		xp -= xOffset;
		yp -= yOffset;
		for(int y = 0; y < 16; y++) {
			int ya = y + yp;
			int ys = y;
			if(flip == 2 || flip == 3)  ys = 15 - y;
			for(int x = 0; x < 16; x++) {
				int xa = x + xp;
				int xs = x;
				if(flip == 1 || flip == 3) xs = 15 - x;
				if(xa < -16 || xa >= width || ya < 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				int color = sprite.pixels[xs + ys * 16];
				if(color != FILLER_COLOR) pixels[xa + ya * width] = color;
			}
		}
	}
	
	public void setOffset (int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	
	
	
	
	
	
	
}
