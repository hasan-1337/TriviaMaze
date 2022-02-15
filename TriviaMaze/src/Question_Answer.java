
public class Question_Answer extends TriviaMaze {
	
	public Question_Answer() {
		
		final char[] type = {'/', '*', '+', '-'};
		final int num1 = (int) Math.round(Math.random() * 10);
		final int num2 = (int) Math.round(Math.random() * 10);
		final char symbol = type[(int) Math.round(Math.random() * 3)];
		int answer;
		
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
	}
}
