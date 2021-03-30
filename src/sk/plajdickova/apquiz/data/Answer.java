package sk.plajdickova.apquiz.data;

public class Answer {
    int id;
    String text;
    int questionId;
    boolean isCorrect;

    public Answer() {
    }

    public Answer(int id, String text, int questionId,boolean isCorrect) {
        this.id = id;
        this.text = text;
        this.questionId = questionId;
        this.isCorrect = isCorrect;
    }

}
