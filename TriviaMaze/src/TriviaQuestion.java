/**
 * Class TriviaQuestion contains the question and answer for a TriviaQuestion object.
 */

public class TriviaQuestion {
    private final String question;
    private final String answer;

    /**
     * Constructs a TriviaQuestion with the given parameters: question and answer.
     * @param question
     * @param answer
     */
    public TriviaQuestion(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    /**
     * returns the question.
     * @return
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Returns the answer
     * @return
     */
    public String getAnswer() {
        return answer;
    }
}
