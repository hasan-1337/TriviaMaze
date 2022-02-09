import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Loadgame {
	
	public Loadgame(final Connection connection, final Scanner input) {
		Database(connection); 
	}
	
    /**
     * Connect to the database and send or retrieve information.
     * @param theConnection	Connection object to be used to connect to the database.
     */
    private void Database(final Connection theConnection) {

    	try (final Connection conn = theConnection; Statement query = conn.createStatement()) {
    		System.out.println("Connected to the database.");
        } catch (final SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
