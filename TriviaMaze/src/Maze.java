import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Maze {
	
	public Maze(final Connection connection, final Scanner input) {
		
    	final String sql = "CREATE TABLE IF NOT EXISTS saves (\n"
				 + "id integer PRIMARY KEY,\n"
				 + "name text NOT NULL,\n" 
				 + "capacity real);";
    	
		Database(connection, sql); 
	}
	
    /**
     * Connect to the database and send or retrieve information.
     * @param theConnection	Connection object to be used to connect to the database.
     * @param theSQL The SQL query to send to the database.
     */
    private void Database(final Connection theConnection, final String theSQL) {

        try (final Connection conn = theConnection; Statement query = conn.createStatement()) {
        	query.execute(theSQL);
        } catch (final SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
