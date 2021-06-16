package sk.plajdickova.apquiz;

import sk.plajdickova.apquiz.data.entity.Result;
import sk.plajdickova.apquiz.data.entity.Test;
import sk.plajdickova.apquiz.data.repository.Database;
import sk.plajdickova.apquiz.view.test.TestView;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TestController {

    private static final long DELAY = 1000;
    private final TestView view;
    private final Test test;
    private final Timer timer;
    private long time = 0;
    private boolean isRunning = true;
    private int position = 0;
    private final Date date;
    private int points = 0;

    public TestController(TestView view, Test test) {
        this.view = view;
        this.test = test;
        view.setController(this);
        view.showQuestion(test.questions.get(position), position, test.questions.size());
        timer = new Timer();
        timer.schedule(task, 0, 1000);
        date = new Date(System.currentTimeMillis());
    }

    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if (isRunning) {
                view.updateTime(time);
                time++;
            }
        }
    };

    public void pauseTest() {
        isRunning = false;
        view.showPause();
        isRunning = true;
    }

    public void nextQuestion(boolean[] checkedAnswers) {
        Boolean[] correctAnswers = new Boolean[checkedAnswers.length];
        for (int i = 0; i < checkedAnswers.length; i++) {
            boolean correct = test.questions.get(position).answers.get(i).isCorrect;
            if (!correct && !checkedAnswers[i]) correctAnswers[i] = null;
            else {
                correctAnswers[i] = correct && checkedAnswers[i];
                if(correctAnswers[i]) points += 2;
                else points -= 1;
            }
        }
        view.showCorrectAnswers(correctAnswers);
        view.setButtonsEnabled(false);
        isRunning = false;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                isRunning = true;
                view.setButtonsEnabled(true);
                position++;
                if (position == test.questions.size()) {
                    endTest();
                } else {
                    view.showQuestion(test.questions.get(position), position, test.questions.size());
                }
            }
        }, DELAY);
    }

    private void endTest() {
        timer.cancel();
        Database.getInstance().addResult(new Result(date, time, test.id, points));
        view.showEndDialog();
        Container parent = view.getParent();
        parent.removeAll();
        parent.validate();
        parent.repaint();
    }

}
