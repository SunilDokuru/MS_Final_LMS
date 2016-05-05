/**
 *
 * @author Dokuru
 */

package com.template.spring.domain;

import java.sql.Time;

public class Schedule2 {
    private String date, dayOfWeek;
    private Time startTime, endTime;
    
    public Schedule2() {
        
    }
    public Schedule2(String date, String dayOfWeek, Time startTime, Time endTime) {
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
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
