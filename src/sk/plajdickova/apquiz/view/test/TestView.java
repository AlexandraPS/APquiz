package sk.plajdickova.apquiz.view.test;

import sk.plajdickova.apquiz.TestController;
import sk.plajdickova.apquiz.data.entity.Answer;
import sk.plajdickova.apquiz.data.entity.Question;

import javax.swing.*;
import java.awt.*;

public class TestView extends JPanel {

    private TestController controller;
    private final JLabel labelQuestion;
    private final JLabel labelTime;
    private JLabel labelCount;
    private final JPanel panelAnswers;
    private final JButton buttonNext;
    private final JButton buttonPause;

    public void setController(TestController controller) {
        this.controller = controller;
    }

    public TestView() {
        setLayout(new BorderLayout());
        JPanel panelTop = new JPanel(new BorderLayout());
        labelTime = new JLabel("OO:00");
        labelCount = new JLabel("1/6");
        panelTop.add(labelTime, BorderLayout.WEST);
        panelTop.add(labelCount, BorderLayout.EAST);
        add(panelTop, BorderLayout.NORTH);
        JPanel panelBottom = new JPanel(new BorderLayout());
        buttonPause = new JButton("Pause");
        buttonPause.addActionListener(e -> {
            controller.pauseTest();
        });
        panelBottom.add(buttonPause, BorderLayout.WEST);
        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(e -> {

        });
        panelBottom.add(buttonCancel, BorderLayout.EAST);
        add(panelBottom, BorderLayout.SOUTH);
        JPanel panelCenter = new JPanel(new BorderLayout());
        labelQuestion = new JLabel("Lorem Ipsum", JLabel.CENTER);
        panelCenter.add(labelQuestion, BorderLayout.NORTH);
        add(panelCenter, BorderLayout.CENTER);
        panelAnswers = new JPanel();
        panelAnswers.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        panelAnswers.setLayout(new BoxLayout(panelAnswers, BoxLayout.Y_AXIS));
        panelCenter.add(panelAnswers, BorderLayout.CENTER);
        buttonNext = new JButton("Next question");
        buttonNext.addActionListener(e -> {
            boolean[] checkedAnswers = new boolean[panelAnswers.getComponentCount()];
            for (int i = 0; i < panelAnswers.getComponentCount(); i++) {
                JCheckBox checkBox = (JCheckBox) panelAnswers.getComponent(i);
                checkedAnswers[i] = checkBox.isSelected();
            }
            controller.nextQuestion(checkedAnswers);
        });
        panelCenter.add(buttonNext, BorderLayout.SOUTH);
    }

    public void showCorrectAnswers(Boolean[] correctAnswers) {
        for (int i = 0; i < panelAnswers.getComponentCount(); i++) {
            JCheckBox checkBox = (JCheckBox) panelAnswers.getComponent(i);
            if (correctAnswers[i] != null) {
                if (correctAnswers[i]) checkBox.setBackground(Color.GREEN);
                else checkBox.setBackground(Color.RED);
            }
        }
    }

    public void updateTime(long time) {
        labelTime.setText(String.format("%02d:%02d", time / 60, time % 60));
    }

    public void showPause() {
        JOptionPane.showMessageDialog(this, "Pokračovať v teste");
    }

    public void showQuestion(Question q, int position, int questionCount) {
        labelQuestion.setText(q.text);
        panelAnswers.removeAll();
        for (Answer a : q.answers) {
            JCheckBox checkBox = new JCheckBox(a.text);
            panelAnswers.add(checkBox);
        }
        panelAnswers.revalidate();
        panelAnswers.repaint();
        labelCount.setText((position + 1) + "/" + questionCount);
    }

    public void setButtonsEnabled(boolean isEnabled) {
        buttonNext.setEnabled(isEnabled);
        for (int i = 0; i < panelAnswers.getComponentCount(); i++) {
            JCheckBox checkBox = (JCheckBox) panelAnswers.getComponent(i);
            checkBox.setEnabled(isEnabled);
        }
        buttonPause.setEnabled(isEnabled);

    }
}
//TODO: casovac po zaklikani checkboxov(nemoznost zmeny odpovede)