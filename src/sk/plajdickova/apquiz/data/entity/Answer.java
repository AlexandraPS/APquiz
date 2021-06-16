package sk.plajdickova.apquiz.data.entity;

public class Answer {
    public int id;
    public String text;
    public int questionId;
    public boolean isCorrect;

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
