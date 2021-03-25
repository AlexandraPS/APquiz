package sk.plajdickova.apquiz.data;

import java.sql.*;
import java.util.ArrayList;

public class Database {

    private Connection c;

    public Database() {
    }

    public boolean connect() {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:apquiz.db");
            Statement s = c.createStatement();
            s.executeUpdate("CREATE TABLE IF NOT EXISTS questions(id INTEGER PRIMARY KEY, text TEXT, answerId INTEGER);");
            s.executeUpdate("CREATE TABLE IF NOT EXISTS answers(id INTEGER PRIMARY KEY, text TEXT, questionId INTEGER);");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean addQuestion(Question q) {
        try {
            PreparedStatement p = c.prepareStatement("INSERT INTO questions (text, answerId) VALUES(?,?);");
            //pripravny prikaz, id vygeneruje sql samo, nasledne nastavime hodnoty otaznikov:
            p.setString(1, q.text);
            p.setInt(2, q.answerId);
            int eU = p.executeUpdate(); //vracia pocet uspesnych vlozeni
            return eU == 1;  //vracia true co znamena, ze sa vlozila jedna otazka

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public ArrayList<Question> getQuestions() {
        try {
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM questions");
            //bodkociarku doplni statement, Query davame ked chceme dopytovat z databazy
            ArrayList<Question> questions = new ArrayList<>();
            while(rs.next()) {  //prechadza vysledkami dopytu a kazdy vysledok nacita do otazky
                Question q = new Question();
                q.id = rs.getInt("id");
                q.text = rs.getString("text");
                q.answerId = rs.getInt("answerId");
                questions.add(q); //otazku pridame do arraylistu
            }
            rs.close();

            return questions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }
//spravit funkciu addAnswer,getAnswers
}
