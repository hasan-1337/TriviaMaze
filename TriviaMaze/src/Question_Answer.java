import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Question_Answer extends TriviaMaze {
	
	// The player answered correctly or not.
	protected boolean myCorrect;
	
	// The player's keys.
	protected int myKeys;

	private HashMap<Integer, TriviaQuestion> dictionary;

	private int numberOfQuestions;
	
	public Question_Answer(final int theKeys) {
		Random rand = new Random();
		dictionary = new HashMap<>();
		numberOfQuestions = 0;
		readFile();
		myKeys = theKeys;
		final String[] type = {"*", "+", "-", "trivia"};
		final int num1 = (int) Math.round(Math.random() * 10);
		final int num2 = (int) Math.round(Math.random() * 10);
		final String symbol = type[rand.nextInt(4)];
		String answer = "";
		String question = "";
		myCorrect = false;
		
		switch (symbol) {
			case "*": {
				answer = Integer.toString(num1 * num2);
				break;
			}
			case "+": {
				answer = Integer.toString(num1 + num2);
				break;
			}
			case "-": {
				answer = Integer.toString(num1 - num2);
				break;
			}
			case "trivia": {
				TriviaQuestion word = dictionary.get(rand.nextInt(numberOfQuestions));
				question = word.getQuestion();
				answer = word.getAnswer();
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
		
		final Clip sound = playSound("Question.wav");
		final String input;

		if (!symbol.equals("trivia")) {
			input = JOptionPane.showInputDialog(frame, String.format("Solve the problem to open the door!\nProblem: %d %s %d", num1, symbol, num2), "Door Trivia", 3);
		} else {
			input = JOptionPane.showInputDialog(frame, String.format("Solve the problem to open the door!\nProblem: %s", question), "Door Trivia", 1);
		}

		if (input == null) {
			sound.stop();
			playSound("Incorrect.wav");
			JOptionPane.showMessageDialog(null, String.format("You lost a key for not answering!\nKeys Remaining: %d", --myKeys), "Door Trivia", 0);
		} else if (input.equalsIgnoreCase(answer)) {
			sound.stop();
			playSound("Correct.wav");
			JOptionPane.showMessageDialog(null, String.format("Correct!\nYou opened the door.\nKeys Remaining: %d", myKeys), "Door Trivia", 1);
			playSound("Door.wav");
			myCorrect = true;
		} else {
			sound.stop();
			playSound("Incorrect.wav");
			JOptionPane.showMessageDialog(null, String.format("Incorrect!\nYou lost a key.\nKeys Remaining: %d", --myKeys), "Door Trivia", 0);
		}
	}

	/**
	 * Reads a file that contains the trivia questions with answers and loads these questions and answers in the
	 * dictionary.
	 */
	private void readFile() {
		Scanner input;
		try {
			input = new Scanner(new File("src/Questions"));
		} catch (FileNotFoundException e) {
			System.out.println("File doesn't exist");
			return;
		}
		while (input.hasNextLine()) {
			String question = input.nextLine();
			String answer = input.nextLine();
			TriviaQuestion triviaQuestion = new TriviaQuestion(question, answer);
			dictionary.put(numberOfQuestions, triviaQuestion);
			numberOfQuestions++;
		}
	}
}
