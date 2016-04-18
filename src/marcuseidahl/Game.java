package marcuseidahl;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import marcuseidahl.entity.mob.player.ChessPlayer;
import marcuseidahl.entity.mob.player.DinosaurPlayer;
import marcuseidahl.entity.mob.player.MarioPlayer;
import marcuseidahl.entity.mob.player.NullPlayer;
import marcuseidahl.entity.mob.player.PacmanPlayer;
import marcuseidahl.entity.mob.player.Player;
import marcuseidahl.graphics.Screen;
import marcuseidahl.input.Keyboard;
import marcuseidahl.input.Mouse;
import marcuseidahl.level.Level;
import marcuseidahl.level.TileCoordinate;

//this is the class that will be run
public class Game extends Canvas implements Runnable {
	
	//convention
	private static final long serialVersionUID = 1L;
	
	//Game title on the window
	public static String title = "2D Game Engine";
	
	//Resolution with 16:9 aspect ratio
	public final static int width = 400;
	public final static int height = width / 16 * 9; //168 @ 300 and 279 @ 500
	public final static int scale = 3;
	
	//Size of a randomly generated level
	public static int RANDOM_LEVEL_SIZE = 64;
	
	//Variable declarations
	private Thread thread;	//Game Thread
	private JFrame frame;	//Window
	private Keyboard key;	//For taking keyboard input
	private Level level;	//holds the level to be updated and rendered
	private Player player;	//hold the player for this client
	private Screen screen;	//screen that the graphics will be rendered to

	private boolean running = false;	//true while the game is running
	
	//Holds the high scores for the games while the game is running
	private int survivalHighScore = 0;
	private int pacmanHighScore = 0;
	private int dodgeblockHighScore = 0;
	
	//private GameClient socketClient;
	//private GameServer socketServer;
	
	//Holds what state the game is in
	public enum GameState {
		START_MENU, PACMAN, SURVIVAL, DODGEBLOCK, CHESS
	}
	public GameState gameState;
	
	//Default player spawn point
	TileCoordinate playerSpawn = new TileCoordinate(width / 32, height / 32);

	//Init the image to be rendered
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int [] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	//Creates a game 
	public Game() {
		
		//Init the frame, screen, input, and initial game settings
		Dimension size = new Dimension(width*scale, height*scale);
		setPreferredSize(size);
		screen = new Screen(width, height);
		frame = new JFrame();
		key = new Keyboard();
		level = Level.menuStart;
		level.reset();
		gameState = GameState.START_MENU;
		player = new NullPlayer(playerSpawn.getX() + 32, playerSpawn.getY() + 32);
		level.add(player);
		
		addKeyListener(key);
		
		Mouse mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		
	}
	
	//Starts the main game loop thread
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Main Game Loop");
		thread.start();
		
