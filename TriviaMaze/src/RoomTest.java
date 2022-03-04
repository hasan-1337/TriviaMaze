import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;  
  
public class RoomTest {  
  
    // Class object to be tested.
	Room myTest;
	
    /**
     * This function with @Before annotation is called before every test case is executed.
     */
    @Before
    public void setUp() {
    	
    	myTest = new Room(0);
    }
	
    /**
     * Testing Room constructor.
     */
    @Test 
    public void testRoom() {  
    	
    	assertNotNull("The object is null.", myTest);
    }  
}  