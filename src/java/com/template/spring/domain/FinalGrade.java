/**
 *
 * @author Dokuru
 */

package com.template.spring.domain;

public class FinalGrade {
    private Integer studentID, courseID, facultyID;
    private Double finalScore;
    private String studentName, courseName, grade;

    public FinalGrade() {
        
    }
    public FinalGrade(Integer studentID, Integer facultyID, Integer courseID, Double finalScore, String studentName, String courseName, String grade) {
        this.studentID = studentID;
        this.courseID = courseID;
        this.facultyID = facultyID;
        this.finalScore = finalScore;
        this.studentName = studentName;
        this.courseName = courseName;
        this.grade = grade;
    }

    public Integer getFacultyID() {
        return facultyID;
    }

    public void setFacultyID(Integer facultyID) {
        this.facultyID = facultyID;
    }
    
    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

    public Integer getCourseID() {
        return courseID;
    }

    public void setCourseID(Integer courseID) {
        this.courseID = courseID;
    }

    public Double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(Double finalScore) {
        this.finalScore = finalScore;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
    
    
}
