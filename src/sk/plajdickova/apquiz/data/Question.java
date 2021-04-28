package sk.plajdickova.apquiz.data;

public class Question {
    public int id;
    public String text;
    public String category;

    public Question() {
    }

    public Question(String text, String category) {
        this.text = text;
        this.category = category;
    }

    @Override
    public String toString() {
        return id + " " + text + " " + category;
    }
}
