package marcuseidahl.level;

import marcuseidahl.Game;
import marcuseidahl.entity.UI.UIElement;
import marcuseidahl.entity.chess.Bishop;
import marcuseidahl.entity.chess.ChessPiece;
import marcuseidahl.entity.chess.King;
import marcuseidahl.entity.chess.Knight;
import marcuseidahl.entity.chess.Pawn;
import marcuseidahl.entity.chess.Queen;
import marcuseidahl.entity.chess.Rook;
import marcuseidahl.entity.mob.player.ChessPlayer;
import marcuseidahl.graphics.Sprite;
import marcuseidahl.input.Mouse;

public class ChessLevel extends Level{
	
	private static final int gridXstart = 360;
	private static final int gridXend = gridXstart + 16 * 8 * Game.scale;
	
	private static final int gridYstart = 96;
	private static final int gridYend = gridYstart + 16 * 8 * Game.scale;
	
	boolean haveSelectedPiece = false;
	ChessPiece selected = null;
	int curRow = -100, curCol = -100;
	int selectedRow = -200, selectedCol = -200;

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
				
				int mouseX = Mouse.getX();
				int mouseY = Mouse.getY();
				
				if(mouseX > gridXstart && mouseX < gridXend && mouseY > gridYstart && mouseY < gridYend) {
					
					curRow = (mouseY - gridYstart) / (16 * Game.scale);
					curCol = (mouseX - gridXstart) / (16 * Game.scale);
										
					if(!haveSelectedPiece) {	
						if(board[curRow][curCol] != null) {
							selectedRow = curRow;						
							selectedCol = curCol;
							selected = board[curRow][curCol];
							
							System.out.println("Selected: " + selected.getName());
							System.out.println("selectedRow: " + selectedRow + "\nselectedCol: " + selectedCol);
							
							haveSelectedPiece = true;
						}
					}
					else {
						
						int dRow = curRow - selectedRow;
						int dCol = curCol - selectedCol;
						
						if(!(dRow == 0 && dCol == 0)) {
						
							System.out.println("dRow: " + dRow + "\ndCol: " + dCol);
							
							if(selected.move(dRow, dCol, board)) {
								
								System.out.println("Moved " + selected.getName());
								
								if(isWhiteTurn) isWhiteTurn = false;
								else isWhiteTurn = true;
							}
							
							haveSelectedPiece = false;
							selected = null;
							
							System.out.println("Reset Selected");
							
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
			c = new Pawn(1, i, false);
			add(c);
			board[1][i] = c;
			
			c = new Pawn(6, i, true);
			add(c);
			board[6][i] = c;
		}
		
		//Add Rooks
		c = new Rook(0, 0, false);
		add(c);
		board[0][0] = c;
		c = new Rook(0, 7, false);
		add(c);
		board[0][7] = c;
		c = new Rook(7, 0, true);
		add(c);
		board[7][0] = c;
		c = new Rook(7, 7, true);
		add(c);
		board[7][7] = c;
		
		//Add Knights
		c = new Knight(0, 1, false);
		add(c);
		board[0][1] = c;
		c = new Knight(0, 6, false);
		add(c);
		board[0][6] = c;
		c = new Knight(7, 1, true);
		add(c);
		board[7][1] = c;
		c = new Knight(7, 6, true);
		add(c);
		board[7][6] = c;
		
		//Add Bishops
		c = new Bishop(0, 2, false);
		add(c);
		board[0][2] = c;
		c = new Bishop(0, 5, false);
		add(c);
		board[0][5] = c;
		c = new Bishop(7, 2, true);
		add(c);
		board[7][2] = c;
		c = new Bishop(7, 5, true);
		add(c);
		board[7][5] = c;
		
		//Add Kings and Queens
		c = new King(0, 3, false);
		add(c);
		board[0][3] = c;
		c = new Queen(0, 4, false);
		add(c);
		board[0][4] = c;
		c = new King(7, 3, true);
		add(c);
		board[7][3] = c;
		c = new Queen(7, 4, true);
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
		
		selected = null;
		haveSelectedPiece = false;
		isWhiteTurn = true;

		spawnInitialMobs();
	}
	
	public boolean isYourTurn() {
						
		if(isWhiteTurn && ((ChessPlayer)getClientPlayer()).isWhite()) {
			return true;
		}
		else if(!isWhiteTurn && !((ChessPlayer)getClientPlayer()).isWhite()) {
			return true;
		}
		else return false;
	}
	
}
