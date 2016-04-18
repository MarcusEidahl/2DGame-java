package marcuseidahl.level;

import marcuseidahl.entity.UI.UIElement;
import marcuseidahl.entity.chess.Bishop;
import marcuseidahl.entity.chess.ChessPiece;
import marcuseidahl.entity.chess.King;
import marcuseidahl.entity.chess.Knight;
import marcuseidahl.entity.chess.Pawn;
import marcuseidahl.entity.chess.Queen;
import marcuseidahl.entity.chess.Rook;
import marcuseidahl.entity.mob.player.Player;
import marcuseidahl.graphics.Sprite;
import marcuseidahl.input.Mouse;

public class ChessLevel extends Level{
	
	private static final int gridXstart = 360;
	private static final int gridXend = gridXstart + 16 * 8;
	
	private static final int gridYstart = 96;
	private static final int gridYend = gridYstart + 16 * 8;
	
	boolean selectedPiece = false;
	ChessPiece selected;
	
	boolean isWhiteTurn;
	ChessPiece[][] board;
	
	public ChessLevel(String path) {
		super(path);
		board = new ChessPiece[8][8];
		isWhiteTurn = true;
	}
	
	protected void time() {
		if(isYourTurn()) {
			
			if(Mouse.mouseB() == 1) {
				
				int curRow = -1, curCol = -1;
				int nextRow = -1, nextCol = -1;
				
				int mouseX = Mouse.getX();
				int mouseY = Mouse.getY();
				if(mouseX > gridXstart && mouseX < gridXend && mouseY > gridYstart && mouseY < gridYend) {
			
					if(!selectedPiece) {
						curRow = (mouseX - gridXstart) / 16;
						curRow = (mouseY - gridYstart) / 16;
						selected = board[curRow][curCol];
						selectedPiece = true;
					}
					else {
						nextRow = (mouseX - gridXstart) / 16;
						nextRow = (mouseY - gridYstart) / 16;
						
						if(selected.move(nextRow - curRow, nextCol - curCol, board)) {
							selectedPiece = false;
							selected = null;
							if(isWhiteTurn) isWhiteTurn = false;
							else isWhiteTurn = true;
						}
						else {
							selectedPiece = false;
							selected = null;
						}
					}
					
				}

				
				
			}
		}
	}
	
	protected void spawnInitialMobs() {
		
		ChessPiece c;
		
		//Add Pawns
		for(int i = 0; i < 8; i++) {
			c = new Pawn(i * 16, 16, i, 1, false);
			add(c);
			board[1][i] = c;
			
			c = new Pawn(i * 16, 16 * 6, i, 6, true);
			add(c);
			board[6][i] = c;
		}
		
		//Add Rooks
		c = new Rook(0, 0, 0, 0, false);
		add(c);
		board[0][0] = c;
		c = new Rook(7 * 16, 0, 0, 7, false);
		add(c);
		board[0][7] = c;
		c = new Rook(0, 7 * 16, 7, 0, true);
		add(c);
		board[7][0] = c;
		c = new Rook(7 * 16 , 7 * 16, 7, 7, true);
		add(c);
		board[7][7] = c;
		
		//Add Knights
		c = new Knight(1 * 16, 0, 0, 1, false);
		add(c);
		board[0][1] = c;
		c = new Knight(6 * 16, 0, 0, 6, false);
		add(c);
		board[0][6] = c;
		c = new Knight(1 * 16, 7 * 16, 7, 1, true);
		add(c);
		board[7][1] = c;
		c = new Knight(6 * 16 , 7 * 16, 7, 6, true);
		add(c);
		board[7][6] = c;
		
		//Add Bishops
		c = new Bishop(2 * 16, 0, 0, 2, false);
		add(c);
		board[0][2] = c;
		c = new Bishop(5 * 16, 0, 0, 5, false);
		add(c);
		board[0][5] = c;
		c = new Bishop(2 * 16, 7 * 16, 7, 2, true);
		add(c);
		board[7][2] = c;
		c = new Bishop(5 * 16 , 7 * 16, 7, 5, true);
		add(c);
		board[7][5] = c;
		
		//Add Kings and Queens
		c = new King(3 * 16, 0, 0, 3, false);
		add(c);
		board[0][3] = c;
		c = new Queen(4 * 16, 0, 0, 4, false);
		add(c);
		board[0][4] = c;
		c = new King(3 * 16, 7 * 16, 7, 3, true);
		add(c);
		board[7][3] = c;
		c = new Queen(4 * 16 , 7 * 16, 7, 4, true);
		add(c);
		board[7][4] = c;
		
		add(new UIElement(384, 209, Sprite.mobMudkip_right));

		
	}
	
	//reset the chess pieces
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
		
		spawnInitialMobs();
	}
	
	public boolean isYourTurn() {
		if(isWhiteTurn && getClientPlayer() instanceof Player) {
			return true;
		}
		else if(!isWhiteTurn && getClientPlayer() instanceof Player) {
			return true;
		}
		else return false;
	}
	
}
