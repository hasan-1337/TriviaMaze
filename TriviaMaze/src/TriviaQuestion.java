public class TriviaQuestion {

    private final String question;
    private final String answer;

    public TriviaQuestion(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
