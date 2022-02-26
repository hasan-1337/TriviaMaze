import javax.swing.JOptionPane;

public class Room {
	
	// Whether or not the player chose to open the door.
	private boolean myDoorOption;
	
	// Whether or not the player answered correctly.
	private boolean myResult;
	
	// The player's keys.
	private int myKeys;
	
	/**
     * Check if the player wants to open a door.
     * @param theKeys The player's keys.
     */
	public Room(final int theKeys) {
		final int option = JOptionPane.showConfirmDialog(null, "You are at a locked door!\nAre you ready to answer a question?\nYou'll have 10 seconds to answer the question!", "Trivia Maze", JOptionPane.YES_OPTION);
		
		if (option == JOptionPane.YES_OPTION) {
			myDoorOption = true;
			final Question_Answer result = new Question_Answer(theKeys);
			myKeys = result.getKeys();
			myResult = result.getResult();
		} else {
			myDoorOption = false;
			myKeys = theKeys;
		}
	}
	
	/**
     * Getter for myDoorOption.
     * @return Retrieves whether or not the user attempted to open the door.
     */
	public boolean getDoorOption() {
		return myDoorOption;
	}
	
	/**
     * Getter for myResult.
     * @return Retrieves the result of the question.
     */
	public boolean getResult() {
		return myResult;
	}
	
	/**
     * Getter for myKeys.
     * @return Retrieves the player's updated keys.
     */
	public int getKeys() {
		return myKeys;
	}
}
