/**
 *
 * @author Dokuru
 */

package com.template.spring.domain;

import java.sql.Time;

public class AppointmentInfo {
    private String studentName, date, dayOfWeek;
    private Time startTime, endTime;

    public AppointmentInfo() {
        
    }
    public AppointmentInfo(String studentName, String date, String dayOfWeek, Time startTime, Time endTime) {
        this.studentName = studentName;
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
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
