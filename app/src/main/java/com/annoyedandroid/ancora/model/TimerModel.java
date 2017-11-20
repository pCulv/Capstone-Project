package com.annoyedandroid.ancora.model;


public class TimerModel {

    private String timerName;
    private long totalTime;

    public TimerModel() {}

    public TimerModel(String timerName) {
        this.timerName = timerName;
    }

    public TimerModel(String timerName, long totalTime) {
        this.timerName = timerName;
        this.totalTime = totalTime;
    }

    public String getTimerName() {
        return timerName;
    }

    public long getTotalTime() {
        return totalTime;
    }

}
