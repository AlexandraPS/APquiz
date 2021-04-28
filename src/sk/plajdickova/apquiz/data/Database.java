package sk.plajdickova.apquiz.data;

import java.sql.*;
import java.util.ArrayList;

public class Database {

    private Connection c;
    private static Database instance; //singleton (vytvorenie len jednej instancie)

    private Database() {
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public boolean connect() {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:apquiz.db");
            Statement s = c.createStatement();
            s.executeUpdate("CREATE TABLE IF NOT EXISTS questions(id INTEGER PRIMARY KEY, text TEXT, category TEXT);");
            s.executeUpdate("CREATE TABLE IF NOT EXISTS answers(id INTEGER PRIMARY KEY, text TEXT, questionId INTEGER, isCorrect INTEGER);");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public int addQuestion(Question q) {
        try {
            if (c == null) return -1;
            PreparedStatement p = c.prepareStatement("INSERT INTO questions (text, category) VALUES(?,?);");
            //pripravny prikaz, id vygeneruje sql samo, nasledne nastavime hodnoty otaznikov:
            p.setString(1, q.text);
            p.setString(2, q.category);
            int eU = p.executeUpdate(); //vracia pocet uspesnych vlozeni
            if(eU !=1) return -1;  //vracia true co znamena, ze sa vlozila jedna otazka
            ResultSet keys = p.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            } else return -1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

    }

    public ArrayList<Question> getQuestions() {
        try {
            if (c == null) return null;
            Statement statement = c.createStatement();
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

    public boolean addAnswer(Answer a) {
        try {
            if (c == null) return false;
            PreparedStatement p = c.prepareStatement("INSERT INTO answers (text, questionId, isCorrect) VALUES(?,?,?);");
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
            if (c == null) return null;
            Statement statement = c.createStatement();
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

}
