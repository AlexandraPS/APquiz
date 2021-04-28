package sk.plajdickova.apquiz;

import sk.plajdickova.apquiz.data.Answer;
import sk.plajdickova.apquiz.data.Database;
import sk.plajdickova.apquiz.data.Question;
import sk.plajdickova.apquiz.ui.QuizGui;

import java.util.ArrayList;

public class QuizController {
    public static final int ANSWERS_MAX = 4;
    public QuizGui ui;
    private boolean isAdmin = true; // TODO: zmeniť na false po dokončení
    private static final String PASSWORD = "admin";

    public QuizController(QuizGui ui) {
        this.ui = ui;
        ui.setController(this);
        boolean ok = Database.getInstance().connect();
        if (!ok){
            ui.showConnectionError();
            System.exit(-1);
        }
        ArrayList <Question> questions = Database.getInstance().getQuestions();
        for (Question q:questions) {
            System.out.println(q);
        }
        ArrayList <Answer> answers = Database.getInstance().getAnswers();
        for (Answer a: answers) {
            System.out.println(a);
        }
    }
    public void login(String password) {
        isAdmin = password.equals(PASSWORD);
        ui.showLoginStatus(isAdmin);
    }
    public boolean getIsAdmin() {
        return isAdmin;
    }
    public boolean addQuestion(String text, String category,String [] answers,boolean [] correct) {
        if(text.isBlank()) {
            return false;
        }
        int questionId = Database.getInstance().addQuestion(new Question(text,category));
        if (questionId == -1) return false;
        for (int i = 0; i < ANSWERS_MAX; i++) {
            if(!answers[i].isBlank()) Database.getInstance().addAnswer(new Answer(answers[i],questionId,correct[i]));


        }
        return true;
    }

}
