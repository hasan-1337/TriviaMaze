/*
 * This file is used for testing the Question_Answer class for the Trivia Maze Game.
 */

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;  
  
public class Question_AnswerTest {  
  
    // Class object to be tested.
	Question_Answer myTest;
	
    /**
     * This function with @Before annotation is called before every test case is executed.
     */
    @Before
    void setUp() {
    	
    	myTest = new Question_Answer(0);
    }
	
    /**
     * Testing Question_Answer constructor.
     */
    @Test
    void testQuestion_Answer() {  
    	
    	assertNotNull("The object is null.", myTest);
    }  
}  