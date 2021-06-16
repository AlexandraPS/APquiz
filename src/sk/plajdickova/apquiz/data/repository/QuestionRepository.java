package sk.plajdickova.apquiz.data.repository;

import sk.plajdickova.apquiz.data.entity.Question;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class QuestionRepository {

    private static final int ERROR = -1;
    private static QuestionRepository instance;


    public static QuestionRepository getInstance() {
        if (instance == null) {
            instance = new QuestionRepository();
        }
        return instance;
    }

    private QuestionRepository() {
    }
    public int addQuestion(Question q) {
        try {
            PreparedStatement p = Database.getInstance().getConnection().prepareStatement("INSERT INTO questions (text, category) VALUES(?,?);");
            //pripravny prikaz, id vygeneruje sql samo, nasledne nastavime hodnoty otaznikov:
            p.setString(1, q.text);
            p.setString(2, q.category);
            int eU = p.executeUpdate(); //vracia pocet uspesnych vlozeni
            if (eU != 1) return ERROR;  //vracia true co znamena, ze sa vlozila jedna otazka
            ResultSet keys = p.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            } else return ERROR;

        } catch (SQLException e) {
            e.printStackTrace();
            return ERROR;
        }
//TODO: vymenit minus jedna za error
    }
    public ArrayList<Question> getQuestions() {
        try {
            Statement statement = Database.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM questions");
            //bodkociarku doplni statement, Query davame ked chceme dopytovat z databazy
            ArrayList<Question> questions = new ArrayList<>();
            while (rs.next()) {  //prechadza vysledkami dopytu a kazdy vysledok nacita do otazky
                Question q = new Question();
                q.id = rs.getInt("id");
                q.text = rs.getString("text");
                q.category = rs.getString("category");
                questions.add(q); //otazku pridame do arraylistu
            }
            rs.close();

            return questions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }
    public Question getQuestion(int id) {
        try {
            PreparedStatement p = Database.getInstance().getConnection().prepareStatement("SELECT * FROM questions WHERE id = ?;");
            p.setInt(1, id);

            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                Question q = new Question();
                q.id = rs.getInt("id");
                q.text = rs.getString("text");
                q.category = rs.getString("category");
                q.answers = AnswerRepository.getInstance().getAnswers(id);
                rs.close();
                return q;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