		/* Testing basic network code
		if(JOptionPane.showConfirmDialog(this, "Do you want to run the server?") == 0) {
			socketServer = new GameServer(this);
			socketServer.start();
		}
		
		socketClient = new GameClient(this, "localhost");
		socketClient.start();
		socketClient.sendData("ping".getBytes());
		*/
	}
	
	//Closes the current Game thread
	public synchronized void stop() {
		running = false;
		try{
			thread.join();
		} catch (InterruptedException e){
			e.printStackTrace();
		}
	}
	
	/*What the Game thread does 
	 * NOTE: CURRENTLY ONLY WORKS CONSISTENTLY IF UPDATES STAYS AT 60 PER SECOND BECAUSE MOVEMENT ASSUMES 60 UPDATES PER SECOND
	 * TODO: CALCULATE A DELTA AND REWORK ALL THE MOVEMENT TO MOVE AS A FACTOR OF DELTA
	 * TODO: ADD A FRAMERATE CAP
	 */ 
	public void run() {
		long lastTime = System.nanoTime();
		//long timer = System.currentTimeMillis();
		long timer = 0;		//counts until the next second
		final double nsTickTime = 1000000000.0 / 60.0;	// 1/60th of a second in nanoseconds
		double delta = 0;	//Time between loop iterations in nanoseconds
		double tickPercent = 0; // Percentage of the time before next update
		int frames = 0;		// how many frames were rendered in the last second
		int updates = 0;	// how many updates there were in the last second
		requestFocus();		// effectively clicks the window on start
		while (running) {
			long now = System.nanoTime();
			delta = now - lastTime;
			timer += delta;
			tickPercent += (now - lastTime) / nsTickTime; 
			lastTime = now;
			while (tickPercent >= 1) {
				update();
				updates++;
				tickPercent--;
			}
			render();
			frames++;
			
			//Every second update the frames per second and updates per second
			if(timer >= 1000000000) {
				timer = 0;
				frame.setTitle(title + "   |   " + updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
			
		}
		stop();
	}
	
	//Update the logic for everything in the game
	public void update() {
		key.update();
		level.update();
		
		System.out.println("X: " + Mouse.getX() + "\nY: " + Mouse.getY() + "\n");
		
		//Game State Updates
		
		/* START MENU
		 * This screen is where the player detirmines which game to play and it displays the high scores for each game 
		 */
		if(gameState == GameState.START_MENU) {
			if(Mouse.mouseB() == 1 && Mouse.getX() >= 0 * scale  && Mouse.getX() <= 15 * scale && Mouse.getY() >= 0 * scale && Mouse.getY() <= 15 * scale) {
				level = Level.friendTestLevel1;
				level.reset();
				playerSpawn.setX(3);
				playerSpawn.setY(3);
				player = new MarioPlayer(playerSpawn.getX(), playerSpawn.getY() , key);
				level.add(player);
				gameState = GameState.SURVIVAL;
			}
			if(Mouse.mouseB() == 1 && Mouse.getX() >= 16 * scale  && Mouse.getX() <= 31 * scale && Mouse.getY() >= 0 * scale && Mouse.getY() <= 15 * scale) {
				level = Level.pacmanLevel;
				level.reset();
				playerSpawn.setX(13);
				playerSpawn.setY(11);
				player = new PacmanPlayer(playerSpawn.getX(), playerSpawn.getY() , key);
				level.add(player);
				gameState = GameState.PACMAN;
			}
			if(Mouse.mouseB() == 1 && Mouse.getX() >= 32 * scale  && Mouse.getX() <= 47 * scale && Mouse.getY() >= 0 * scale && Mouse.getY() <= 15 * scale) {
				level = Level.dinosaurLevel;
				level.reset();
				playerSpawn.setX(16);
				playerSpawn.setY(16);
				player = new DinosaurPlayer(playerSpawn.getX(), playerSpawn.getY() , key);
				level.add(player);
				gameState = GameState.DODGEBLOCK;
			}
			if(Mouse.mouseB() == 1 && Mouse.getX() >= 48 * scale  && Mouse.getX() <= 63 * scale && Mouse.getY() >= 0 * scale && Mouse.getY() <= 15 * scale) {
				level = Level.chessLevel;
				level.reset();
				playerSpawn.setX(5);
				playerSpawn.setY(5);
				player = new ChessPlayer(playerSpawn.getX(), playerSpawn.getY(), key, true);
				level.add(player);
				gameState = GameState.CHESS;
			}
		}
		
		
		// PACMAN GAME
		if(gameState == GameState.PACMAN) {
			if(key.p) {
				level.reset();
				player = new PacmanPlayer(playerSpawn.getX(), playerSpawn.getY() , key);
				level.add(player);
			}
			if(Mouse.mouseB() == 1 && Mouse.getX() > (width - 16) * scale && Mouse.getY() > (height - 16) * scale) {
				level = Level.menuStart;
				level.reset();
				playerSpawn.setX(width/32);
				playerSpawn.setY(height/32);
				player = new NullPlayer(playerSpawn.getX() + 32, playerSpawn.getY() + 32);
				level.add(player);
				gameState = GameState.START_MENU;
			}
		}
		
		//SURVIVAL GAME
		if(gameState == GameState.SURVIVAL) {
			if(key.p) {
				level.reset();
				player = new MarioPlayer(playerSpawn.getX(), playerSpawn.getY() , key);
				level.add(player);
			}
			if(Mouse.mouseB() == 1 && Mouse.getX() > (width - 16) * scale && Mouse.getY() > (height - 16) * scale) {
				level = Level.menuStart;
				level.reset();
				playerSpawn.setX(width/32);
				playerSpawn.setY(height/32);
				player = new NullPlayer(playerSpawn.getX() + 32, playerSpawn.getY() + 32);
				level.add(player);
				gameState = GameState.START_MENU;
			}
		}
		
		//DODGEBLOCK GAME
		if(gameState == GameState.DODGEBLOCK) {
			if(key.p) {
				level.reset();
				player = new DinosaurPlayer(playerSpawn.getX(), playerSpawn.getY() , key);
				level.add(player);
			}
			if(Mouse.mouseB() == 1 && Mouse.getX() > (width - 16) * scale && Mouse.getY() > (height - 16) * scale) {
				level = Level.menuStart;
				level.reset();
				playerSpawn.setX(width/32);
				playerSpawn.setY(height/32);
				player = new NullPlayer(playerSpawn.getX() + 32, playerSpawn.getY() + 32);
				level.add(player);
				gameState = GameState.START_MENU;
			}
			if(Mouse.mouseB() == 1 && Mouse.getX() > (width - 16) * scale && Mouse.getY() > (height - 16) * scale) {
				level = Level.menuStart;
				level.reset();
				playerSpawn.setX(width/32);
				playerSpawn.setY(height/32);
				player = new NullPlayer(playerSpawn.getX() + 32, playerSpawn.getY() + 32);
				level.add(player);
				gameState = GameState.START_MENU;
			}
		}
		
		//CHESS
		if(gameState == GameState.CHESS) {
			if(key.p) {
				level.reset();
				player = new ChessPlayer(playerSpawn.getX(), playerSpawn.getY(), key, true);
				level.add(player);
			}
			if(Mouse.mouseB() == 1 && Mouse.getX() > (width - 16) * scale && Mouse.getY() > (height - 16) * scale) {
				level = Level.menuStart;
				level.reset();
				playerSpawn.setX(width/32);
				playerSpawn.setY(height/32);
				player = new NullPlayer(playerSpawn.getX() + 32, playerSpawn.getY() + 32);
				level.add(player);
				gameState = GameState.START_MENU;
			}
		}
		
	}
	
	//Draw all the objects to the screen
	public void render() {
		
		//Set triple buffering if not already set
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			//triple buffering
			createBufferStrategy(3);
			return;
		}
		
		//Clears the previous render
		screen.clear();
		
		//Set the player as the center of the screen
		int xScroll = (int) player.getX() - screen.width / 2;
		int yScroll = (int) player.getY() - screen.height / 2;
		
		//Render the level with the player in the center
		level.render(xScroll, yScroll, screen);
				
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
		/*Level Specific Overlays
		 * TODO: CUSTOM TEXT INSTEAD OF JAVA GRAPHICS
		 */
		if(gameState == GameState.START_MENU) {
			g.setColor(Color.BLACK);
			g.setFont(new Font("Verdana", 0, 50));
			g.drawString("Survival High Score: " + survivalHighScore, 130 * scale, 50 * scale);
			g.drawString("Pacman High Score: " + pacmanHighScore, 130 * scale, 100 * scale);
			g.drawString("Dodgeblock High Score: " + dodgeblockHighScore, 130 * scale, 150 * scale);


		}
		if(gameState == GameState.PACMAN) {
			g.setColor(Color.GRAY);
			g.setFont(new Font("Verdana", 0, 25));
			int score = player.getScore();
			g.drawString("Score: " + Integer.toString(player.getScore()), 5, 25);
			if(score > pacmanHighScore) pacmanHighScore = score;
				g.drawString("High Score: " + Integer.toString(pacmanHighScore), 5, 55);
				
		}
		
		if(gameState == GameState.SURVIVAL) {
			g.setColor(Color.GRAY);
			g.setFont(new Font("Verdana", 0, 25));
			int score = player.getScore();
			g.drawString("Score: " + Integer.toString(player.getScore()), 5, 25);
			if(score > survivalHighScore) survivalHighScore = score;
				g.drawString("High Score: " + Integer.toString(survivalHighScore), 5, 55);
		}
		
		if(gameState == GameState.DODGEBLOCK) {
			g.setColor(Color.GRAY);
			g.setFont(new Font("Verdana", 0, 25));
			int score = player.getScore();
			g.drawString("Score: " + Integer.toString(player.getScore()), 5, 25);
			if(score > dodgeblockHighScore) dodgeblockHighScore = score;
				g.drawString("High Score: " + Integer.toString(dodgeblockHighScore), 5, 55);
		}
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args){
	
		//Creates a Game
		Game game = new Game();
		
		//init the frame that the graphics will be drawn to
		game.frame.setResizable(false);
		game.frame.setTitle(title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		
		//Runs the game until it exits
		game.start();
		
	}
}
