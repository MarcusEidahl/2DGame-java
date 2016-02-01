package marcuseidahl.entity.mob;

import java.util.List;

import marcuseidahl.entity.mob.player.PacmanPlayer;
import marcuseidahl.entity.mob.player.Player;
import marcuseidahl.graphics.Sprite;

public class PacmanOrb extends Mob{
	
	
	public PacmanOrb(int x, int y) {
		this.x = x * 16;
		this.y = y * 16;
		meleeDamage = 0;
		speed =  0;
		health = 1;
		sprite =  Sprite.projectile_Pokeball;

		
	}
	
	public void update() {
		
		List<Player> players = level.getPlayers();
		for(int i = 0; i < players.size(); i++) {
			Player e = players.get(i);
			if(e instanceof PacmanPlayer){
				if(Math.sqrt((x - e.getX()) * (x - e.getX()) + (y - e.getY()) * (y - e.getY())) < 13) {
					((PacmanPlayer) e).addScore();
					health = 0;
				}
			}
		}
		
		if(health <= 0) {
			remove();
		}
	}
}
