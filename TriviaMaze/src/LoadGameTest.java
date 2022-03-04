/*
 * This file is used for testing the LoadGame class for the Trivia Maze Game.
 */

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
    void setUp() {
    	
    	myTest = new LoadGame();
    }
	
    /**
     * Testing LoadGame constructor.
     */
    @Test
    void testLoadGame() {  
    	
    	assertNotNull("The object is null.", myTest);
    }  
}  