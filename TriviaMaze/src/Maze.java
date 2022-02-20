import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Maze extends JPanel {
	
	// Serial Version.
	private static final long serialVersionUID = -8859286187722619956L;
	
	// Size of the maze.
	private final int MAZESIZE = 18;
	
	// The maze map to play on.
	// 0 = not-visited
	// 1 = wall
	// 2 = visited
	// 3 = door
	// 4 = player
	// 9 = goal
	private int[][] myMaze;
	
	// The player's X coordinate. 
	private int myX;
	
	// The player's Y coordinate.
	private int myY;
	
	// The player's keys.
	private int myKeys;
	
	// The doors opened counter.
	private int myDoors;
	
	// The player's steps counter.
	private int mySteps;
	
	// Pause the player's movement.
	private boolean myPause;
	
	private JPanel myPanel;
	
	/**
     * Generates the maze.
     * @param theSave The save file name.
     * @param theKeys The player's keys.
     */
	public Maze(final String theSave, final int theKeys) {
    	
		this.setBounds(0, 0, 800, 600);
		this.setFocusable(true);
		this.requestFocus();
		myPause = false;
		myKeys = theKeys;
		myDoors = 0;
		mySteps = 0;

		switch (myKeys) {
			case 1: { // Hard
				final int[][] maze = {
		            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		            {1,4,3,0,1,0,1,0,0,0,0,0,0,0,1,0,0,1,0,3,1,0,0,0,1,1,0,1},
		            {1,0,1,3,0,0,1,0,1,1,1,1,1,3,0,3,0,3,1,0,0,0,1,1,1,0,0,1},
		            {1,0,3,0,1,3,0,0,1,0,0,0,1,1,1,1,0,1,0,0,3,0,0,0,0,0,0,1},
		            {1,3,0,3,1,0,0,0,1,1,0,1,0,0,0,0,3,1,1,1,0,0,0,0,1,0,0,1},
		            {1,0,1,0,1,1,1,0,1,0,0,1,3,0,3,0,3,1,0,1,1,1,0,0,0,0,3,1},
		            {1,3,0,3,1,3,0,3,1,0,0,0,1,1,0,0,0,1,1,1,0,1,0,0,1,1,0,1},
		            {1,0,1,0,1,1,3,0,3,1,0,0,0,1,1,1,0,1,0,1,0,1,3,3,1,1,0,1},
		            {1,1,0,1,3,0,0,3,0,0,1,3,0,3,1,0,0,0,1,1,1,1,0,0,0,0,0,1},
		            {1,0,0,0,0,1,1,0,0,0,0,0,3,3,1,0,3,3,0,3,1,0,0,0,1,1,3,1},
		            {1,0,0,1,0,1,3,0,3,1,0,0,0,1,1,1,0,1,1,1,3,0,0,3,1,1,0,1},
		            {1,0,3,0,3,1,0,0,0,1,1,1,0,0,1,1,0,1,1,1,0,1,1,0,0,0,0,1},
		            {1,0,0,1,0,0,3,0,0,3,0,3,3,0,3,1,1,0,3,1,0,1,0,3,0,0,0,1},
		            {1,0,0,0,0,1,1,1,0,0,0,0,1,3,1,0,1,3,0,3,0,0,0,0,1,1,3,1},
		            {1,0,0,0,0,1,1,0,1,0,0,0,1,3,0,0,0,3,0,3,0,0,0,0,1,1,3,1},
		            {1,0,3,0,1,3,0,3,1,0,0,0,1,1,0,1,0,0,3,0,3,0,0,3,0,0,3,1},
		            {1,3,0,3,1,0,0,0,1,1,3,1,0,0,1,1,0,1,1,1,0,1,3,0,1,0,0,1},
		            {1,0,3,0,1,3,0,3,1,0,0,0,1,1,1,1,0,1,0,0,3,0,0,0,0,0,0,1},
		            {1,3,0,3,1,0,0,0,1,1,0,1,0,0,0,0,3,1,1,1,0,0,0,0,3,0,3,1},
		            {1,0,1,0,1,1,1,0,1,0,0,1,0,0,3,1,0,0,0,1,1,1,0,0,0,0,1,1},
		            {1,1,0,1,0,0,0,0,0,0,1,1,0,3,1,0,0,0,1,1,1,1,0,0,3,0,0,1},
		            {1,0,0,0,0,1,1,0,0,0,0,0,1,3,1,0,0,3,0,3,0,1,0,0,1,1,3,1},
		            {1,0,0,1,0,1,3,0,3,1,0,0,0,1,1,1,1,1,1,1,3,1,0,0,3,1,0,1},
		            {1,0,3,0,3,1,0,0,0,1,1,0,1,0,1,1,1,1,1,1,0,1,0,1,0,3,0,1},
		            {1,0,0,1,0,0,3,0,0,0,1,3,3,0,3,1,0,3,0,1,0,0,0,3,0,1,3,1},
		            {1,0,0,0,0,1,1,0,0,0,0,0,1,3,1,0,0,1,0,3,1,0,0,0,1,1,9,1},
		            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
				};
				
				myMaze = maze;
				break;
			}
			case 2: { // Medium
				final int[][] maze = {
			            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			            {1,4,3,0,1,0,1,0,0,0,0,0,0,0,1,0,0,1,0,3,1,0,0,0,1,1,0,1},
			            {1,0,1,3,0,0,1,0,1,1,1,1,1,3,0,3,0,3,1,0,0,0,1,1,1,0,0,1},
			            {1,0,3,0,1,3,0,0,1,0,0,0,1,1,1,1,0,1,0,0,3,0,0,0,0,0,0,1},
			            {1,3,0,3,1,0,0,0,1,1,0,1,0,0,0,0,3,1,1,1,0,0,0,0,1,0,0,1},
			            {1,0,1,0,1,1,1,0,1,0,0,1,3,0,3,0,3,1,0,1,1,1,0,0,0,0,3,1},
			            {1,3,0,3,1,3,0,3,1,0,0,0,1,1,0,0,0,1,1,1,0,1,0,0,1,1,0,1},
			            {1,0,1,0,1,1,3,0,3,1,0,0,0,1,1,1,0,1,0,1,0,1,3,3,1,1,0,1},
			            {1,1,0,1,3,0,0,3,0,0,1,3,0,3,1,0,0,0,1,1,1,1,0,0,0,0,0,1},
			            {1,0,0,0,0,1,1,0,0,0,0,0,3,3,1,0,3,3,0,3,1,0,0,0,1,1,3,1},
			            {1,0,0,1,0,1,3,0,3,1,0,0,0,1,1,1,0,1,1,1,3,0,0,3,1,1,0,1},
			            {1,0,3,0,3,1,0,0,0,1,1,1,0,0,1,1,0,1,1,1,0,1,1,0,0,0,0,1},
			            {1,0,0,1,0,0,3,0,0,3,0,3,3,0,3,1,1,0,3,1,0,1,0,3,0,0,0,1},
			            {1,0,0,0,0,1,1,1,0,0,0,0,1,3,1,0,1,3,0,3,0,0,0,0,1,1,3,1},
			            {1,0,0,0,0,1,1,0,1,0,0,0,1,3,0,0,0,3,0,3,0,0,0,0,1,1,3,1},
			            {1,0,3,0,1,3,0,3,1,0,0,0,1,1,0,1,0,0,3,0,3,0,0,3,0,0,3,1},
			            {1,3,0,3,1,0,0,0,1,1,3,1,0,0,1,1,0,1,1,1,0,1,3,0,1,0,0,1},
			            {1,0,3,0,1,3,0,3,1,0,0,0,1,1,1,1,0,1,0,0,3,0,0,0,0,0,0,1},
			            {1,3,0,3,1,0,0,0,1,1,0,1,0,0,0,0,3,1,1,1,0,0,0,0,3,0,3,1},
			            {1,0,1,0,1,1,1,0,1,0,0,1,0,0,3,1,0,0,0,1,1,1,0,0,0,0,1,1},
			            {1,1,0,1,0,0,0,0,0,0,1,1,0,3,1,0,0,0,1,1,1,1,0,0,3,0,0,1},
			            {1,0,0,0,0,1,1,0,0,0,0,0,1,3,1,0,0,3,0,3,0,1,0,0,1,1,3,1},
			            {1,0,0,1,0,1,3,0,3,1,0,0,0,1,1,1,1,1,1,1,3,1,0,0,3,1,0,1},
			            {1,0,3,0,3,1,0,0,0,1,1,0,1,0,1,1,1,1,1,1,0,1,0,1,0,3,0,1},
			            {1,0,0,1,0,0,3,0,0,0,1,3,3,0,3,1,0,3,0,1,0,0,0,3,0,1,3,1},
			            {1,0,0,0,0,1,1,0,0,0,0,0,1,3,1,0,0,1,0,3,1,0,0,0,1,1,9,1},
			            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
					};
				myMaze = maze;
				break;
			}
			case 3: { // Easy
				final int[][] maze = {
			            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			            {1,4,3,0,1,0,1,0,0,0,0,0,0,0,1,0,0,1,0,3,1,0,0,0,1,1,0,1},
			            {1,0,1,3,0,0,1,0,1,1,1,1,1,3,0,3,0,3,1,0,0,0,1,1,1,0,0,1},
			            {1,0,3,0,1,3,0,0,1,0,0,0,1,1,1,1,0,1,0,0,3,0,0,0,0,0,0,1},
			            {1,3,0,3,1,0,0,0,1,1,0,1,0,0,0,0,3,1,1,1,0,0,0,0,1,0,0,1},
			            {1,0,1,0,1,1,1,0,1,0,0,1,3,0,3,0,3,1,0,1,1,1,0,0,0,0,3,1},
			            {1,3,0,3,1,3,0,3,1,0,0,0,1,1,0,0,0,1,1,1,0,1,0,0,1,1,0,1},
			            {1,0,1,0,1,1,3,0,3,1,0,0,0,1,1,1,0,1,0,1,0,1,3,3,1,1,0,1},
			            {1,1,0,1,3,0,0,3,0,0,1,3,0,3,1,0,0,0,1,1,1,1,0,0,0,0,0,1},
			            {1,0,0,0,0,1,1,0,0,0,0,0,3,3,1,0,3,3,0,3,1,0,0,0,1,1,3,1},
			            {1,0,0,1,0,1,3,0,3,1,0,0,0,1,1,1,0,1,1,1,3,0,0,3,1,1,0,1},
			            {1,0,3,0,3,1,0,0,0,1,1,1,0,0,1,1,0,1,1,1,0,1,1,0,0,0,0,1},
			            {1,0,0,1,0,0,3,0,0,3,0,3,3,0,3,1,1,0,3,1,0,1,0,3,0,0,0,1},
			            {1,0,0,0,0,1,1,1,0,0,0,0,1,3,1,0,1,3,0,3,0,0,0,0,1,1,3,1},
			            {1,0,0,0,0,1,1,0,1,0,0,0,1,3,0,0,0,3,0,3,0,0,0,0,1,1,3,1},
			            {1,0,3,0,1,3,0,3,1,0,0,0,1,1,0,1,0,0,3,0,3,0,0,3,0,0,3,1},
			            {1,3,0,3,1,0,0,0,1,1,3,1,0,0,1,1,0,1,1,1,0,1,3,0,1,0,0,1},
			            {1,0,3,0,1,3,0,3,1,0,0,0,1,1,1,1,0,1,0,0,3,0,0,0,0,0,0,1},
			            {1,3,0,3,1,0,0,0,1,1,0,1,0,0,0,0,3,1,1,1,0,0,0,0,3,0,3,1},
			            {1,0,1,0,1,1,1,0,1,0,0,1,0,0,3,1,0,0,0,1,1,1,0,0,0,0,1,1},
			            {1,1,0,1,0,0,0,0,0,0,1,1,0,3,1,0,0,0,1,1,1,1,0,0,3,0,0,1},
			            {1,0,0,0,0,1,1,0,0,0,0,0,1,3,1,0,0,3,0,3,0,1,0,0,1,1,3,1},
			            {1,0,0,1,0,1,3,0,3,1,0,0,0,1,1,1,1,1,1,1,3,1,0,0,3,1,0,1},
			            {1,0,3,0,3,1,0,0,0,1,1,0,1,0,1,1,1,1,1,1,0,1,0,1,0,3,0,1},
			            {1,0,0,1,0,0,3,0,0,0,1,3,3,0,3,1,0,3,0,1,0,0,0,3,0,1,3,1},
			            {1,0,0,0,0,1,1,0,0,0,0,0,1,3,1,0,0,1,0,3,1,0,0,0,1,1,9,1},
			            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
					};
				myMaze = maze;
				break;
			}
		}
	}
	
	/**
     * Checks what the player presses on their keyboard.
     * @param theEvent The event object to see what keys they clicked.
     */
	@Override
	protected void processKeyEvent(final KeyEvent theEvent) {
		
		if (theEvent.getID() != KeyEvent.KEY_PRESSED || myPause) {
			return;
		}

		switch (theEvent.getKeyCode()) {
			case KeyEvent.VK_UP, KeyEvent.VK_W: {
				if (myMaze[myX - 1][myY] != 1) {
					if (!move(myMaze[myX - 1][myY])) {
						return;
					} else if (myMaze[myX - 1][myY] != 2) {
						mySteps++;
					}
					myMaze[myX][myY] = 2;
					myMaze[myX - 1][myY] = 4;
				}
				break;
			}
			case KeyEvent.VK_DOWN, KeyEvent.VK_S: {
				if (myMaze[myX + 1][myY] != 1) {
					if (!move(myMaze[myX + 1][myY])) {
						return;
					} else if (myMaze[myX + 1][myY] != 2) {
						mySteps++;
					}
					myMaze[myX][myY] = 2;
					myMaze[myX + 1][myY] = 4;
				}
				break;
			}
			case KeyEvent.VK_LEFT, KeyEvent.VK_A: {
				if (myMaze[myX][myY - 1] != 1) {
					if (!move(myMaze[myX][myY - 1])) {
						return;
					} else if (myMaze[myX][myY - 1] != 2) {
						mySteps++;
					}
					myMaze[myX][myY] = 2;
					myMaze[myX][myY - 1] = 4;
				}
				break;
			}
			case KeyEvent.VK_RIGHT, KeyEvent.VK_D: {
				if (myMaze[myX][myY + 1] != 1) {
					if (!move(myMaze[myX][myY + 1])) {
						return;
					} else if (myMaze[myX][myY + 1] != 2) {
						mySteps++;
					}
					myMaze[myX][myY] = 2;
					myMaze[myX][myY + 1] = 4;
				}
				break;
			}
		}
		attachments();
		repaint();
	}
	
	/**
     * Check if the player could move.
     * @param thePath The player's next move.
     */
	private boolean move(final int thePath) {
		
		if (thePath == 9) { // Reached the end.
			TriviaMaze.playSound("Win.wav");
			JOptionPane.showMessageDialog(null, "WINNER WINNER CHICKEN DINNER!", "You Won", 1);
			myPause = true;
			TriviaMaze.myFrame.dispose();
			TriviaMaze.createGUI(); // Restart
			return false;
		}
		else if (thePath == 3) { // At a door.
			final Room door = new Room(myKeys);
			myKeys = door.myKeys;
			
			if (door.myDoorOption) {
				if (!door.myResult) {
					if (myKeys == 0) { // Game over.
						myPause = true;
						TriviaMaze.playSound("Lose.wav");
						JOptionPane.showMessageDialog(null, "GAME OVER\nYou're out of keys.", "Game Over", 0);
						TriviaMaze.myFrame.dispose();
						TriviaMaze.createGUI(); // Restart
					} else {
						TriviaMaze.playSound("Locked.wav");
					}
					return false;
				} else {
					myDoors++;
				}
			} else {
				return false;
			}
		}
		TriviaMaze.playSound("Footstep.wav");
		return true;
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
					default: color = Color.DARK_GRAY; // Path
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
		attachments();
	}
	
	/**
     * Place the instructions and side attachments on the panel.
     */
	private void attachments() {
		
		if (myPanel != null) {
			myPanel.getParent().remove(myPanel);
		}
		myPanel = new JPanel();
		myPanel.setBounds(0, 0, 800, 600);
		myPanel.setBackground(Color.BLACK);
		
		final JLabel key = new JLabel(new ImageIcon("images/key.png"));
		key.setBounds(680, 10, 100, 99);
		
		final JLabel keycounter = new JLabel(String.format("Keys: %d", myKeys));
		keycounter.setBounds(550, 16, 100, 100);
		keycounter.setForeground(Color.RED);
		keycounter.setFont(new Font("Serif", Font.PLAIN, 24));
		
		final JLabel door = new JLabel(new ImageIcon("images/door.png"));
		door.setBounds(680, 150, 100, 99);
		
		final JLabel doorcounter = new JLabel(String.format("Doors: %d", myDoors));
		doorcounter.setBounds(550, 150, 100, 100);
		doorcounter.setForeground(Color.RED);
		doorcounter.setFont(new Font("Serif", Font.PLAIN, 24));
		
		final JLabel steps = new JLabel(new ImageIcon("images/steps.png"));
		steps.setBounds(680, 290, 100, 99);
		
		final JLabel stepscounter = new JLabel(String.format("Steps: %d", mySteps));
		stepscounter.setBounds(550, 290, 100, 100);
		stepscounter.setForeground(Color.RED);
		stepscounter.setFont(new Font("Serif", Font.PLAIN, 24));
		
		final JLabel image = new JLabel(new ImageIcon("images/instructions.png"));
		image.setBounds(512, 495, 297, 160);
		
		final JTextArea instruction = new JTextArea("   1. Use arrow keys or WASD on your keyboard to move.\n"
				+ "   2. Once you step on a grey square it will turn white which marks it as visited.\n"
		        + "   3. Yellow squares are doors, you must answer a question in order to pass through the door.\n"
		        + "   4. Answering incorrectly or not answering at all, will cost you a key.\n"
		        + "   5. If you run out of keys, you will lose the game.\n"
				+ "   6. Reach the red square in order to win. Good luck :)");
		instruction.setEditable(false);
		instruction.setBounds(0, 500, 510, 300);
		instruction.setBackground(Color.BLACK);
		instruction.setForeground(Color.RED);
		
		final JButton exit = new JButton("Save & Exit");
		exit.setBackground(Color.DARK_GRAY);
		exit.setForeground(Color.RED);
		exit.setFocusPainted(false);
		exit.setFont(new Font("Serif", Font.PLAIN, 24));
		exit.setBounds(550, 440, 220, 40);
		exit.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		        if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?\nYour progress is saved.", "Exit Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					TriviaMaze.myFrame.dispose();
					TriviaMaze.createGUI(); // Back to main menu.
		        } else {
		        	repaint();
		        }
		    }
		});
		
		
		myPanel.add(instruction);
		myPanel.add(image);
		myPanel.add(door);
		myPanel.add(key);
		myPanel.add(steps);
		myPanel.add(keycounter);
		myPanel.add(doorcounter);
		myPanel.add(stepscounter);
		myPanel.add(exit);
		this.add(myPanel);
		this.requestFocus();
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