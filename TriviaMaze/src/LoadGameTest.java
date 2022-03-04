import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;  
  
public class LoadGameTest {  
  
    // Class object to be tested.
	LoadGame myTest;
	
    /**
     * This function with @Before annotation is called before every test case is executed.
     */
    @Before
    public void setUp() {
    	
    	myTest = new LoadGame();
    }
	
    /**
     * Testing LoadGame constructor.
     */
    @Test
    public void testLoadGame() {  
    	
    	assertNotNull("The object is null.", myTest);
    }  
}  