import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
* @author Hasan, Mohammed, Manuel
* @version 1.0
*/
public class TriviaMaze {
	
	// Database's connection URL.
	private static final String DATABASE = "jdbc:sqlite:/C:\\sqlite\\saves.db";
	
	// The main frame for the GUI.
	protected static JFrame myFrame;
	
	// The main menu music.
	protected static Clip myMusic;
	
	// The player's doors.
	protected int myDoors;

	
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
		
		myFrame = new JFrame("Trivia Maze");
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setSize(800, 600);
		myFrame.setLocationRelativeTo(null);
		myFrame.setResizable(false);
		myFrame.setLayout(null);
		myFrame.setUndecorated(true);
		
		final JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 1));
		panel.setBounds(0, 0, 800, 600);
		panel.add(new JLabel(new ImageIcon("images/title.jpg")));
		
		final JLabel newGame = new JLabel(new ImageIcon("images/new.jpg"));
		newGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				playSound("Select.wav");
				
				while (true) {
					final String input = JOptionPane.showInputDialog(null, "Enter a save name:" , "New Game", 1);
	                
	                if (input == null) {
	                	playSound("Select.wav");
	                    break;
	                }
	                
	                final Pattern pattern = Pattern.compile("^[a-zA-Z]+$");
	                final Matcher matcher = pattern.matcher(input);
	               
	                if (input.length() < 3) {
	                	playSound("Error.wav");
	                	JOptionPane.showMessageDialog(null, "Must have at least 3 characters.", "Warning", 2);
	                } else if (!matcher.find()) {
	                	playSound("Error.wav");
	                	JOptionPane.showMessageDialog(null, "Must input characters only.", "Warning", 2);
	                } else {
	    				if (input != null) {
	    					if (insert(input, "Easy")) {
	    						playSound("Select.wav");
	    						new NewGame(input);
	    						break;
	    					} else {
	    						playSound("Error.wav");
	    						JOptionPane.showMessageDialog(null, "Save already exists, please pick a different name.", "Warning", 2);
	    					}
	    				}
	                }
				}
			}
		});
		
		final JLabel loadGame = new JLabel(new ImageIcon("images/load.jpg"));
		loadGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				playSound("Select.wav");
				new LoadGame();
			}
		});
		
		final JLabel exitGame = new JLabel(new ImageIcon("images/exit.jpg"));
		exitGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				playSound("Select.wav");
				
				if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit? :(", "Exit Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					System.out.println("Thanks for playing!");
					System.exit(0);
				}
				playSound("Select.wav");
			}
		});
		
		panel.add(newGame);
		panel.add(loadGame);
		panel.add(exitGame);
		
		myFrame.add(panel);
		myFrame.setVisible(true);
		
		if (myMusic != null) {
			myMusic.stop();
		}
		myMusic = playSound("mainmenu.wav");
		myMusic.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	/**
     * Launches the game.
     * @param theSave The save file name.
     * @param theKeys The player's keys.
     */
	protected void launchGame(final String theSave, final int theKeys) {
		
    	final JFrame loading = new JFrame("Trivia Maze");
    	loading.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	loading.getContentPane().add(new JLabel(new ImageIcon("images/loading.gif")));
    	loading.setSize(800, 600);
    	loading.setLocationRelativeTo(null);
    	loading.setUndecorated(true);
    	loading.setVisible(true);
    	
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
				  myMusic.stop();
				  myMusic = playSound("Maze.wav");
				  myMusic.loop(Clip.LOOP_CONTINUOUSLY);
				  loading.dispose();
				  
				  final JPanel game = new Maze(theSave, theKeys);
				  myFrame = new JFrame("Trivia Maze");
				  myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				  myFrame.setSize(800, 600);
				  myFrame.setLocationRelativeTo(null);
				  myFrame.setResizable(false);
				  myFrame.setUndecorated(true);
				  myFrame.add(game);
				  myFrame.setVisible(true);
			  }
		}, 3500);
	}
	
    /**
     * Plays a sound from an audio file.
     * @param theSoundFile The sound file's name.
     * @return Clip Retrieves the object of the sound that is playing.
     */
    protected static Clip playSound(final String theSoundFile) {
    	
		try {
			final AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("sounds/" + theSoundFile).toURI().toURL());
			final Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
			return clip;
		}  
        catch(final Exception e) {
            e.printStackTrace();
        }
		return null;
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
     * @param theKey The player's keys.
     * @param theDoor How many doors
     */
    protected void update(final String theName, final String theDifficulty, final int theKey, final int theDoor) {
    	
        final String sql = "UPDATE saves SET difficulty = ?, keys = ?, doors = ? WHERE name = ?";

        try (final Connection conn = this.connect(); final PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, theDifficulty);
            pstmt.setInt(2, theKey);
            pstmt.setInt(3, theDoor);
            pstmt.setString(4, theName);
            pstmt.executeUpdate();
        } catch (final SQLException e) {
        	e.printStackTrace();
        }
    }
    
    /**
     * Delete a save from the database specified by the id.
     * @param theName The name of the save to delete.
     */
    protected void delete(final String theName) {
    	
    	final String sql = "DELETE FROM saves WHERE name = ?";

        try (final Connection conn = this.connect(); final PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, theName);
            pstmt.executeUpdate();
        } catch (final SQLException e) {
        	e.printStackTrace();
        }
    }
    
	/**
     * Create the database table if it doesn't exist.
     */
    private static void createDatabase() {

    	final String sql = "CREATE TABLE IF NOT EXISTS saves (\n"
				 + "id INTEGER PRIMARY KEY ASC,\n"
				 + "name text NOT NULL UNIQUE, \n"
				 + "keys INTEGER, \n"
				 + "doors INTEGER, \n"
				 + "difficulty text NOT NULL);";
    	
        try (final Connection conn = DriverManager.getConnection(DATABASE); final Statement stmt = conn.createStatement()) {
        	stmt.execute(sql);
        } catch (final SQLException e) {
        	e.printStackTrace();
        }
    }
}