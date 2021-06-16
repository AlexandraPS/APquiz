package sk.plajdickova.apquiz;

import sk.plajdickova.apquiz.data.entity.Answer;
import sk.plajdickova.apquiz.data.entity.Result;
import sk.plajdickova.apquiz.data.repository.AnswerRepository;
import sk.plajdickova.apquiz.data.repository.Database;
import sk.plajdickova.apquiz.data.entity.Question;
import sk.plajdickova.apquiz.data.entity.Test;
import sk.plajdickova.apquiz.data.repository.QuestionRepository;
import sk.plajdickova.apquiz.view.main.QuizView;
import sk.plajdickova.apquiz.view.test.TestView;

import java.util.ArrayList;

public class QuizController {
    public static final int ANSWERS_MAX = 4;
    public QuizView view;
    private boolean isAdmin = true; // TODO: zmeniť na false po dokončení
    private static final String PASSWORD = "admin";

    public QuizController(QuizView view) {
        this.view = view;
        view.setController(this);
        boolean ok = Database.getInstance().connect();
        if (!ok){
            view.showConnectionError();
            System.exit(-1);
        }
       /* ArrayList <Question> questions = Database.getInstance().getQuestions();
        for (Question q:questions) {
            System.out.println(q);
        }
        ArrayList <Answer> answers = Database.getInstance().getAnswers();
        for (Answer a: answers) {
            System.out.println(a);
        } */
        ArrayList<Test> tests = Database.getInstance().getTests();
        for(Test t: tests) {
            System.out.println(t);
        }
        //System.out.println(Database.getInstance().getQuestion(2));
        ArrayList<Result> results = Database.getInstance().getResults();
        for(Result r: results) {
            System.out.println(r);
        }
    }
    public void login(String password) {
        isAdmin = password.equals(PASSWORD);
        view.showLoginStatus(isAdmin);
    }
    public boolean getIsAdmin() {
        return isAdmin;
    }
    public boolean addQuestion(String text, String category,String [] answers,boolean [] correct) {
        if(text.isBlank()) {
            return false;
        }
        int questionId = QuestionRepository.getInstance().addQuestion(new Question(text,category));
        if (questionId == -1) return false;
        for (int i = 0; i < ANSWERS_MAX; i++) {
            if(!answers[i].isBlank()) AnswerRepository.getInstance().addAnswer(new Answer(answers[i],questionId,correct[i]));


        }
        return true;
    }

    public void tryToShowNewTestDialog() {
        if (isAdmin) view.showNewTestDialog(QuestionRepository.getInstance().getQuestions());
        else view.showNoAdmin();
    }

    public boolean addTest(String title, ArrayList <Question> questions) {
        if(title.isBlank()) {
            return false;
        }
        return Database.getInstance().addTest(new Test(title,questions)); //true co znamena ze sa vytvoril novy test, pridal do Db
    }

    public void startNewTest() {
        ArrayList <Test> tests = Database.getInstance().getTests();
        if (tests.isEmpty()) {
            view.showNoTests();
            return; // pokial mam za if return, nemusim davat else!!
        } view.showTests(tests);
    }

    public void startTest(Test test) {
        TestView ui = new TestView();
        new TestController(ui, test);
        this.view.showTestGui(ui);
    }
}
// TODO: zobrazovanie testov,oprava testov