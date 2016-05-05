package com.template.spring.domain;

public class Attendance {
    private String studentID, facultyID, date, day;
    private String attendance, notes;

    public Attendance() {
        
    }
    
    public Attendance(String studentID, String facultyID, String date, String day, String attendance, String notes) {
        this.studentID = studentID;
        this.facultyID = facultyID;
        this.date = date;
        this.day = day;
        this.attendance = attendance;
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getFacultyID() {
        return facultyID;
    }

    public void setFacultyID(String facultyID) {
        this.facultyID = facultyID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }
}
