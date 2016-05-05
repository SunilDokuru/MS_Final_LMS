/**
 *
 * @author Dokuru
 */

package com.template.spring.domain;

public class AdminSchedule2 {
    private String date, startTime, endTime, startTimeOfDay, endTimeOfDay;
    private String dayOfWeek, saveCurrent;

    public AdminSchedule2() {
    
    }
    public AdminSchedule2(String date, String startTime, String endTime, String startTimeOfDay, String endTimeOfDay, String saveCurrent, String dayOfWeek) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startTimeOfDay = startTimeOfDay;
        this.endTimeOfDay = endTimeOfDay;
        this.saveCurrent = saveCurrent;
        this.dayOfWeek = dayOfWeek;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getSaveCurrent() {
        return saveCurrent;
    }

    public void setSaveCurrent(String saveCurrent) {
        this.saveCurrent = saveCurrent;
    }
    
    
}
