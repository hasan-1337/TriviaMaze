import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;  
  
public class NewGameTest {  
  
    // Class object to be tested.
	NewGame myTest;
	
    /**
     * This function with @Before annotation is called before every test case is executed.
     */
    @Before
    public void setUp() {
    	
    	myTest = new NewGame(null);
    }
	
    /**
     * Testing NewGame constructor.
     */
    @Test
    public void testNewGame() {  
    	
    	assertNotNull("The object is null.", myTest);
    }  
}  