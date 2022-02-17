import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

public class Maze extends JPanel {
	
	// Serial Version.
	private static final long serialVersionUID = -8859286187722619956L;
	
	// The game's max window width.
	private final int MAXWIDTH = 800;
	
	// The game's max window height.
	private final int MAXHEIGHT = 600;
	
	// Size of the maze.
	private final int MAZESIZE = 40;
	
	// Random test maze.
	// 0 = not-visited
	// 1 = wall
	// 2 = visited
	// 3 = door
	// 4 = player
	// 9 = goal
	private int[][] myMaze = {
			{1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,4,3,0,1,0,1,0,0,0,0,0,1},
			{1,5,1,0,0,1,1,0,1,1,1,0,1},
			{1,0,0,0,1,1,1,0,0,0,0,0,1},
			{1,0,1,0,0,0,0,0,1,1,1,0,1},
			{1,0,1,0,1,1,1,0,1,0,0,0,1},
			{1,0,3,0,1,0,0,0,1,1,1,0,1},
			{1,0,1,0,1,1,1,0,1,0,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,1,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,9,1}
	};
	
	// The player's X coordinate. 
	private int myX;
	
	// The player's Y coordinate.
	private int myY;
	
	// Pause the player's movement.
	private boolean myPause;
	
	/**
     * Generates the maze.
     * @param theSave The save file name.
     * @param theDifficulty The game's difficulty setting.
     */
	public Maze(final String theSave, final String theDifficulty) {
    	
		this.setPreferredSize(new Dimension(MAXWIDTH, MAXHEIGHT));
		this.setFocusable(true);
		this.requestFocus();
	}
	
	/**
     * Checks what the player presses on their keyboard.
     * @param theEvent The event object to see what keys they clicked.
     */
	@Override
	protected void processKeyEvent(KeyEvent theEvent) {
		
		if (theEvent.getID() != KeyEvent.KEY_PRESSED || myPause) {
			return;
		}

		switch (theEvent.getKeyCode()) {
			case KeyEvent.VK_UP, KeyEvent.VK_W: {
				if (myMaze[myX - 1][myY] != 1) {
					if (myMaze[myX - 1][myY] == 9) {
						System.out.println("YOU WIN!!!");
						myPause = true;
						return;
					}
					else if (myMaze[myX - 1][myY] == 3) {
						Room door = new Room();
						if (door.myDoorOption) {
							if (!door.myResult) {
								return;
							}
						} else {
							return;
						}
					}
					myMaze[myX][myY] = 2;
					myMaze[myX - 1][myY] = 4;
				}
				break;
			}
			case KeyEvent.VK_DOWN, KeyEvent.VK_S: {
				if (myMaze[myX + 1][myY] != 1) {
					if (myMaze[myX + 1][myY] == 9) {
						System.out.println("YOU WIN!!!");
						myPause = true;
						return;
					}
					else if (myMaze[myX + 1][myY] == 3) {
						Room door = new Room();
						if (door.myDoorOption) {
							if (!door.myResult) {
								return;
							}
						} else {
							return;
						}
					}
					myMaze[myX][myY] = 2;
					myMaze[myX + 1][myY] = 4;
				}
				break;
			}
			case KeyEvent.VK_LEFT, KeyEvent.VK_A: {
				if (myMaze[myX][myY - 1] != 1) {
					if (myMaze[myX][myY - 1] == 9) {
						System.out.println("YOU WIN!!!");
						myPause = true;
						return;
					}
					else if (myMaze[myX][myY - 1] == 3) {
						Room door = new Room();
						if (door.myDoorOption) {
							if (!door.myResult) {
								return;
							}
						} else {
							return;
						}
					}
					myMaze[myX][myY] = 2;
					myMaze[myX][myY - 1] = 4;
				}
				break;
			}
			case KeyEvent.VK_RIGHT, KeyEvent.VK_D: {
				if (myMaze[myX][myY + 1] != 1) {
					if (myMaze[myX][myY + 1] == 9) {
						System.out.println("YOU WIN!!!");
						myPause = true;
						return;
					}
					else if (myMaze[myX][myY + 1] == 3) {
						
						Room door = new Room();
						if (door.myDoorOption) {
							if (!door.myResult) {
								return;
							}
						} else {
							return;
						}
					}
					myMaze[myX][myY] = 2;
					myMaze[myX][myY + 1] = 4;
				}
				break;
			}
		}
		repaint();
	}
	
	/**
     * Draw the maze.
     * @param theGraphics The graphics object used to draw.
     */
	public void paint(final Graphics theGraphics) {
		
		super.paint(theGraphics);
		
		for (int row = 0; row < myMaze.length; row++) {
			for (int column = 0; column < myMaze[0].length; column++) {
				Color color = null;
				
				switch (myMaze[row][column]) {
					case 1: color = Color.BLACK; break; // Wall
					case 2: color = Color.WHITE; break; // Visited
					case 3: color = Color.YELLOW; break; // Door
					case 9: color = Color.RED; break; // The End
					default: color = Color.GRAY; // Path
				}
				
				theGraphics.setColor(color);
				theGraphics.fillRect(MAZESIZE * column, MAZESIZE * row, MAZESIZE, MAZESIZE);
				theGraphics.setColor(Color.BLACK);
				theGraphics.drawRect(MAZESIZE * column, MAZESIZE * row, MAZESIZE, MAZESIZE);
			} 
		}
		
		final Point index = findPlayer(myMaze);
		myX = index.x;
		myY = index.y;
		theGraphics.setColor(Color.GREEN);
		theGraphics.fillOval(myY * MAZESIZE, myX * MAZESIZE, MAZESIZE, MAZESIZE);
	}
	
	/**
     * Find the player's location.
     * @param theMaze The maze to search into.
     */
	private Point findPlayer(final int[][] theMaze) {

		 for (int row = 0; row < theMaze.length; row++ ) {
			 final int[] location = theMaze[row];
			 
			 for (int column = 0; column < location.length; column++) {
				 if (location[column] == 4) {
					 return new Point(row, column);
				 }
			 }
		 }
		 return null; 
	 }
}