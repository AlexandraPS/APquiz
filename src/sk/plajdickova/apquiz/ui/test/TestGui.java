package sk.plajdickova.apquiz.ui.test;

import sk.plajdickova.apquiz.TestController;
import sk.plajdickova.apquiz.data.Question;

import javax.swing.*;
import java.awt.*;

public class TestGui extends JPanel {

    private TestController controller;
    private JLabel labelQuestion;
    private JLabel labelTime;
    private JLabel labelCount;
    private JPanel panelAnswers;

    public void setController(TestController controller) {
        this.controller = controller;
    }

    public TestGui() {
        setLayout(new BorderLayout());
        JPanel panelTop = new JPanel(new BorderLayout());
        labelTime = new JLabel("OO:00");
        labelCount = new JLabel("1/6");
        panelTop.add(labelTime, BorderLayout.WEST);
        panelTop.add(labelCount, BorderLayout.EAST);
        add(panelTop, BorderLayout.NORTH);
        JPanel panelBottom = new JPanel(new BorderLayout());
        JButton buttonPause = new JButton("Pause");
        buttonPause.addActionListener(e -> {
        controller.pauseTest();
        });
        panelBottom.add(buttonPause,BorderLayout.WEST);
        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(e -> {

        });
        panelBottom.add(buttonCancel,BorderLayout.EAST);
        add(panelBottom,BorderLayout.SOUTH);
        JPanel panelCenter = new JPanel(new BorderLayout());
        labelQuestion = new JLabel("Lorem Ipsum", JLabel.CENTER);
        panelCenter.add(labelQuestion,BorderLayout.NORTH);
        add(panelCenter,BorderLayout.CENTER);
        panelAnswers = new JPanel();
        panelCenter.add(panelAnswers,BorderLayout.CENTER);
    }

    public void updateTime(long time) {
        labelTime.setText(String.format("%02d:%02d",time/60,time%60));
    }

    public void showPause() {
        JOptionPane.showMessageDialog(this,"Pokračovať v teste");
    }
    public void showQuestion(Question q) {
        labelQuestion.setText(q.text);
    }
}
//TODO: checkboxy pre odpovede, tlačidlo ďalšia otázka