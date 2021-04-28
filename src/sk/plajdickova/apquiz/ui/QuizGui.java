package sk.plajdickova.apquiz.ui;

import sk.plajdickova.apquiz.QuizController;
import sk.plajdickova.apquiz.data.Database;
import sk.plajdickova.apquiz.data.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static sk.plajdickova.apquiz.QuizController.ANSWERS_MAX;

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
        dialog.setSize(400, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridLayout(10, 1));
        JLabel l1 = new JLabel("Zadaj otázku");
        l1.setHorizontalAlignment(JLabel.CENTER);
        dialog.add(l1);
        JTextField fieldQuestion = new JTextField();
        dialog.add(fieldQuestion);
        JLabel l2 = new JLabel("Zadaj kategóriu");
        l2.setHorizontalAlignment(JLabel.CENTER);
        dialog.add(l2);
        String[] categories = {"SJL", "MAT", "PRV"};
        JComboBox<String> comboBoxCategory = new JComboBox<>(categories);
        dialog.add(comboBoxCategory);
        JLabel l3 = new JLabel("Zadaj odpoveď");
        l3.setHorizontalAlignment(JLabel.CENTER);
        dialog.add(l3);
        JTextField[] answerFields = new JTextField[ANSWERS_MAX];
        JCheckBox[] answerBoxes = new JCheckBox[ANSWERS_MAX];
        for (int i = 0; i < ANSWERS_MAX; i++) {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            answerFields[i] = new JTextField();
            panel.add(answerFields[i], BorderLayout.CENTER);
            answerBoxes[i] = new JCheckBox();
            panel.add(answerBoxes[i], BorderLayout.EAST);
            dialog.add(panel);
        }


        JButton buttonAdd = new JButton("Ulož");
        buttonAdd.addActionListener(e -> {
            String[] answers = new String[ANSWERS_MAX];
            boolean[] correct = new boolean[ANSWERS_MAX];
            for (int i = 0; i < ANSWERS_MAX; i++) {
                answers[i] = answerFields[i].getText();
                correct[i] = answerBoxes[i].isSelected();

            }
            boolean ok = controller.addQuestion(fieldQuestion.getText(),
                    (String) comboBoxCategory.getSelectedItem(),answers,correct);
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

    public void showConnectionError() {
        JOptionPane.showMessageDialog(this, "Nepodarilo sa pripojiť k databáze");
    }

}
