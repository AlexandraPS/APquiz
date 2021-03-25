package sk.plajdickova.apquiz.data;

public class Answer {
    int id;
    String text;
    int questionId;

    public Answer() {
    }

    public Answer(int id, String text, int questionId) {
        this.id = id;
        this.text = text;
        this.questionId = questionId;
    }

}
