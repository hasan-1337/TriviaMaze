import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;  
  
public class MazeTest {  
  
    // Class object to be tested.
	Maze myTest;
	
    // Class object to be tested.
	Maze myTest2;
	
    /**
     * This function with @Before annotation is called before every test case is executed.
     */
    @Before
    public void setUp() {
    	
    	myTest = new Maze(null, 0);
    	myTest2 = new Maze(null, null, 0, 0, 0, null);
    }
	
    /**
     * Testing Maze constructor.
     */
    @Test
    public void testMaze() {  
    	
    	assertNotNull("The object is null.", myTest);
    }  
  
    /**
     * Testing second Maze constructor.
     */
    @Test
    public void testMaze2() {  
    	
    	assertNotNull("The object is null.", myTest2);
    }  
}  