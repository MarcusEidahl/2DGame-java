package marcuseidahl.entity.mob;

import java.util.List;

import marcuseidahl.entity.mob.player.Player;
import marcuseidahl.entity.projectile.Projectile;
import marcuseidahl.graphics.Sprite;
import marcuseidahl.level.Node;
import marcuseidahl.util.Vector2i;

public class AStarGoomba extends Mob{

	private List<Node> path;
	Player player;
	
	public AStarGoomba(int x, int y) {
		this.x = x * 16;
		this.y = y * 16;		
		meleeDamage = 100;
		speed = .25;
		health = 100;
		sprite =  Sprite.mobGoomba_still;
	}
	
	
	public void update() {
		time++;
		
		//checking if hit by a projectile
		List<Projectile> projectiles = level.getProjectiles();
		for(int i = 0; i < projectiles.size(); i++){
			Projectile p = projectiles.get(i);
			if(Math.sqrt((x - p.getX()) * (x - p.getX()) + (y - p.getY()) * (y - p.getY())) < 13){
				health -= p.getDamage();
				p.remove();
			}
		}
		if(health <= 0) {
			remove();
		}
		
		
		//AI pathing
		
		int px, py;
		if(level.getClientPlayer() != null){
			px = (int) level.getClientPlayer().getX();
			py = (int) level.getClientPlayer().getY();
		} else {
			px = -1;
			py = -1;
		}
		Vector2i start = new Vector2i((int) getX() / 16, (int) getY() / 16);
		Vector2i goal = new Vector2i(px / 16, py / 16);
		if(level.getClientPlayer() != null) path = level.findPath(start, goal);
		if(path != null) {
			if(path.size() > 0) {
				Vector2i vec = path.get(path.size() - 1).tile;
				if (x / 16 <= vec.getX()) xa = speed; 
				if (x / 16 > vec.getX()) xa = -speed; 
				if (y / 16 <= vec.getY()) ya = speed; 
				if (y / 16 > vec.getY()) ya = -speed; 
			}
		}
		
		//movement
		if (ya > 0) {
			dir = Direction.UP;
		}
		else if(ya < 0) {
			dir = Direction.DOWN;
		}
		if(xa < 0) {
			dir = Direction.LEFT;
		}
		else if(xa > 0) {
			dir = Direction.RIGHT;
		}
		
		if(xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		}
		else {
			walking = false;
		}
		
		
	}	
	
}
