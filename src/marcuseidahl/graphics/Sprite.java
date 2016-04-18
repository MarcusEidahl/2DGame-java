package marcuseidahl.graphics;

public class Sprite {
	
	private final static int VOID_COLOR = 0;
	public final int SIZE;
	private int x, y;
	private int width, height;
	public int[] pixels;
	private Spritesheet sheet;
	
	//Tile Sprites
	public static Sprite voidSprite = new Sprite(16, VOID_COLOR);
	public static Sprite grass = new Sprite(16 , 0, 0, Spritesheet.tiles);
	public static Sprite water = new Sprite (16, 1, 0, Spritesheet.tiles);
	public static Sprite sand = new Sprite (16, 2, 0, Spritesheet.tiles);
	public static Sprite mountain = new Sprite (16, 3, 0, Spritesheet.tiles);
	public static Sprite path = new Sprite (16, 4, 0, Spritesheet.tiles);
	public static Sprite bridge_horizontal = new Sprite (16, 5, 0, Spritesheet.tiles);
	public static Sprite lava = new Sprite (16, 6, 0 , Spritesheet.tiles);
	public static Sprite dinosaurFloor = new Sprite(16, 7, 0, Spritesheet.tiles);
	public static Sprite dinosaurBlockGreen = new Sprite(16, 8, 0, Spritesheet.tiles);
	public static Sprite dinosaurBlockRed = new Sprite(16, 9, 0, Spritesheet.tiles);
	public static Sprite dinosaurBlockPurple = new Sprite(16, 10, 0, Spritesheet.tiles);
	public static Sprite chessLight = new Sprite(16, 11, 0, Spritesheet.tiles);
	public static Sprite chessDark = new Sprite(16, 12, 0, Spritesheet.tiles);

	//Chess Sprites
	public static Sprite chessRookWhite = new Sprite(16, 0, 9, Spritesheet.tiles);
	public static Sprite chessKnightWhite = new Sprite(16, 1, 9, Spritesheet.tiles);
	public static Sprite chessBishopWhite = new Sprite(16, 2, 9, Spritesheet.tiles);
	public static Sprite chessKingWhite = new Sprite(16, 3, 9, Spritesheet.tiles);
	public static Sprite chessQueenWhite = new Sprite(16, 4, 9, Spritesheet.tiles);
	public static Sprite chessPawnWhite = new Sprite(16, 5, 9, Spritesheet.tiles);
	
	public static Sprite chessRookBlack = new Sprite(16, 0, 8, Spritesheet.tiles);
	public static Sprite chessKnightBlack = new Sprite(16, 1, 8, Spritesheet.tiles);
	public static Sprite chessBishopBlack = new Sprite(16, 2, 8, Spritesheet.tiles);
	public static Sprite chessKingBlack = new Sprite(16, 3, 8, Spritesheet.tiles);
	public static Sprite chessQueenBlack = new Sprite(16, 4, 8, Spritesheet.tiles);
	public static Sprite chessPawnBlack = new Sprite(16, 5, 8, Spritesheet.tiles);


	//Player Sprites	
	public static Sprite playerMario_still = new Sprite(16, 0, 15, Spritesheet.tiles);
	public static Sprite playerMario_right_walk = new Sprite(16, 1, 15, Spritesheet.tiles);
	
	public static Sprite playerTrainer_right = new Sprite(16, 4, 15, Spritesheet.tiles);
	
	public static Sprite playerPacman_right = new Sprite(16, 9, 15, Spritesheet.tiles);
	public static Sprite playerPacman_rightAnim = new Sprite(16, 9, 14, Spritesheet.tiles);
	public static Sprite playerPacman_up = new Sprite(16, 10, 15, Spritesheet.tiles);
	public static Sprite playerPacman_upAnim = new Sprite(16, 10, 14, Spritesheet.tiles);
	public static Sprite playerPacman_closed = new Sprite(16, 11, 15, Spritesheet.tiles);
	
	public static Sprite playerDinosaur = new Sprite(16, 0, 14, Spritesheet.tiles);


	
	//Mob Sprites
	public static Sprite mobBear_still = new Sprite(16, 0, 11, Spritesheet.tiles);
	public static Sprite mobBoar_still = new Sprite(16, 0, 12, Spritesheet.tiles);
	public static Sprite mobGoomba_still = new Sprite(16, 0, 13, Spritesheet.tiles);
	public static Sprite mobMudkip_right = new Sprite(16, 8, 15, Spritesheet.tiles);
	
	public static Sprite mobRedGhost = new Sprite(16, 1, 11, Spritesheet.tiles);
	public static Sprite mobBlueGhost = new Sprite(16, 1, 12, Spritesheet.tiles);
	public static Sprite mobYellowGhost = new Sprite(16, 1, 13, Spritesheet.tiles);
	public static Sprite mobPinkGhost = new Sprite(16, 2, 11, Spritesheet.tiles);

	
	//Projectile Sprites
	public static Sprite projectile_MarioFireball = new Sprite(16, 0, 0, Spritesheet.projectiles);
	public static Sprite projectile_Shurikan = new Sprite(16, 0, 1, Spritesheet.projectiles);
	public static Sprite projectile_Pokeball = new Sprite(16, 3, 11, Spritesheet.tiles);
	
	//Particle Sprites
	public static Sprite particle_normal = new Sprite(3, 0xAAAAAA);
	public static Sprite particle_MarioFireball = new Sprite(3, 0xFF0000);
	public static Sprite particle_BearGuts = new Sprite(3, 0x663300);
	public static Sprite particle_Shurikan = new Sprite (3, 0x000000);
	
	//UI Sprites
	//public static Sprite button_back = new Sprite();
	//public static Sprite
	
	public Sprite(int size, int x, int y, Spritesheet sheet) {
		SIZE = size;
		this.width = SIZE;
		this.height = SIZE;
		pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		load();
	}
	
	public Sprite(int width, int height, int color) {
		SIZE = -1;
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		setColor(color);
	}
	
	public Sprite(int size, int color) {
		SIZE = size;
		this.width = SIZE;
		this.height = SIZE;
		pixels = new int[SIZE * SIZE];
		setColor(color);
	}
	
	private void setColor(int color) {
		for(int i = 0; i < width * height ; i++) {
			pixels[i] = color;
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	private void load() {
		for (int y = 0; y < SIZE; y++) {
			for( int x = 0; x < SIZE; x++) {
				pixels[x + y * SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SIZE];
			}
		}
	}
	
}
