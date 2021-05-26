package sk.plajdickova.apquiz.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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

    public Set<String> getCategories() {
        HashSet<String > categories = new HashSet<>();
        for (Question q:questions) {
            categories.add(q.category);
        }
        return categories;
    }
}
