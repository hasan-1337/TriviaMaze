import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
//import java.awt.Graphics;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
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
	protected static JFrame myFrame;
	
	// The player's keys.
	protected int myKeys;
	
    /**
     * Main method.
     * @param theArgs The command line arguments
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
	protected static void createGUI() {
		
		myFrame = new JFrame();
		myFrame.setTitle("Trivia Maze");
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myFrame.setSize(800, 600);
		myFrame.setLocationRelativeTo(null);
		myFrame.setResizable(false);
		myFrame.setLayout(null);
		
		final JPanel welcomeText = new JPanel();
		welcomeText.setBounds(0, 0, 800, 200);
		
		final JLabel image = new JLabel();
		image.setIcon(new ImageIcon("images/menu.jpg"));
		image.setText("Welcome to Trivia Maze!");
		image.setForeground(Color.RED);
		image.setFont(new Font("Serif", Font.PLAIN, 36));
		image.setHorizontalTextPosition(JLabel.CENTER);
		image.setVerticalTextPosition(JLabel.CENTER);
		welcomeText.add(image);
		myFrame.add(welcomeText);
		
		final JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));
		panel.setBounds(0, 200, 800, 370);
		panel.setBackground(Color.BLACK); 
		
		final JLabel newGame = new JLabel();
		newGame.setIcon(new ImageIcon("images/menugame.gif"));
		newGame.setText("New Game");
		newGame.setForeground(Color.RED);
		newGame.setFont(new Font("Serif", Font.PLAIN, 36));
		newGame.setHorizontalTextPosition(JLabel.CENTER);
		newGame.setVerticalTextPosition(JLabel.CENTER);
		newGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				String input;
				final JFrame frame = new JFrame();
				
				while (true) {
	                input = JOptionPane.showInputDialog(frame, "Enter a save name:" , "New Game", 1);
	                
	                if (input == null) {
	                    break;
	                }
	                
	                Pattern pattern = Pattern.compile("^[a-zA-Z]+$");
	                Matcher matcher = pattern.matcher(input);
	               
	                if (input.length() < 3) {
	                	playSound("Error.wav");
	                	JOptionPane.showMessageDialog(frame, "Must have at least 3 characters.", "Warning", 2);
	                } else if (!matcher.find()) {
	                	playSound("Error.wav");
	                	JOptionPane.showMessageDialog(frame, "Must input characters only.", "Warning", 2);
	                } else {
	    				if (input != null) {
	    					if (insert(input, "Easy")) {
	    						new NewGame(input);
	    						break;
	    					} else {
	    						JOptionPane.showMessageDialog(frame, "Save already exists, please pick a different name.", "Warning", 2);
	    					}
	    				}
	                }
				}
			}
		});
		
		final JLabel loadGame = new JLabel();
		loadGame.setIcon(new ImageIcon("images/menuload.gif"));
		loadGame.setText("Load Game");
		loadGame.setForeground(Color.RED);
		loadGame.setFont(new Font("Serif", Font.PLAIN, 36));
		loadGame.setHorizontalTextPosition(JLabel.CENTER);
		loadGame.setVerticalTextPosition(JLabel.CENTER);
		loadGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				new LoadGame();
			}
		});
		
		final JLabel exitGame = new JLabel();
		exitGame.setIcon(new ImageIcon("images/menuexit.gif"));
		exitGame.setText("Exit Game");
		exitGame.setForeground(Color.RED);
		exitGame.setFont(new Font("Serif", Font.PLAIN, 36));
		exitGame.setHorizontalTextPosition(JLabel.CENTER);
		exitGame.setVerticalTextPosition(JLabel.CENTER);
		exitGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
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
		myFrame.setVisible(true);
	}

	/**
     * Create the database table if it doesn't exist.
     */
    private static void createDatabase() {

    	final String sql = "CREATE TABLE IF NOT EXISTS saves (\n"
				 + "id INTEGER PRIMARY KEY ASC,\n"
				 + "name text NOT NULL UNIQUE, \n"
				 + "difficulty text NOT NULL);";
    	
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
    protected HashMap<Integer, String> select(final String theQuery) {
    	
    	final HashMap<Integer, String> list = new HashMap<Integer, String>(); 
    	
        try (final Connection conn = this.connect(); final Statement stmt = conn.createStatement(); final ResultSet rs = stmt.executeQuery(theQuery)) {
        	try {
    			while (rs.next()) {
    				final String data = String.format("%s | %s", rs.getString("name"), rs.getString("difficulty"));
    				list.put(rs.getInt("id"), data);
    			}
    		} catch (final SQLException e) {
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
     * @param theDifficulty The difficulty of the game.
     */
    protected static boolean insert(final String theName, final String theDifficulty) {
    	
    	final String sql = "INSERT INTO saves(name, difficulty) VALUES(?, ?)";
    	
        try (final Connection conn = DriverManager.getConnection(DATABASE); final PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	pstmt.setString(1, theName);
        	pstmt.setString(2, theDifficulty);
            pstmt.executeUpdate();
        } catch (final SQLException e) {
        	return false;
        }
        return true;
    }
    
    /**
     * Update the data of an existing save from the database.
     * @param theName The name of the save to update.
     * @param theDifficulty The game's difficulty to update.
     */
    protected void update(final String theName, final String theDifficulty) {
    	
        final String sql = "UPDATE saves SET difficulty = ? WHERE name = ?";

        try (final Connection conn = this.connect(); final PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, theDifficulty);
            pstmt.setString(2, theName);
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
    
    /**
     * Plays a sound from an audio file.
     * @param soundFile The sound file's name.
     */
    protected static void playSound(final String soundFile) {
    	
        final File f = new File("sounds/" + soundFile);
        AudioInputStream audioIn = null;
        
		try {
			audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
		} catch (final MalformedURLException e1) {
			e1.printStackTrace();
		} catch (final UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (final IOException e1) {
			e1.printStackTrace();
		}  
		
        Clip clip = null;
        
		try {
			clip = AudioSystem.getClip();
		} catch (final LineUnavailableException e) {
			e.printStackTrace();
		}
		
        try {
			clip.open(audioIn);
		} catch (final LineUnavailableException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
        clip.start();
    }
}