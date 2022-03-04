/*
 * This file is used for testing the NewGame class for the Trivia Maze Game.
 */

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
    void setUp() {
    	
    	myTest = new NewGame(null);
    }
	
    /**
     * Testing NewGame constructor.
     */
    @Test
    void testNewGame() {  
    	
    	assertNotNull("The object is null.", myTest);
    }  
}  