import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

/**
* @author Hasan, Mohammed, Manuel
* @version 1.0
*/
public class TriviaMaze {
	
	// Database's connection URL.
	protected static final String DATABASE = "jdbc:sqlite:/C:\\sqlite\\saves.db";
	
	// Scanner used for user inputs.
	private static Scanner myInput;
	
    /**
     * @param sArgs The command line arguments
     * @throws Exception 
     */
	public static void main(final String[] sArgs) throws Exception {
		
		System.out.println("Welcome to Trivia Maze!\n\n[Options]\n1. New Game\n2. Load Game\n3. Exit\n");
		
		while (true) {
			System.out.print("Please enter an option based on the number: ");
			myInput = new Scanner(System.in);
			
			final int choice = myInput.nextInt();
			boolean exit = true;
			
			switch (choice) {
				case 1: { // New game
					new Maze(connect(), myInput);
					break;
				}
				case 2: { // Load
					new Loadgame(connect(), myInput);
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
     * Connect to the database
     * @return the Connection object
     * @throws Exception 
     */
    private static Connection connect() throws Exception {
    	
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection(DATABASE);
            
            if (conn != null) {
            	System.out.println("Connected to the database.");
            } else {
            	throw new Exception("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
