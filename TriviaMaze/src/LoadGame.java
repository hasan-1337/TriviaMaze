import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class LoadGame extends TriviaMaze {
	
	/**
     * Opens the load game menu.
     */
	public LoadGame() {
		
		if (myFrame != null) {
			myFrame.dispose();
		}
		
		myFrame = new JFrame("Trivia Maze - Load Game");
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setSize(800, 600);
		myFrame.setLocationRelativeTo(null);
		myFrame.setResizable(false);
		myFrame.setUndecorated(true);
		myFrame.setLayout(null);
		
		final JPanel title = new JPanel();
		title.setBounds(0, 0, 800, 200);
		title.setBackground(Color.BLACK);
		
		final JLabel loadGame = new JLabel(new ImageIcon("images/load.jpg"));
		title.add(loadGame);
		myFrame.add(title);
		
		final JPanel panel = new JPanel();
		panel.setBounds(0, 200, 800, 400);
		panel.setBackground(Color.BLACK);
		
		JButton[] button = new JButton[50];
		
    	final HashMap<Integer, String> list = select("SELECT * FROM saves");
    	final Iterator<Integer> itr = list.keySet().iterator();
    	int rows = 0;
    	
    	while (itr.hasNext()) {
    		final int id = itr.next();
    		final String data = list.get(id);
    		final String[] info = data.split("\\|");

    		button[rows] = new JButton(String.format("[Save %d] %s - %s", id, info[0].toString(), info[1].toString()));
    		button[rows].setBackground(Color.DARK_GRAY);
    		button[rows].setForeground(Color.RED);
    		button[rows].setFocusPainted(false);
        	button[rows].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent ae) {
                	playSound("Select.wav");
                	load(data);
                }
            });
    		panel.add(button[rows]);
    		rows++;
    	}
    	
    	if (rows == 0) {
            JOptionPane.showMessageDialog(null, "No saved games found!", "", 2);
        	createGUI();
    		return;
    	}
    	
    	button[rows] = new JButton("Back to Main Menu");
 		button[rows].setBackground(Color.DARK_GRAY);
		button[rows].setForeground(Color.RED);
		button[rows].setFocusPainted(false);
    	button[rows].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent ae) {
            	playSound("Select.wav");
            	createGUI();
            }
        });
    	
    	panel.add(button[rows]);
    	panel.setLayout(new GridLayout(rows - 1, 1));
    	myFrame.add(panel);
		myFrame.setVisible(true);
	}
	
    /**
     * Loads the save.
     * @param theData The information that holds all the save information.
     */
	private void load(final String theData) {
		
		final String[] info = theData.split("\\|");
		
    	myFrame.dispose();
		myFrame = new JFrame("Trivia Maze - Load Game");
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setSize(800, 600);
		myFrame.setLocationRelativeTo(null);
		myFrame.setResizable(false);
		myFrame.setUndecorated(true);
		myFrame.setLayout(null);
    	
		final JPanel title = new JPanel();
		title.setBounds(0, 0, 800, 200);
		title.setBackground(Color.BLACK);
		
		final JLabel loadGame = new JLabel(new ImageIcon("images/load.jpg"));
		title.add(loadGame);
		myFrame.add(title);
		
		final JPanel data = new JPanel();
		data.setBounds(0, 200, 800, 300);
		data.setBackground(Color.BLACK);
		
		final JLabel name = new JLabel(String.format("<html>Save Name: %s<br/>Difficulty: %s<br/>Keys: %d<br/>Doors: %d<br/>Steps: %d</html>", info[0], info[1],
				Integer.parseInt(info[2]), Integer.parseInt(info[3]),  Integer.parseInt(info[4])));
		name.setForeground(Color.RED);
		name.setFont(new Font("Serif", Font.PLAIN, 42));
		data.add(name);
		myFrame.add(data);
		
		final JPanel choice = new JPanel();
		choice.setBounds(0, 500, 800, 100);
		choice.setLayout(new GridLayout());
		
		final JButton load = new JButton("Load Save");
		load.setBackground(Color.DARK_GRAY);
		load.setForeground(Color.RED);
		load.setFocusPainted(false);
		load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent ae) {
            	playSound("Select.wav");
            	launchGame(info[0], info[1], Integer.parseInt(info[2]), Integer.parseInt(info[3]), Integer.parseInt(info[4]), info[5]); 
            }
        });
		
		final JButton delete = new JButton("Delete Save");
		delete.setBackground(Color.DARK_GRAY);
		delete.setForeground(Color.RED);
		delete.setFocusPainted(false);
		delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent ae) {
            	playSound("Select.wav");
            	delete(info[0]);
            	createGUI();
            }
        });
		
		final JButton exit = new JButton("Cancel");
		exit.setBackground(Color.DARK_GRAY);
		exit.setForeground(Color.RED);
		exit.setFocusPainted(false);
		exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent ae) {
            	playSound("Select.wav");
            	new LoadGame();
            }
        });
		
		choice.add(load);
		choice.add(delete);
		choice.add(exit);
		myFrame.add(choice);
		myFrame.setVisible(true);
	}
}
