package sk.plajdickova.apquiz.data.entity;

import java.sql.Date;

public class Result {
    public int id;
    public Date date;
    public long totalTime;
    public int testId;
    public int points;

    public Result() {

    }

    @Override
    public String toString() {
        return "id: " + id + " dátum:" + date + " celkový čas:" + totalTime + " id testu:" + testId + " body:" + points;
    }

    public Result(Date date, long totalTime, int testId, int points) {
        this.date = date;
        this.totalTime = totalTime;
        this.testId = testId;
        this.points = points;
    }
}
