package sk.plajdickova.apquiz.data.entity;

import sk.plajdickova.apquiz.data.entity.Answer;

import java.util.ArrayList;

public class Question {
    public int id;
    public String text;
    public String category;
    public ArrayList<Answer> answers;

    public Question() {
    }

    public Question(String text, String category) {
        this.text = text;
        this.category = category;
    }

    @Override
    public String toString() {
        return id + " " + text + " " + category + " " + answers;
    }
}
