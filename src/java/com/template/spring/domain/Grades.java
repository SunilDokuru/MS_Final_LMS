/**
 *
 * @author Dokuru
 */

package com.template.spring.domain;


public class Grades {
    private String testName;
    private Integer facultyID, studentID, courseID, maxScore;
    private Double score, weightage;
    
    public Grades() {    
    }
    public Grades(String testName, Integer facultyID, Integer studentID, Integer courseID, Double score, Double weightage, Integer maxScore) {
        this.testName = testName;
        this.facultyID = facultyID;
        this.studentID = studentID;
        this.courseID = courseID;
        this.score = score;
        this.weightage = weightage;
        this.maxScore = maxScore;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
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

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getWeightage() {
        return weightage;
    }

    public void setWeightage(Double weightage) {
        this.weightage = weightage;
    }
    
    
}
