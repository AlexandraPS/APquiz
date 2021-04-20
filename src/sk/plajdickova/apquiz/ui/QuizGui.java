package sk.plajdickova.apquiz.ui;

import sk.plajdickova.apquiz.data.Database;
import sk.plajdickova.apquiz.data.Question;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class QuizGui extends JFrame {

    public QuizGui() {

       /* boolean ok = Database.getInstance().connect();
        System.out.println(ok);
        ok = Database.getInstance().addQuestion(new Question(0, "Nový t_p auta ", "Slovenský jazyk"));
        System.out.println(ok);
        ok = Database.getInstance().addQuestion(new Question(0, "Drevená g_tara ", "Slovenský jazyk"));
        System.out.println(ok);
        ArrayList<Question> questions = Database.getInstance().getQuestions();
        for (Question q : questions) {
            System.out.println(q.id + " " + q.text + " " + q.category);
        }*/
        setTitle("APquiz");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setupMenu();




        setVisible(true);
    }
    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuAdmin = new JMenu("Admin");
        JMenuItem itemLogin = new JMenuItem("Login");
        itemLogin.addActionListener(e -> showLogin());

        menuAdmin.add(itemLogin);
        JMenuItem itemQuestions = new JMenuItem("Otázky");
        menuAdmin.add(itemQuestions);
        menuBar.add(menuAdmin);
        setJMenuBar(menuBar);
    }
    private void showLogin() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Zadajte heslo: ");
        JPasswordField field = new JPasswordField(10);
        panel.add(label);
        panel.add(field);
        String [] options = {"OK","ZRUŠIŤ"};
        int option = JOptionPane.showOptionDialog(this,panel,
                "Login",JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE,null,options,options[1]);
        if (option == 0) {
            field.getPassword();
        }
    }

}
