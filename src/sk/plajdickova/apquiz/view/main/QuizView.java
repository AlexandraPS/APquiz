package sk.plajdickova.apquiz.view.main;

import sk.plajdickova.apquiz.QuizController;
import sk.plajdickova.apquiz.data.entity.Question;
import sk.plajdickova.apquiz.data.entity.Test;
import sk.plajdickova.apquiz.view.test.TestView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;

import static sk.plajdickova.apquiz.QuizController.ANSWERS_MAX;

public class QuizView extends JFrame {
    public void setController(QuizController controller) {
        this.controller = controller;
    }

    private QuizController controller;
    private int i = 0;


    public QuizView() {

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
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setupMenu();


        setVisible(true);
    }

    public void showNoAdmin() {
        JOptionPane.showMessageDialog(this, "Admin nieje prihlásený");
    }

    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuAdmin = new JMenu("Admin");
        JMenuItem itemLogin = new JMenuItem("Login");
        itemLogin.addActionListener(e -> showLogin());

        menuAdmin.add(itemLogin);
        //pridanie kolonky "Pridaj otazku" do menu
        JMenuItem itemQuestions = new JMenuItem("Pridaj otázku");
        itemQuestions.addActionListener(e -> {
            if (controller.getIsAdmin()) {
                showNewQuestionDialog();
            } else JOptionPane.showMessageDialog(this, "Admin nieje prihlásený");
        });
        menuAdmin.add(itemQuestions);
        //pridanie kolonky "Pridaj test" do menu
        JMenuItem itemNewTest = new JMenuItem("Pridaj test");
        itemNewTest.addActionListener(e ->
            controller.tryToShowNewTestDialog());
        menuAdmin.add(itemNewTest);
        menuBar.add(menuAdmin);
        JMenu menuStudent = new JMenu("Študent");
        JMenuItem itemStartTest = new JMenuItem("Spusti test");
        itemStartTest.addActionListener(e -> controller.startNewTest());
        menuStudent.add(itemStartTest);
        JMenuItem itemTestResults = new JMenuItem("Výsledky testov");
        menuStudent.add(itemTestResults);
        menuBar.add(menuStudent);
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
                    (String) comboBoxCategory.getSelectedItem(), answers, correct);
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

    public void showNewTestDialog(ArrayList<Question> questions) {
        JDialog dialog = new JDialog();
        dialog.setSize(400, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        dialog.setTitle("Vytváranie nového testu");

        JTextField fieldTitle = new JTextField();
        dialog.add(fieldTitle, BorderLayout.NORTH);


        DefaultListModel<QuestionListItem> listModel = new DefaultListModel<>();
        for (Question q : questions) {
            listModel.addElement(new QuestionListItem(q));
        }
        HashSet<Question> selectedQuestions = new HashSet<>();
        JList<QuestionListItem> questionsList = new JList<>(listModel);
        questionsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        questionsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int index = questionsList.locationToIndex(e.getPoint());
                QuestionListItem item = listModel.getElementAt(index);
                item.isSelected = !item.isSelected;
                if (item.isSelected) selectedQuestions.add(item.q);
                else selectedQuestions.remove(item.q);
                questionsList.repaint(questionsList.getCellBounds(index, index));
                System.out.println(item);
            }
        });
        questionsList.setCellRenderer(new CheckboxListCellRenderer());
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(questionsList);
        dialog.add(scrollPane, BorderLayout.CENTER);

        JButton buttonAdd = new JButton("Ulož");
        buttonAdd.addActionListener(e -> {
            String title = fieldTitle.getText();
            if (title.isBlank()) {
                JOptionPane.showMessageDialog(this,"Vyplnte názov testu.");
                return;
            }
            controller.addTest(title, new ArrayList<>(selectedQuestions));
            dialog.dispose();
        });
        dialog.add(buttonAdd, BorderLayout.SOUTH);


        dialog.setVisible(true);
    }

    public void showNoTests() {
        JOptionPane.showMessageDialog(this,"Nie sú dostupné žiadne testy");
    }

    public void showTests(ArrayList<Test> tests) {
        JDialog dialog = new JDialog();
        dialog.setSize(350,400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        i = 0;
        JLabel label = new JLabel(tests.get(i).title + " " + (i + 1) + "/" + tests.size());
        label.setHorizontalAlignment(JLabel.CENTER);
        dialog.add(label, BorderLayout.NORTH);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,3));
        JLabel label1 = new JLabel();
        JButton left = new JButton("<");
        left.addActionListener(e -> {
            i--;
            if (i == -1) i = tests.size() - 1;
            label.setText(tests.get(i).title + " " + (i + 1) + "/" + tests.size());
            label1.setText("<html>Počet otázok: " + tests.get(i).questions.size() + "<br>Kategórie: " + tests.get(i).getCategories() + "</html>");
        });
        panel.add(left);
        JButton start = new JButton("START");
        start.addActionListener(e -> {
            controller.startTest(tests.get(i));
            dialog.dispose();
        });
        panel.add(start);
        JButton right = new JButton(">");
        right.addActionListener(e -> {
            i++;
            if (i == tests.size()) i = 0;
            label.setText(tests.get(i).title + " " + (i + 1) + "/" + tests.size());
            label1.setText("<html>Počet otázok: " + tests.get(i).questions.size() + "<br>Kategórie: " + tests.get(i).getCategories() + "</html>");
        });
        panel.add(right);
        dialog.add(panel,BorderLayout.SOUTH);

        label1.setText("<html>Počet otázok: " + tests.get(i).questions.size() + "<br>Kategórie: " + tests.get(i).getCategories() + "</html>");
        dialog.add(label1,BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    public void showTestGui(TestView ui) {
        getContentPane().removeAll();
        add(ui);
        validate();
    }



}
