import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

/**
* @author Hasan, Mohammed, Manuel
* @version 1.0
*/
public abstract class TriviaMaze {
	
	// Database's connection URL.
	protected static final String DATABASE = "jdbc:sqlite:/C:\\sqlite\\saves.db";
	
	// Scanner used for user inputs.
	private static Scanner myInput;
	
    /**
     * @param sArgs The command line arguments
     * @throws Exception 
     */
	public static void main(final String[] sArgs) throws Exception {
		
		createDatabase();
		
		System.out.println("Welcome to Trivia Maze!\n\n[Options]\n1. New Game\n2. Load Game\n3. Exit\n");
		
		while (true) {
			System.out.print("Please enter an option based on the number: ");
			myInput = new Scanner(System.in);
			
			final int choice = myInput.nextInt();
			boolean exit = true;
			
			switch (choice) {
				case 1: { // New game
					new Maze(myInput);
					break;
				}
				case 2: { // Load
					new Loadgame(myInput);
					break;
				}
				case 3: { // Exit
					System.exit(0);
					break;
				}
				default: { // Invalid input
					System.out.println("Invalid input, please try again.\n");
					exit = false;
				}
			}
			
			if (exit) { // Exit the while loop.
				break;
			}
		}
		myInput.close();
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
    protected HashMap<Integer, String> select(final String theQuery) {
    	
    	HashMap<Integer, String> list = new HashMap<Integer, String>(); 
    	
        try (final Connection conn = this.connect(); final Statement stmt = conn.createStatement(); final ResultSet rs = stmt.executeQuery(theQuery)){
        	
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
