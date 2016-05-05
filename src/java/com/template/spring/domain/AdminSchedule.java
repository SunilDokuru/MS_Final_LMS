/**
 *
 * @author Dokuru
 */

package com.template.spring.domain;

public class AdminSchedule {
    private String date, startTimeOfDay, endTimeOfDay;
    private String startTime, endTime;
    private String saveCurrent;

    public String getSaveCurrent() {
        return saveCurrent;
    }

    public void setSaveCurrent(String saveCurrent) {
        this.saveCurrent = saveCurrent;
    }
    
    
    public AdminSchedule() {
        
    }
    public AdminSchedule(String date, String startTime, String endTime, String startTimeOfDay, String endTimeOfDay, String saveCurrent) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startTimeOfDay = startTimeOfDay;
        this.endTimeOfDay = endTimeOfDay;
        this.saveCurrent = saveCurrent;
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
}
