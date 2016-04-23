package marcuseidahl.entity.mob;

import java.util.List;

import marcuseidahl.entity.mob.player.Player;
import marcuseidahl.entity.projectile.Projectile;
import marcuseidahl.graphics.Sprite;

public class ChaserBoar extends Mob{
	
	Player player;
	
	public ChaserBoar(int x, int y) {
		this.x = x * 16;
		this.y = y * 16;
		meleeDamage = 100;
		speed = .8;
		health = 100;
		sprite =  Sprite.mobBoar_still;
	}

	
	public void update() {
		
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
		if(time >= Integer.MAX_VALUE - 1000) {
			time = 0;
		}
		time++;
		List<Player> players = level.getPlayers(this, 7 * 16);
		
		if(time % 30 == 0 && players.size() > 0) {
			xa = 0;
			ya = 0;
			player = players.get(0);
			if(player.getX() < x) xa -= speed;
			if(player.getX() > x) xa += speed;
			if(player.getY() < y) ya -= speed;
			if(player.getY() > y) ya += speed;
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
