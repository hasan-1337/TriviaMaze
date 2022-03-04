import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;  
  
public class TriviaMazeTest {  
  
    // Class object to be tested.
	TriviaMaze myTest;
	
    /**
     * This function with @Before annotation is called before every test case is executed.
     */
    @Before
    public void setUp() {
    	
    	myTest = new TriviaMaze();
    }
	
    /**
     * Testing TriviaMaze constructor.
     */
    @Test
    public void testTriviaMaze() {  
    	
    	assertNotNull("The object is null.", myTest);
    }  
}  