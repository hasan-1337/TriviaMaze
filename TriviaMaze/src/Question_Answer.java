import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Question_Answer extends TriviaMaze {
	
	// The player answered correctly or not.
	protected boolean correct = false;
	
	public Question_Answer() {
		
		final char[] type = {'/', '*', '+', '-'};
		final int num1 = (int) Math.round(Math.random() * 10);
		final int num2 = (int) Math.round(Math.random() * 10);
		final char symbol = type[(int) Math.round(Math.random() * 3)];
		int answer = 0;
		
		switch (symbol) {
			case '/': {
				answer = num1 / num2;
				break;
			}
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
		final String input = JOptionPane.showInputDialog(frame, String.format("Solve the problem to open the door!\nYou have only have 10 seconds!\nProblem: %d %c %d", num1, symbol, num2) , "Door Trivia", 3);
		
		if (input == null) {
			JOptionPane.showMessageDialog(frame, String.format("You lost a key for not answering!\nKeys Remaining: %d", --myKeys), "Door Trivia", 0);
		} else if (input.equals(Integer.toString(answer))) {
			JOptionPane.showMessageDialog(frame, String.format("Correct!\nYou opened the door.\nKeys Remaining: %d", myKeys), "Door Trivia", 1);
			correct = true;
		} else {
			JOptionPane.showMessageDialog(frame, String.format("Incorrect!\nYou lost a key.\nKeys Remaining: %d", --myKeys), "Door Trivia", 0);
		}
	}
}
