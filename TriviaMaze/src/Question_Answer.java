import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Question_Answer {
	
	// The player answered correctly or not.
	private boolean myCorrect;
	
	// The player's keys.
	private int myKeys;
	
	/**
     * Prompt the user with a question to answer.
     * @param theKeys The player's keys.
     */
	public Question_Answer(final int theKeys) {
		
		myKeys = theKeys;
		final char[] type = {'*', '+', '-'};
		final int num1 = (int) Math.round(Math.random() * 10);
		final int num2 = (int) Math.round(Math.random() * 10);
		final char symbol = type[(int) Math.round(Math.random() * 2)];
		int answer = 0;
		myCorrect = false;
		
		switch (symbol) {
			case '*': {
				answer = num1 * num2;
				break;
			}
			case '+': {
				answer = num1 + num2;
				break;
			}
			case '-': {
				answer = num1 - num2;
				break;
			}
		}
		
		final JFrame frame = new JFrame();
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
				  	if (myKeys == theKeys && !myCorrect) {
						frame.dispose();
				  	}
			  }
		}, 10000);
		
		final Clip sound = TriviaMaze.playSound("Question.wav");
		final String input = JOptionPane.showInputDialog(frame, String.format("Solve the problem to open the door!\nProblem: %d %c %d", num1, symbol, num2) , "Door Trivia", 3);
		
		if (input == null) {
			sound.stop();
			TriviaMaze.playSound("Incorrect.wav");
			JOptionPane.showMessageDialog(null, String.format("You lost a key for not answering!\nKeys Remaining: %d", --myKeys), "Door Trivia", 0);
		} else if (input.equals(Integer.toString(answer))) {
			sound.stop();
			TriviaMaze.playSound("Correct.wav");
			JOptionPane.showMessageDialog(null, String.format("Correct!\nYou opened the door.\nKeys Remaining: %d", myKeys), "Door Trivia", 1);
			TriviaMaze.playSound("Door.wav");
			myCorrect = true;
		} else {
			sound.stop();
			TriviaMaze.playSound("Incorrect.wav");
			JOptionPane.showMessageDialog(null, String.format("Incorrect!\nYou lost a key.\nKeys Remaining: %d", --myKeys), "Door Trivia", 0);
		}
	}
	
	/**
     * Getter for myKeys.
     * @return Retrieves the player's updated keys.
     */
	public int getKeys() {
		return myKeys;
	}
	
	/**
     * Getter for myCorrect.
     * @return Retrieves if the user answered correctly or not.
     */
	public boolean getResult() {
		return myCorrect;
	}
}
