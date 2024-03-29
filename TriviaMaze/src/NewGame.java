import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NewGame extends TriviaMaze {
	
	/**
     * Opens the new game menu.
     * @param theSave The save file name.
     */
	public NewGame(final String theSave) {
		
		myFrame = new JFrame("Trivia Maze - Difficulty");
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setSize(800, 600);
		myFrame.setLocationRelativeTo(null);
		myFrame.setResizable(false);
		myFrame.setUndecorated(true);
		
		final JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 1));
		panel.setBounds(0, 0, 800, 600);
		
		JLabel title = new JLabel();
		title.setIcon(new ImageIcon("images/difficulty.jpg"));
		panel.add(title);
		
		JLabel[] button = new JLabel[3];
		button[0] = new JLabel("Easy");
		button[0].setIcon(new ImageIcon("images/easy.jpg"));
		button[1] = new JLabel("Medium");
		button[1].setIcon(new ImageIcon("images/medium.jpg"));
		button[2] = new JLabel("Hard");
		button[2].setIcon(new ImageIcon("images/hard.jpg"));
		
		for (int i = 0; i < 3; i++) {
			final int inneri = i;

        	button[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(final MouseEvent e) {
                	playSound("Select.wav");
                	String difficulty = button[inneri].getText();
                	update(theSave, difficulty, 3 - inneri, 0, 0, "");
                	launchGame(theSave, difficulty, 3 - inneri, 0, 0, "");
                }
            });
        	panel.add(button[i]);
		}
		
		myFrame.add(panel);
		myFrame.setVisible(true);
	}
}
