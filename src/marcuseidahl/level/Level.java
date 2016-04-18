package marcuseidahl.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import marcuseidahl.entity.Entity;
import marcuseidahl.entity.UI.UIElement;
import marcuseidahl.entity.mob.player.Player;
import marcuseidahl.entity.particle.Particle;
import marcuseidahl.entity.projectile.Projectile;
import marcuseidahl.graphics.Screen;
import marcuseidahl.level.tile.Tile;
import marcuseidahl.util.Vector2i;


public abstract class Level {
	
	protected int width, height;
	protected int tiles[];
	protected int time;
	
	protected Random random = new Random();
	
	protected List<Entity> entities = new ArrayList<Entity>();
	protected List<Projectile> projectiles = new ArrayList<Projectile>();
	protected List<Particle> particles = new ArrayList<Particle>();
	protected List<Player> players = new ArrayList<Player>();
	protected List<UIElement> UIElements = new ArrayList<UIElement>();
	
	private Comparator<Node> nodeSorter = new Comparator<Node>() {
		public int compare(Node n0, Node n1) {
			if(n1.fCost < n0.fCost) return 1;
			if(n1.fCost > n0.fCost) return -1;
			return 0;
		}
	};
	
	public static Level practiceLevel1 = new PracticeLevel1("/levels/PracticeLevel1.png");
	public static Level friendTestLevel1 = new FriendTestLevel1("/levels/FriendTestLevel1.png");
	public static Level friendTestLevel2 = new FriendTestLevel2("/levels/FriendTestLevel2.png");
	public static Level andrewSmileyLevel = new AndrewSmileyLevel("/levels/SmileyLevel.png");
	public static Level andrewPracticeLevel1 = new AndrewPracticeLevel1("/levels/AndrewPractice1.png");
	public static Level pacmanLevel = new PacmanLevel("/levels/PacmanLevel.png");
	public static Level dinosaurLevel = new DodgeblockLevel("/levels/DinosaurLevel.png");
	public static Level chessLevel = new ChessLevel("/levels/chessBoard.png");
	
