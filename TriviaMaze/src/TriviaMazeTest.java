/*
 * This file is used for testing the TriviaMaze class for the Trivia Maze Game.
 */

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
    void setUp() {
    	
    	myTest = new TriviaMaze();
    }
	
    /**
     * Testing TriviaMaze constructor.
     */
    @Test
    void testTriviaMaze() {  
    	
    	assertNotNull("The object is null.", myTest);
    }  
}  