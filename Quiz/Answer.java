public class Answer {
    private int answerId;
    private int questionId;
    private String answerText;
    private boolean isCorrect;

    Answer(int answerId, int questionId, String answerText, boolean isCorrect) {
        this.answerId = answerId;
        this.questionId = questionId;
        this.answerText = answerText;
        this.isCorrect = isCorrect;
    }

    public int getAnswerId() {
        return answerId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }
}
