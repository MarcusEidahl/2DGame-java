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

public class Game extends Canvas implements Runnable {
	
	//convention
	private static final long serialVersionUID = 1L;
	
	//Game title on the window
	public static String title = "2D Game Engine";
	
	//Resolution with 16:9 aspect ratio
	public static int width = 400;
	public static int height = width / 16 * 9; //168 @ 300 and 279 @ 500
	public static int scale = 3;
	
	//Size of a randomly generated level
	public static int RANDOM_LEVEL_SIZE = 64;
	
	//Variable declarations
	private Thread thread;
	private JFrame frame;
	private Keyboard key;
	private Level level;
	private Player player;
	private boolean running = false;
	private int survivalHighScore = 0;
	private int pacmanHighScore = 0;
	private int dodgeblockHighScore = 0;
	private Screen screen;
	
	//private GameClient socketClient;
	//private GameServer socketServer;
	
	public enum GameState {
		START_MENU, PACMAN, SURVIVAL, DODGEBLOCK
	}
	public GameState gameState;
	TileCoordinate playerSpawn = new TileCoordinate(width / 32, height / 32);

	
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int [] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	
	public Game() {
		 Dimension size = new Dimension(width*scale, height*scale);
		 setPreferredSize(size);
		 screen = new Screen(width, height);
		 frame = new JFrame();
		 key = new Keyboard();
		 level = Level.menuStart;
		 gameState = GameState.START_MENU;
		 player = new NullPlayer(playerSpawn.getX() + 32, playerSpawn.getY() + 32);
		 level.add(player);
		 
		 addKeyListener(key);
		 
		 Mouse mouse = new Mouse();
		 addMouseListener(mouse);
		 addMouseMotionListener(mouse);
		 
	}
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
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
	
	public synchronized void stop() {
		running = false;
		try{
			thread.join();
		} catch (InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus(); //effectively clicks the window on start
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				//System.out.println(updates + "ups," + frames + " fps");
				frame.setTitle(title + "   |   " + updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
			
		}
		stop();
	}
	

	public void update() {
		key.update();
		level.update();
		
		//Game State Updates
		
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
		}
		
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
		
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			//triple buffering
			createBufferStrategy(3);
			return;
		}
		screen.clear();
		int xScroll = (int) player.getX() - screen.width / 2;
		int yScroll = (int) player.getY() - screen.height / 2;

		level.render(xScroll, yScroll, screen);
				
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
		//Level Specific Overlays
		
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
		Game game = new Game();
		
		//setting up the frame
		game.frame.setResizable(false);
		game.frame.setTitle(title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		
		game.start();
		
	}
}
