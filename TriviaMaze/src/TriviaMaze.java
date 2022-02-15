import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import java.awt.Graphics;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
* @author Hasan, Mohammed, Manuel
* @version 1.0
*/
public abstract class TriviaMaze {
	
	// Database's connection URL.
	private static final String DATABASE = "jdbc:sqlite:/C:\\sqlite\\saves.db";
	
	// The main frame for the GUI.
	private static JFrame myFrame;
	
    /**
     * @param theArgs The command line arguments
     * @throws Exception 
     */
	public static void main(final String[] theArgs) {
		
		createDatabase();
		
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	createGUI();
            }
        });
	}
	
    /**
     * Create the main frame for GUI.
     */	
	private static void createGUI() {
		myFrame = new JFrame();
		myFrame.setTitle("Trivia Maze");
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myFrame.setSize(800, 600);
		myFrame.setLocationRelativeTo(null);
		myFrame.setResizable(false);
		myFrame.setLayout(null);
		
		JPanel welcomeText = new JPanel();
		welcomeText.setBounds(0, 0, 800, 200);
		
		JLabel image = new JLabel();
		image.setIcon(new ImageIcon("menu.jpg"));
		image.setText("Welcome to Trivia Maze!");
		image.setForeground(Color.RED);
		image.setFont(new Font("Serif", Font.PLAIN, 36));
		image.setHorizontalTextPosition(JLabel.CENTER);
		image.setVerticalTextPosition(JLabel.CENTER);
		welcomeText.add(image);
		myFrame.add(welcomeText);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));
		panel.setBounds(0, 200, 800, 370);
		panel.setBackground(Color.BLACK); 
		
		JLabel newGame = new JLabel();
		newGame.setIcon(new ImageIcon("menugame.gif"));
		newGame.setText("New Game");
		newGame.setForeground(Color.RED);
		newGame.setFont(new Font("Serif", Font.PLAIN, 36));
		newGame.setHorizontalTextPosition(JLabel.CENTER);
		newGame.setVerticalTextPosition(JLabel.CENTER);
		newGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//openNewGameMenu();
			}
		});
		
		JLabel loadGame = new JLabel();
		loadGame.setIcon(new ImageIcon("menuload.gif"));
		loadGame.setText("Load Game");
		loadGame.setForeground(Color.RED);
		loadGame.setFont(new Font("Serif", Font.PLAIN, 36));
		loadGame.setHorizontalTextPosition(JLabel.CENTER);
		loadGame.setVerticalTextPosition(JLabel.CENTER);
		loadGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				openLoadGameMenu();
			}
		});
		
		JLabel exitGame = new JLabel();
		exitGame.setIcon(new ImageIcon("menuexit.gif"));
		exitGame.setText("Exit Game");
		exitGame.setForeground(Color.RED);
		exitGame.setFont(new Font("Serif", Font.PLAIN, 36));
		exitGame.setHorizontalTextPosition(JLabel.CENTER);
		exitGame.setVerticalTextPosition(JLabel.CENTER);
		exitGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit? :(", "Exit Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					System.out.println("Thanks for playing!");
					System.exit(0);
				}
			}
		});
		
		panel.add(newGame);
		panel.add(loadGame);
		panel.add(exitGame);
		
		myFrame.add(panel);
		//myFrame.pack();
		myFrame.setVisible(true);
	}
	
	/**
     * Opens the load game menu.
     */
	private static void openLoadGameMenu() {
		myFrame.dispose();
		myFrame = new JFrame();
		myFrame.setTitle("Trivia Maze - Load Game");
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myFrame.setSize(800, 600);
		myFrame.setLocationRelativeTo(null);
		myFrame.setResizable(false);
		
		JPanel panel = new JPanel();
		JButton[] button = new JButton[10];
		
    	final HashMap<Integer, String> list = select("SELECT id, name FROM saves");
    	final Iterator<Integer> itr = list.keySet().iterator();
    	int rows = 0;
    	
    	while (itr.hasNext()) {
    		final int id = itr.next();
    		final String name = list.get(id);
    		
    		button[rows] = new JButton(String.format("Save %d - %s", id, name));
    		panel.add(button[rows]);
    		rows++;
    	}
    	
    	if (rows == 0) {
            JOptionPane.showMessageDialog(null, "No saved games found!", "", 2);
        	myFrame.dispose();
        	createGUI();
    		return;
    	}
    	
    	button[rows] = new JButton("Back to Main Menu");
    	button[rows].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent ae) {
            	myFrame.dispose();
            	createGUI();
            }
        });
    	
    	panel.add(button[rows]);
    	panel.setLayout(new GridLayout(rows-1, 1));
    	myFrame.add(panel);
		myFrame.setVisible(true);
	}

	/**
     * Create the database table if it doesn't exist.
     */
    private static void createDatabase() {

    	final String sql = "CREATE TABLE IF NOT EXISTS saves (\n"
				 + "id INTEGER PRIMARY KEY ASC,\n"
				 + "name text NOT NULL UNIQUE);";
    	
        try (final Connection conn = DriverManager.getConnection(DATABASE); final Statement stmt = conn.createStatement()) {
        	stmt.execute(sql);
        } catch (final SQLException e) {
        	e.printStackTrace();
        }
    }
	
    /**
     * Connect to the database and retrieve the connection object.
     * @return Connection The database connection object.
     */
    protected Connection connect() {
    	
        Connection connection = null;
        
        try {
        	connection = DriverManager.getConnection(DATABASE);
        } catch (final SQLException e) {
        	e.printStackTrace();
        }
        return connection;
    }
    
    /**
     * Selects & retrieves rows from the saves table.
     * @param theQuery The SQL query to retrieve information from the database.
     * @return HashMap Contains the list of results of the query.
     */
    protected static HashMap<Integer, String> select(final String theQuery) {
    	
    	final HashMap<Integer, String> list = new HashMap<Integer, String>(); 
    	
        try (final Connection conn = DriverManager.getConnection(DATABASE); final Statement stmt = conn.createStatement(); final ResultSet rs = stmt.executeQuery(theQuery)){
        	
        	try {
    			while (rs.next()) {
    				list.put(rs.getInt("id"), rs.getString("name"));
    			}
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
        } catch (final SQLException e) {
        	e.printStackTrace();
        }
		return list;
    }
    
    /**
     * Insert a new row into the saves database.
     * @param theName The name of the save to insert.
     */
    protected void insert(final String theName) {
    	
    	final String sql = "INSERT INTO saves(name) VALUES(?)";
    	
        try (final Connection conn = this.connect(); final PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	pstmt.setString(1, theName);
            pstmt.executeUpdate();
        } catch (final SQLException e) {
        	e.printStackTrace();
        }
    }
    
    /**
     * Update the data of an existing save from the database.
     * @param theName The name of the save to update.
     */
    protected void update(final String theName) {
    	
        final String sql = "UPDATE saves SET name = ?";

        try (final Connection conn = this.connect(); final PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, theName);
            pstmt.executeUpdate();
        } catch (final SQLException e) {
        	e.printStackTrace();
        }
    }
    
    /**
     * Delete a save from the database specified by the id.
     * @param theId The id of the save to delete.
     */
    protected void delete(final int theId) {
    	
    	final String sql = "DELETE FROM saves WHERE id = ?";

        try (final Connection conn = this.connect(); final PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, theId);
            pstmt.executeUpdate();
        } catch (final SQLException e) {
        	e.printStackTrace();
        }
    }
}