	public static Level menuStart = new StartMenu("/levels/menu_Start.png");
	
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		generateLevel();
	}
	
	public Level(String path) {
		loadLevel(path);
		generateLevel();
				
	}
	
	protected void generateLevel() {
		
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
	
	protected void spawnInitialMobs() {
		
	}
	
	public void update() {
		remove();
		time();
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}
		
		for(int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).update();
		}
		
		for(int i = 0; i < particles.size(); i++) {
			particles.get(i).update();
		}
		
		for(int i = 0; i < players.size(); i++) {
			players.get(i).update();
		}
		
		
		
	}
	
	private void remove() {
		for(int i = 0; i < entities.size(); i++) {
			if(entities.get(i).isRemoved()) entities.remove(i);
		}
		
		for(int i = 0; i < projectiles.size(); i++) {
			if(projectiles.get(i).isRemoved()) projectiles.remove(i);
		}
		
		for(int i = 0; i < particles.size(); i++) {
			if(particles.get(i).isRemoved()) particles.remove(i);
		}
		
		for(int i = 0; i < players.size(); i++) {
			if(players.get(i).isRemoved()) players.remove(i);
		}
	}
	
	public List<Projectile> getProjectiles() {
		return projectiles;
	}
	
	protected void time() {

	}
	
	public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset){
		boolean solid = false;
		
		if(getTile((int) (x) / 16, (int) (y) / 16).solid()) solid = true;
		if(getTile((int) (x) / 16, (int) (y + 15) / 16).solid()) solid = true;
		if(getTile((int) (x + 15) / 16, (int) (y) / 16).solid()) solid = true;
		if(getTile((int) (x + 15) / 16, (int) (y + 15) / 16).solid()) solid = true;
		
		return solid;
	}
	
	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll / screen.TILE_SIZE;
		int x1 = (xScroll + screen.width + screen.TILE_SIZE) / screen.TILE_SIZE;
		int y0 = yScroll / screen.TILE_SIZE;
		int y1 = (yScroll + screen.height + screen.TILE_SIZE) / screen.TILE_SIZE;

		for(int y = y0; y < y1; y++){
			for(int x = x0; x < x1; x++){
				getTile(x, y).render(x, y, screen);
			}
		}
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}
		for(int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);
		}
		for(int i = 0; i < particles.size(); i++) {
			particles.get(i).render(screen);
		}
		for(int i = 0; i < players.size(); i++) {
			players.get(i).render(screen);
		}
		for(int i = 0; i < UIElements.size(); i++) {
			UIElements.get(i).render(screen);
		}
		
	}
	
	//Grass = 0xFF00FF00		  0 255   0
	//Sand = 0xFFFFFF99			255 255 153
	//Water = 0xFF7092BE		112 146 190
	//Mountain = 0xFF716117		113  97  23
	//Lava = 0xFFFF0000			255	  0   0
	public Tile getTile(int x, int y) {
		if(x < 0 || x >= width || y < 0 || y >= height) return Tile.voidTile;
		if(tiles[x + y * width] == Tile.color_grass) return Tile.grass;
		if(tiles[x + y * width] == Tile.color_water) return Tile.water;
		if(tiles[x + y * width] == Tile.color_sand) return Tile.sand;
		if(tiles[x + y * width] == Tile.color_mountain) return Tile.mountain;
		if(tiles[x + y * width] == Tile.color_lava) return Tile.lava;
		if(tiles[x + y * width] == Tile.color_black) return Tile.chessBlack;
		if(tiles[x + y * width] == Tile.color_white) return Tile.chessWhite;

		return Tile.voidTile;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void add(Entity e) {
		e.init(this);
		if(e instanceof Particle) {
			particles.add((Particle) e);
		}
		else if(e instanceof Projectile) {
			projectiles.add((Projectile) e);
		}
		else if(e instanceof Player) {
			players.add((Player) e);
		}
		else if(e instanceof UIElement) {
			UIElements.add((UIElement) e);
		}
		else {
			entities.add(e);
		}
	}
	
	public List<Entity> getEntities() {
		return entities;
	}
	
	public List<Entity> getEntities(Entity e, int radius) {
		List<Entity> result = new ArrayList<Entity>();
		double ex = e.getX();
		double ey = e.getY();
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			double x = entity.getX();
			double y = entity.getY();
			double dx = x - ex;
			double dy = y - ey;
			if(Math.sqrt(dx * dx + dy * dy) <= radius) result.add(entity);
		}
		return result;
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public List<Player> getPlayers(Entity e, int radius) {
		List<Player> result = new ArrayList<Player>();
		double ex = e.getX();
		double ey = e.getY();
		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			double x = player.getX();
			double y = player.getY();
			double dx = x - ex;
			double dy = y - ey;
			if(Math.sqrt(dx * dx + dy * dy) <= radius) result.add(player);
		}
		return result;
	}
	
	public Player getPlayerAt(int index) {
		if(players.size() > index) return players.get(index);
		else return null;
	}
	
	public Player getClientPlayer() {
		if(players.size() > 0) return players.get(0);
		else return null;
	}
	
	public List<Node> findPath(Vector2i start, Vector2i goal) {
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		Node current = new Node(start, null, 0, start.getDistance(goal));
		openList.add(current);
		while(openList.size() > 0) {
			Collections.sort(openList, nodeSorter);
			current = openList.get(0);
			if(current.tile.equals(goal)) {
				List<Node> path = new ArrayList<Node>();
				while(current.parent != null){
					path.add(current);
					current = current.parent;
				}
				openList.clear();
				closedList.clear();
				return path;
			}
			openList.remove(current);
			closedList.add(current);
			for (int i = 0; i < 9; i++) {
				if(i == 4) continue;
				int x = current.tile.getX();
				int y = current.tile.getY();
				int xi = (i % 3) - 1;
				int yi = (i / 3) - 1;
				Tile at = getTile(x + xi, y + yi);
				if (at == null) continue;
				if(at.solid()) continue;
				Vector2i a = new Vector2i(x + xi, y + yi);
				double gCost = current.gCost + current.tile.getDistance(a);
				double hCost = a.getDistance(goal);
				Node node = new Node(a, current, gCost, hCost);
				if(vectInList(closedList, a) && gCost >= node.gCost) continue;
				if(!vectInList(openList, a) || gCost < node.gCost) openList.add(node);
			}
		}
		closedList.clear();
		return null;
	}
		
	public boolean vectInList(List <Node> list, Vector2i vector) {
		for (Node n : list) {
			if(n.tile.equals(vector)) return true;
		}
		return false;
	}
	
	
	
	public void reset() {

	}

	

	
}
