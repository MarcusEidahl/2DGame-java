package marcuseidahl.entity.mob.player;


import marcuseidahl.graphics.Screen;
import marcuseidahl.graphics.Sprite;
import marcuseidahl.input.Keyboard;
import marcuseidahl.level.PacmanLevel;

public class PacmanPlayer extends Player{
	
	Sprite closed;
	private int score = 0;

	public PacmanPlayer(int x, int y, Keyboard input) {
		this.x = x;
		this.y = y;
		this.input = input;
		
		speed = 1;
		health = 100;
		
		animUp = Sprite.playerPacman_upAnim;
		stillUp = Sprite.playerPacman_up;
		animDown = Sprite.playerPacman_upAnim;
		stillDown = Sprite.playerPacman_up;
		animLeft = Sprite.playerPacman_rightAnim;
		stillLeft = Sprite.playerPacman_right;
		animRight = Sprite.playerPacman_rightAnim;
		stillRight = Sprite.playerPacman_right;
		closed = Sprite.playerPacman_closed;
		
		sprite = stillRight;

	}
	
	public void addScore() {
		score += 10;
	}
	protected void updateAbilities() {
		
		if(level instanceof PacmanLevel) {
			if(x <= 0){
				x = level.getWidth() * 16 - 17;
			}
			if(x >= level.getWidth() * 16 - 16){
				x = 1;
			}
		}

	}
	
	public int getScore() {
		return score;
	}
	
	public void render(Screen screen) {
		//no flip = 0, xflip = 1, yflip = 2, both flip = 3
		int flip = 0;	
		if(dir == Direction.UP) {
			flip = 0;
			sprite = stillUp;
			if(walking) {
				if(animationTimer % 30 >= 10) {
					sprite = animUp;
				}
				if(animationTimer% 30 >= 20) {
					sprite = closed;
				}
			}
		}
		if(dir == Direction.RIGHT) {
			flip = 0;
			sprite = stillRight;
			if(walking) {
				if(animationTimer % 30 >= 10) {
					sprite = animRight;
				}
				if(animationTimer% 30 >= 20) {
					sprite = closed;
				}
			}
		}
		if(dir == Direction.DOWN) {
			flip = 2;
			sprite = stillDown;
			if(walking) {
				if(animationTimer % 30 >= 10) {
					sprite = animDown;
				}
				if(animationTimer% 30 >= 20) {
					sprite = closed;
				}
			}
		}
		if(dir == Direction.LEFT) {
			flip = 1;
			sprite = stillLeft;
			if(walking) {
				if(animationTimer % 30 >= 10) {
					sprite = animLeft;
				}
				if(animationTimer% 30 >= 20) {
					sprite = closed;
				}
			}
		}

		screen.renderMob((int) x,(int) y, sprite, flip);
	}

}
