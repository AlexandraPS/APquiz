package sk.plajdickova.apquiz.ui;

import sk.plajdickova.apquiz.data.Database;
import sk.plajdickova.apquiz.data.Question;

import java.util.ArrayList;

public class QuizGui {

    public QuizGui() {
        Database db = new Database();
        boolean ok = db.connect();
        System.out.println(ok);
        ok = db.addQuestion(new Question(0, "Nový t_p auta ", "Slovenský jazyk"));
        System.out.println(ok);
        ok = db.addQuestion(new Question(0, "Drevená g_tara ", "Slovenský jazyk"));
        System.out.println(ok);
        ArrayList<Question> questions = db.getQuestions();
        for (Question q : questions) {
            System.out.println(q.id + " " + q.text + " " + q.category);
        }
    }
}
