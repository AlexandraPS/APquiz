package sk.plajdickova.apquiz.ui;

import sk.plajdickova.apquiz.QuizController;
import sk.plajdickova.apquiz.data.Database;
import sk.plajdickova.apquiz.data.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class QuizGui extends JFrame {
    public void setController(QuizController controller) {
        this.controller = controller;
    }

    private QuizController controller;

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
        JMenuItem itemQuestions = new JMenuItem("Pridaj otázku");
        itemQuestions.addActionListener(e -> {
            if (controller.getIsAdmin()) {
                showNewQuestionDialog();
            } else JOptionPane.showMessageDialog(this, "Admin nieje prihlásený");
        });
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
        String[] options = {"OK", "ZRUŠIŤ"};
        int option = JOptionPane.showOptionDialog(this, panel,
                "Login", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (option == 0) {
            controller.login(String.valueOf(field.getPassword())); //getPassword vracia znakove pole - pretypovali sme
        }
    }

    private void showNewQuestionDialog() {
        JDialog dialog = new JDialog();
        dialog.setSize(200, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridLayout(5, 1));
        JLabel l1 = new JLabel("Zadaj otázku");
        l1.setHorizontalAlignment(JLabel.CENTER);
        dialog.add(l1);
        JTextField fieldQuestion = new JTextField();
        dialog.add(fieldQuestion);
        JLabel l2 = new JLabel("Zadaj kategóriu");
        l2.setHorizontalAlignment(JLabel.CENTER);
        dialog.add(l2);
        String [] categories = {"SJL","MAT","PRV"};
        JComboBox<String> comboBoxCategory = new JComboBox<>(categories);
        dialog.add(comboBoxCategory);
        JButton buttonAdd = new JButton("Ulož");
        buttonAdd.addActionListener(e -> {
            boolean ok = controller.addQuestion(fieldQuestion.getText(), (String) comboBoxCategory.getSelectedItem());
            dialog.dispose();
            if (ok) JOptionPane.showMessageDialog(this, "Otázka bola pridaná");
            else JOptionPane.showMessageDialog(this, "Otázka nebola pridaná");
        });
        dialog.add(buttonAdd);


        dialog.setVisible(true);
    }

    public void showLoginStatus(boolean ok) {
        if (ok) {
            JOptionPane.showMessageDialog(this, "Prihlásenie bolo úspešné");
        } else JOptionPane.showMessageDialog(this, "Zadali ste zlé heslo");
    }

}
