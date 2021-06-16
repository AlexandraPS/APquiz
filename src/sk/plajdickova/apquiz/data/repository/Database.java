package sk.plajdickova.apquiz.data.repository;

import sk.plajdickova.apquiz.data.entity.Answer;
import sk.plajdickova.apquiz.data.entity.Question;
import sk.plajdickova.apquiz.data.entity.Result;
import sk.plajdickova.apquiz.data.entity.Test;

import java.sql.*;
import java.util.ArrayList;

public class Database {


    public Connection getConnection() {
        return connection;
    }

    private Connection connection;
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
            connection = DriverManager.getConnection("jdbc:sqlite:apquiz.db");
            if(connection == null) return false;
            Statement s = connection.createStatement();
            s.executeUpdate("CREATE TABLE IF NOT EXISTS questions(id INTEGER PRIMARY KEY, text TEXT, category TEXT);");
            s.executeUpdate("CREATE TABLE IF NOT EXISTS answers(id INTEGER PRIMARY KEY, text TEXT, questionId INTEGER, isCorrect INTEGER);");
            s.executeUpdate("CREATE TABLE IF NOT EXISTS tests(id INTEGER PRIMARY KEY, title TEXT);");
            s.executeUpdate("CREATE TABLE IF NOT EXISTS testQuestions(testId INTEGER, questionId INTEGER);");
            s.executeUpdate("CREATE TABLE IF NOT EXISTS results(id INTEGER PRIMARY KEY, date DATE, totalTime INTEGER, testId INTEGER," +
                    "points INTEGER)");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    /**
     * pridáva test do databázy
     *
     * @param t test, ktorý bude pridaný do databázy
     * @return true(v prípade úspešného vloženia do db)/ false (v prípade neúspešného vloženia do db)
     */
    public boolean addTest(Test t) {
        try {
            if (connection == null) return false;
            PreparedStatement p = connection.prepareStatement("INSERT INTO tests (title) VALUES(?);");
            //pripravny prikaz, id vygeneruje sql samo, nasledne nastavime hodnoty otaznikov:
            p.setString(1, t.title);
            if (p.executeUpdate() != 1) return false;  //ak pocet vlozeni nieje jedna, funkcia vrati false
            ResultSet keys = p.getGeneratedKeys();
            if (keys.next()) {
                int testId = keys.getInt(1);
                for (Question q : t.questions) {
                    addTestQuestion(testId, q.id);
                }
                return true;
            } else return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    private boolean addTestQuestion(int testId, int questionId) {
        try {
            if (connection == null) return false;
            PreparedStatement p = connection.prepareStatement("INSERT INTO testQuestions (testId, questionId) VALUES(?,?);");
            //pripravny prikaz, id vygeneruje sql samo, nasledne nastavime hodnoty otaznikov:
            p.setInt(1, testId);
            p.setInt(2, questionId);
            int eU = p.executeUpdate(); //vracia pocet uspesnych vlozeni
            return eU == 1;  //vracia true co znamena, ze sa vlozila jedna odpoved

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public ArrayList<Question> getTestQuestions(int testId) {
        try {
            if (connection == null) return null;
            PreparedStatement p = connection.prepareStatement("SELECT * FROM  testQuestions WHERE testId = ?;");

            p.setInt(1, testId);
            ResultSet rs = p.executeQuery();  //bodkociarku doplni statement, Query davame ked chceme dopytovat z databazy
            ArrayList<Question> questions = new ArrayList<>();
            while (rs.next()) {  //prechadza vysledkami dopytu a kazdy vysledok nacita do otazky
                Question q = QuestionRepository.getInstance().getQuestion(rs.getInt("questionId"));

                questions.add(q); //otazku pridame do arraylistu
            }
            rs.close();

            return questions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }


    public ArrayList<Test> getTests() {
        try {
            if (connection == null) return null;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM tests");
            //bodkociarku doplni statement, Query davame ked chceme dopytovat z databazy
            ArrayList<Test> tests = new ArrayList<>();
            while (rs.next()) {  //prechadza vysledkami dopytu a kazdy vysledok nacita do otazky
                Test t = new Test();
                t.id = rs.getInt("id");
                t.title = rs.getString("title");
                t.questions = getTestQuestions(t.id);
                tests.add(t); //test pridame do arraylistu
            }
            rs.close();


            return tests;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }
    public boolean addResult(Result result) {
        try {
            if (connection == null) return false;
            PreparedStatement p = connection.prepareStatement("INSERT INTO results (date, totalTime, testId, points) VALUES(?,?,?,?);");
            //pripravny prikaz, id vygeneruje sql samo, nasledne nastavime hodnoty otaznikov:
            p.setDate(1, result.date);
            p.setLong(2,result.totalTime);
            p.setInt(3, result.testId);
            p.setInt(4,result.points);
            int eU = p.executeUpdate(); //vracia pocet uspesnych vlozeni
            return eU == 1;  //vracia true co znamena, ze sa vlozila jedna odpoved

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    public ArrayList<Result> getResults() {
        try {
            if (connection == null) return null;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM results");
            //bodkociarku doplni statement, Query davame ked chceme dopytovat z databazy
            ArrayList<Result> results = new ArrayList<>();
            while (rs.next()) {
                Result r = new Result();
                r.id = rs.getInt("id");
                r.date = rs.getDate("date");
                r.totalTime = rs.getLong("totalTime");
                r.testId = rs.getInt("testId");
                r.points = rs.getInt("points");
                results.add(r);
            }
            rs.close();

            return results;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }

}
//TODO: rozdelit databazu + vytvorit result GUI