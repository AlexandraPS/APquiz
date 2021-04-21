package sk.plajdickova.apquiz;

import sk.plajdickova.apquiz.data.Database;
import sk.plajdickova.apquiz.data.Question;
import sk.plajdickova.apquiz.ui.QuizGui;

public class QuizController {
    public QuizGui ui;
    private boolean isAdmin = false;
    private static final String PASSWORD = "admin";

    public QuizController(QuizGui ui) {
        this.ui = ui;
        ui.setController(this);
        Database.getInstance().connect(); //TODO: nastavit spravu po neuspesnom pripojeni na databazu
    }
    public void login(String password) {
        isAdmin = password.equals(PASSWORD);
        ui.showLoginStatus(isAdmin);
    }
    public boolean getIsAdmin() {
        return isAdmin;
    }
    public boolean addQuestion(String text, String category) {
        if(text.isBlank()) {
            return false;
        }
        return Database.getInstance().addQuestion(new Question(text,category));
    }
}
