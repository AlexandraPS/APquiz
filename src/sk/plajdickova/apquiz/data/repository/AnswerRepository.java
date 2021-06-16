package sk.plajdickova.apquiz.data.repository;

import sk.plajdickova.apquiz.data.entity.Answer;

import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AnswerRepository {

    private static AnswerRepository instance;

    public static AnswerRepository getInstance() {
        if (instance == null) {
            instance = new AnswerRepository();
        }
        return instance;
    }

    private AnswerRepository() {
    }
    public boolean addAnswer(Answer a) {
        try {
            PreparedStatement p = Database.getInstance().getConnection().prepareStatement("INSERT INTO answers (text, questionId, isCorrect) VALUES(?,?,?);");
            //pripravny prikaz, id vygeneruje sql samo, nasledne nastavime hodnoty otaznikov:
            p.setString(1, a.text);
            p.setInt(2, a.questionId);
            p.setBoolean(3, a.isCorrect);
            int eU = p.executeUpdate(); //vracia pocet uspesnych vlozeni
            return eU == 1;  //vracia true co znamena, ze sa vlozila jedna odpoved

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public ArrayList<Answer> getAnswers() {
        try {
            Statement statement = Database.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM answers");
            //bodkociarku doplni statement, Query davame ked chceme dopytovat z databazy
            ArrayList<Answer> answers = new ArrayList<>();
            while (rs.next()) {  //prechadza vysledkami dopytu a kazdy vysledok nacita do otazky
                Answer a = new Answer();
                a.id = rs.getInt("id");
                a.text = rs.getString("text");
                a.questionId = rs.getInt("questionId");
                a.isCorrect = rs.getBoolean("isCorrect");
                answers.add(a); //odpoved pridame do arraylistu
            }
            rs.close();

            return answers;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }
    public ArrayList<Answer> getAnswers(int questionId) {
        try {
            PreparedStatement p = Database.getInstance().getConnection().prepareStatement("SELECT * FROM answers WHERE questionId = ?");
            p.setInt(1,questionId);
            ResultSet rs = p.executeQuery();
            //bodkociarku doplni statement, Query davame ked chceme dopytovat z databazy
            ArrayList<Answer> answers = new ArrayList<>();
            while (rs.next()) {  //prechadza vysledkami dopytu a kazdy vysledok nacita do otazky
                Answer a = new Answer();
                a.id = rs.getInt("id");
                a.text = rs.getString("text");
                a.questionId = rs.getInt("questionId");
                a.isCorrect = rs.getBoolean("isCorrect");
                answers.add(a); //odpoved pridame do arraylistu
            }
            rs.close();

            return answers;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }
}
