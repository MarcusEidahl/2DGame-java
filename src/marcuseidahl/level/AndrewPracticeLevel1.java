package marcuseidahl.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AndrewPracticeLevel1 extends Level {

	public AndrewPracticeLevel1(String path) {
		super(path);
	}
	
	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(PracticeLevel1.class.getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			tiles = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Exception! Could not load level file!");
		}
		
	}
		
	
	protected void generateLevel() { 
	
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
	}
}
