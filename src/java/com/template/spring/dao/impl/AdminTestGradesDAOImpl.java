/**
 *
 * @author Dokuru
 */

package com.template.spring.dao.impl;

import com.template.spring.dao.AdminTestGradesDAO;
import com.template.spring.domain.FinalGrade;
import com.template.spring.domain.Grades;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdminTestGradesDAOImpl implements AdminTestGradesDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public List<Grades> getTestGrades(String testName, Integer facultyID, Integer courseID) {
        List<Grades> resultList;
        
        String query;
        query = "select courseID, facultyID, studentID, testName, weightage, maxScore, "
                + "score from MS_Final.Tests where courseID = ? and facultyID = ? and testName = ?;";
        
        resultList = getJdbcTemplate().query(query, new Object[]{courseID, facultyID, testName}, (ResultSet rs, int row) -> {
           Grades grade = new Grades();
           
            grade.setCourseID(rs.getInt("courseID"));
            grade.setFacultyID(rs.getInt("facultyID"));
            grade.setStudentID(rs.getInt("studentID"));
            grade.setTestName(rs.getString("testName"));
            grade.setWeightage(rs.getDouble("weightage"));
            grade.setScore(rs.getDouble("score"));
            grade.setMaxScore(rs.getInt("maxScore"));
            
        return grade;
        });
        
        return resultList;
    }

    @Override
    public Double getTotalWeightage(Integer courseID, Integer facultyID) {
        
        String sql = "select sum(sumGrouped)*100 as totalWeightage from (select sum(weightage)/(count(testName)) as sumGrouped from tests where courseID = ? and facultyID = ? group by testName) T;";
        Double weightage;
        
        List<Double> weightTemp  = getJdbcTemplate().query(sql, new Object[]{courseID, facultyID}, (ResultSet rs, int rowNum) -> rs.getDouble("totalWeightage"));
        
        if(weightTemp.isEmpty())    return 0.0;
        else {
            try {
                if(weightTemp.get(0) == null) {
                }
            } catch(NullPointerException ex) {
                return 0.0;
            }
            weightage = weightTemp.get(0);
            return weightage;
        }
    }

    @Override
    public List<FinalGrade> getFinalGradesList(HashMap<String, String> courseDetails, Integer facultyID) {
        List<FinalGrade> resultList = new ArrayList<>();
        String query;
        Integer courseID = Integer.parseInt(courseDetails.get("courseID"));
        String courseName = courseDetails.get("courseName");
        
        query = "select count(*) from MS_Final.Tests where courseID = ? and facultyID = ?;";
        
        int count = getJdbcTemplate().queryForObject(query, new Object[]{courseID, facultyID}, Integer.class);
        
        if(count > 0) {
            query = "select StudentID, sum((weightage*score*100/maxScore))*100/"
                    + "(select totalWeightage from (select sum(sumGrouped)*100 as totalWeightage"
                    + " from (select sum(weightage)/(count(testName)) as sumGrouped from tests "
                    + "where courseID = ? and facultyID = ? group by testName) T) T1) as finalGrade "
                    + "from tests where courseID = ? and facultyID = ? "
                    + "group by StudentID;";
            
            resultList = getJdbcTemplate().query(query, new Object[]{courseID, facultyID, courseID, facultyID}, (ResultSet rs, int row) -> {
               FinalGrade fg = new FinalGrade();
               
               fg.setCourseID(courseID);
               fg.setCourseName(courseName);
               fg.setFacultyID(facultyID);
               
               Integer studentID = rs.getInt("studentID");
               fg.setStudentID(studentID);
               
               String studentName = getStudentName(studentID);
               fg.setStudentName(studentName);
               
               Double finalScore = rs.getDouble("finalGrade");
               finalScore = (int)(finalScore*100)/ 100.0;
               
               fg.setFinalScore(finalScore);
               
               String finalGrade;
               finalGrade = finalScore >= 90 ? "A" : finalScore >=80 && finalScore <90 ? "B" : finalScore >= 70 && finalScore < 80 ? "C" : finalScore >=60 && finalScore < 70 ? "D" : "F";
               fg.setGrade(finalGrade);
               
               return fg;
            });
            
            return resultList;
        } 
        
        return resultList;
    }
    
    private String getStudentName(Integer studentID) {
        String studentName = "";
        String query = "select firstName from MS_Final.users where userID = ?;";
        
        List<String> name = getJdbcTemplate().query(query, new Object[] {studentID}, (ResultSet rs, int row) -> {
            return rs.getString("firstName");
        });
        
        if(!name.isEmpty())
            studentName = name.get(0);
        
        return studentName;
    }
    
    @Override
    public int updateFinalGrade(List<FinalGrade> gradesList) {
        int update = 0;
        
        for(FinalGrade g: gradesList) {
            Integer courseID = g.getCourseID();
            Integer facultyID = g.getFacultyID();
            Integer studentID = g.getStudentID();
            
            char grade = g.getGrade().charAt(0);
            
            System.out.println(grade);
            String query = "update MS_Final.registration set grade = '" + g.getGrade().charAt(0) + "' where courseID = ? and facultyID = ? and studentID = ?";
            
            getJdbcTemplate().update(query, new Object[]{courseID, facultyID, studentID});
            
            update++;
        }
        return update;
    }

    @Override
    public List<FinalGrade> getStudentFinalGrades(Integer studentID) {
        List<FinalGrade> resultList;
        
        String query = "select courseID, facultyID, studentID, grade from MS_Final.registration where studentID = ?;";
        resultList = getJdbcTemplate().query(query, new Object[]{studentID}, (ResultSet rs, int row) -> {
           FinalGrade g = new FinalGrade();
           
           Integer courseID = rs.getInt("courseID");
           g.setCourseID(courseID);
           
           Integer facultyID = rs.getInt("facultyID");
           g.setFacultyID(facultyID);
           
           g.setStudentID(rs.getInt("studentID"));
           g.setGrade(rs.getString("grade"));
           
           String courseName = getCourseName(courseID, facultyID);
           g.setCourseName(courseName);
           g.setStudentName("");
           g.setFinalScore(0.00);
           
           return g;
        });
        return resultList;
    }
    private String getCourseName(Integer courseID, Integer facultyID) {
        String query = "select courseName from MS_Final.courses where courseID = ? and facultyID = ?;";
        
        List<String> result =  getJdbcTemplate().query(query, new Object[]{courseID, facultyID}, (ResultSet rs, int row) -> {
           return rs.getString("courseName");
        });
        
        return result.get(0);
    }
    
}
