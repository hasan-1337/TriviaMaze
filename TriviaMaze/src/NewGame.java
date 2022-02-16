import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class NewGame extends TriviaMaze {
	
	/**
     * Opens the new game menu.
     * @param theSave The save file name.
     */
	public NewGame(final String theSave) {
		
		myFrame.dispose();
		myFrame = new JFrame();
		myFrame.setTitle("Trivia Maze - Difficulty");
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myFrame.setSize(800, 600);
		myFrame.setLocationRelativeTo(null);
		myFrame.setResizable(false);
		
		final JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));
		
		JButton[] button = new JButton[3];
		
		button[0] = new JButton("Easy");
		button[1] = new JButton("Medium");
		button[2] = new JButton("Hard");
		
		for (int i = 0; i < 3; i++) {
			final int inneri = i;
			
        	button[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent ae) {
                	myFrame.dispose();
                	update(theSave, button[inneri].getText());
                }
            });
        	panel.add(button[i]);
		}
		
		myFrame.add(panel);
		myFrame.setVisible(true);
	}
}
