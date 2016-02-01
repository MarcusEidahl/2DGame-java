package marcuseidahl.entity.particle;

import marcuseidahl.entity.Entity;
import marcuseidahl.graphics.Screen;
import marcuseidahl.graphics.Sprite;

public class Particle extends Entity{
	
	private final int LIFESPAN_VARIANCE = 10;
	
	private final double Z_FLOOR_HEIGHT = 6.0;
	private final double X_FLOOR_BOUNCE_SCALING = .8; 
	private final double Y_FLOOR_BOUNCE_SCALING = .8; 
	private final double Z_FLOOR_BOUNCE_SCALING = -.8;
	
	private final double X_WALL_BOUNCE_SCALING = -.5; 
	private final double Y_WALL_BOUNCE_SCALING = -.5; 
	private final double Z_WALL_BOUNCE_SCALING = -.5;
	

	
	
	private Sprite sprite;
	private int lifespan;
	private int time = 0;
	protected double xx, yy, zz;
	protected double xa, ya, za;
	
	
	public Particle(int x, int y, int lifespan, Sprite sprite) {
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.lifespan = lifespan + (random.nextInt(LIFESPAN_VARIANCE));
		this.sprite = sprite;
		
		this.xa = random.nextGaussian(); 
		this.ya = random.nextGaussian();
		this.zz = Z_FLOOR_HEIGHT;

	}
	
	public void update() {
		time++;
		if(time >= 12345) time = 0;
		if(time > lifespan) remove();
		za -= .1;
		
		//bounce off floor scaling
		if(zz < 0) {
			zz = 0;
			za *= Z_FLOOR_BOUNCE_SCALING;
			xa *= X_FLOOR_BOUNCE_SCALING;
			ya *= Y_FLOOR_BOUNCE_SCALING;
		}
		
		move(xx + xa, yy + ya + zz + za);

	}
	
	private void move(double x, double y) {
		
		//bounce off walls scaling
		if (collision(x, y)) {
			this.xa *= X_WALL_BOUNCE_SCALING;
			this.ya *= Y_WALL_BOUNCE_SCALING;
			this.za *= Z_WALL_BOUNCE_SCALING;
		}
		this.xx += xa;
		this.yy += ya;
		this.zz += za;
		
	}
	
	public boolean collision(double x, double y){
		boolean solid = false;
		for(int i = 0; i < 4; i++) {
			//multiplication scales the box and addition shifts it
			double xt = (x - i % 2 * 16) / 16;
			double yt = (y - i / 2 * 16) / 16;
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if (i % 2 == 0) ix = (int) Math.floor(xt);
			if (i / 2 == 0) iy = (int) Math.floor(yt);
			if(level.getTile(ix, iy).solid()) solid = true;
		}
		return solid;
	}

	public void render(Screen screen) {
		screen.renderSprite((int) xx, (int) yy - (int) zz, sprite, true);
	}
}
