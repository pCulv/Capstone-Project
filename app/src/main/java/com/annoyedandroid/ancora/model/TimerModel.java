package com.annoyedandroid.ancora.model;


public class TimerModel {

    private String timerName;
    private Integer hour;
    private Integer minute;
    private Integer second;

    public TimerModel() {}

    public TimerModel(String timerName) {
        this.timerName = timerName;
    }

    public TimerModel(String timerName, Integer hour, Integer minute, Integer second) {
        this.timerName = timerName;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public String getTimerName() {
        return timerName;
    }

    public void setTimerName(String timerName) {
        this.timerName = timerName;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }
}
