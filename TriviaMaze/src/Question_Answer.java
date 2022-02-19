import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Question_Answer extends TriviaMaze {
	
	// The player answered correctly or not.
	protected boolean myCorrect;
	
	// The player's keys.
	protected int myKeys;
	
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
		
		playSound("Question.wav");
		final String input = JOptionPane.showInputDialog(frame, String.format("Solve the problem to open the door!\nProblem: %d %c %d", num1, symbol, num2) , "Door Trivia", 3);
		
		if (input == null) {
			playSound("Incorrect.wav");
			JOptionPane.showMessageDialog(null, String.format("You lost a key for not answering!\nKeys Remaining: %d", --myKeys), "Door Trivia", 0);
		} else if (input.equals(Integer.toString(answer))) {
			playSound("Correct.wav");
			JOptionPane.showMessageDialog(null, String.format("Correct!\nYou opened the door.\nKeys Remaining: %d", myKeys), "Door Trivia", 1);
			playSound("Door.wav");
			myCorrect = true;
		} else {
			playSound("Incorrect.wav");
			JOptionPane.showMessageDialog(null, String.format("Incorrect!\nYou lost a key.\nKeys Remaining: %d", --myKeys), "Door Trivia", 0);
		}
	}
}
