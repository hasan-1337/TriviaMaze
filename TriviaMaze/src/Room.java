import javax.swing.JOptionPane;

public class Room extends TriviaMaze {
	
	// Whether or not the player chose to open the door.
	protected boolean myDoorOption;
	
	// Whether or not the player answered correctly.
	protected boolean myResult;
	
	// The player's keys.
	protected int myKeys;
	
	public Room(final int theKeys) {
		final int option = JOptionPane.showConfirmDialog(null, "You are at a locked door!\nAre you ready to answer a question?\nYou'll have 10 seconds to answer the question!", "Trivia Maze", JOptionPane.YES_OPTION);
		
		if (option == JOptionPane.YES_OPTION) {
			myDoorOption = true;
			final Question_Answer result = new Question_Answer(theKeys);
			myKeys = result.myKeys;
			myResult = result.myCorrect;
		} else {
			myDoorOption = false;
			myKeys = theKeys;
		}
	}
}
