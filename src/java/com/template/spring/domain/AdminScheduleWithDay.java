/**
 *
 * @author Dokuru
 */

package com.template.spring.domain;

public class AdminScheduleWithDay {
    
    private String date;
    private String startTime, endTime, day;

    public AdminScheduleWithDay() {
        
    }
    
    public AdminScheduleWithDay (String date, String startTime, String endTime, String day) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
