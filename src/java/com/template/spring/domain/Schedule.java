/**
 *
 * @author Dokuru
 */

package com.template.spring.domain;

import java.sql.Time;

public class Schedule {
    private String date, startTimeOfDay, endTimeOfDay;
    private Time startTime, endTime;

    public Schedule() {
    }
    
    public Schedule(String date, Time startTime, Time endTime, String startTimeOfDay, String endTimeOfDay) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startTimeOfDay = startTimeOfDay;
        this.endTimeOfDay = endTimeOfDay;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTimeOfDay() {
        return startTimeOfDay;
    }

    public void setStartTimeOfDay(String startTimeOfDay) {
        this.startTimeOfDay = startTimeOfDay;
    }

    public String getEndTimeOfDay() {
        return endTimeOfDay;
    }

    public void setEndTimeOfDay(String endTimeOfDay) {
        this.endTimeOfDay = endTimeOfDay;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
}
