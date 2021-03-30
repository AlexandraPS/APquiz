package sk.plajdickova.apquiz.data;

public class Question {
    public int id;
    public String text;
    public String category;

    public Question() {
    }

    public Question(int id, String text, String category) {
        this.id = id;
        this.text = text;
        this.category = category;

    }

}
