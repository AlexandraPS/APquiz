package sk.plajdickova.apquiz.data;

public class Answer {
    int id;
    String text;
    int questionId;
    boolean isCorrect;

    public Answer() {
    }

    public Answer(String text, int questionId, boolean isCorrect) {
        this.text = text;
        this.questionId = questionId;
        this.isCorrect = isCorrect;
    }

    @Override
    public String toString() {
        return id + " " + text + " " + questionId + " " + isCorrect;
    }
}
