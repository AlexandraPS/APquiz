package sk.plajdickova.apquiz;

import sk.plajdickova.apquiz.data.Database;
import sk.plajdickova.apquiz.data.Test;
import sk.plajdickova.apquiz.ui.main.QuizGui;
import sk.plajdickova.apquiz.ui.test.TestGui;

import java.util.Timer;
import java.util.TimerTask;

public class TestController {

    private TestGui ui;
    private Test test;
    private Timer timer;
    private long time = 0;
    private boolean isRunning = true;
    private int position = 0;

    public TestController(TestGui ui, Test test) {
        this.ui = ui;
        this.test = test;
        ui.setController(this);
        ui.showQuestion(test.questions.get(position));
        timer = new Timer();
        timer.schedule(task,0,1000);
    }

    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if(isRunning) {
                ui.updateTime(time);
                time++;
            }
        }
    };

    public void pauseTest() {
        isRunning = false;
        ui.showPause();
        isRunning = true;
    }
}
