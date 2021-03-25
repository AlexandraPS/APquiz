package sk.plajdickova.apquiz.data;

public class Question {
    public int id;
    public String text;
    public int answerId;

    public Question() {
    }

    public Question(int id, String text, int answerId) {
        this.id = id;
        this.text = text;
        this.answerId = answerId;
    }

}
