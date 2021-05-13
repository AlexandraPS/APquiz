package sk.plajdickova.apquiz.data;

import java.util.ArrayList;

public class Test {
    public int id;
    public String title;
    public ArrayList<Question> questions;

    public Test() { }

    public Test(String title, ArrayList<Question> questions) {
        this.title = title;
        this.questions = questions;
    }

    @Override
    public String toString() {
        return id + " " + title + " " + questions;
    }
}
