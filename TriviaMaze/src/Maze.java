import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Maze extends TriviaMaze {
	
	// The speed of the player.
	private final int SPEED = 5;
	
	// The size of the player.
	private final int SIZE = 75;
	
	// The game's max window width.
	private final int MAXWIDTH = 800;
	
	// The game's max window height.
	private final int MAXHEIGHT = 600;
	
	// The player to control.
	private JButton myPlayer;
	
	/**
     * Loads the game.
     * @param theSave The save file name.
     * @param theDifficulty The game's difficulty setting.
     */
	public Maze(final String theSave, final String theDifficulty) {
    	
		myFrame.dispose();
		myFrame = new JFrame();
		myFrame.setTitle("Trivia Maze");
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setSize(MAXWIDTH, MAXHEIGHT);
		myFrame.setLocationRelativeTo(null);
		myFrame.setResizable(false);
		myFrame.setLayout(null);
		
		final JPanel panel = new JPanel();
		panel.setBounds(0, 0, 800, 600);
		
		myPlayer = new JButton("Test");
		myPlayer.addKeyListener(new KeyListener() {
            public void keyPressed(final KeyEvent theEvent) {
				
            	int x = myPlayer.getX();
            	int y = myPlayer.getY();
            	
				switch (theEvent.getKeyCode()) {
					case KeyEvent.VK_UP, KeyEvent.VK_W: {
						if (y - SPEED > 0) {
							myPlayer.setLocation(x, y - SPEED);
						}
						break;
					}
					case KeyEvent.VK_DOWN, KeyEvent.VK_S: {
						if (y + SPEED < MAXHEIGHT - SIZE) {
							myPlayer.setLocation(x, y + SPEED);
						}
						break;
					}
					case KeyEvent.VK_LEFT, KeyEvent.VK_A: {
						if (x - SPEED > 0) {
							myPlayer.setLocation(x - SPEED, y);
						}
						break;
					}
					case KeyEvent.VK_RIGHT, KeyEvent.VK_D: {
						if (x + SPEED < MAXWIDTH - SIZE) {
							myPlayer.setLocation(x + SPEED, y);
						}
						break;
					}
				}
            }

			public void keyReleased(final KeyEvent theEvent) {}
			public void keyTyped(final KeyEvent theEvent) {}
		});
		
		panel.add(myPlayer);
		myFrame.add(panel);
		myFrame.setVisible(true);
	}
}
