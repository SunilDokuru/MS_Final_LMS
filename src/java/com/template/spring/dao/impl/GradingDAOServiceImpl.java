/**
 *
 * @author Dokuru
 */

/*if this does not work you can still use setting them into hashmap and setting the hashmap into List*/
package com.template.spring.dao.impl;

import com.template.spring.dao.GradingDAOService;
import com.template.spring.domain.Grades;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GradingDAOServiceImpl implements GradingDAOService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public List<Grades> getGrades(LinkedHashMap<String, String> courseDetails, String userID) {
        List<Grades> grades;
        
        String query = "select courseID, facultyID, studentID, testName, weightage, maxScore, "
                + "score from MS_Final.Tests where "
                + "courseID = " + Integer.parseInt(courseDetails.get("courseID")) + 
                " and facultyID = " + Integer.parseInt(courseDetails.get("facultyID")) + 
                " and studentID = " + Integer.parseInt(userID);
        
        grades = getJdbcTemplate().query(query, (ResultSet rs, int i) -> {
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
        
        return grades;
    }

    /* This is Used for Admin */
    @Override
    public List<Grades> getAllGrades(LinkedHashMap<String, String> courseDetails) {
        List<Grades> grades;
        
        String query;// = "select firstName, lastName from MS_Final.users where userID = " + Integer.parseInt(courseDetails.get("studentID")) + ";";
        
        
        query = "select courseID, facultyID, studentID, testName, weightage, maxScore, "
                + "score from MS_Final.Tests where "
                + "courseID = " + Integer.parseInt(courseDetails.get("courseID")) + 
                " and facultyID = " + Integer.parseInt(courseDetails.get("facultyID"));
        
        grades = getJdbcTemplate().query(query, (ResultSet rs, int i) -> {
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
        
        return grades;
    }

    @Override
    public HashMap<Integer, String> getStudentNames (List<Integer> studentIDList) {
        HashMap<Integer, String> result = new HashMap<>();
        String query, name;
        
        for(Integer studentID: studentIDList) {
            name = "";
            query = "select firstName, lastName from MS_Final.users where userID = " + studentID + ";";
            
            List results = getJdbcTemplate().queryForList(query);
            HashMap<String, String> details = new HashMap<>();
            
            for (Object tuple : results) {
                LinkedHashMap map = (LinkedHashMap) tuple;
                map.keySet().stream().forEach((key) -> {
                    details.put(key+"", map.get(key)+"");
                });
            }
            name += details.get("firstName") + " " + details.get("lastName");
            
            result.put(studentID, name);
        }
        
        return result;
    }

    @Override
    public int updateScores(List<Grades> fromForm) {
        int updates = 0;
        for (Grades g : fromForm) {
            
            Integer studentID = g.getStudentID();
            Integer facultyID = g.getFacultyID();
            String testName = g.getTestName();
            Double score = g.getScore();
            Double weightage = g.getWeightage();
            Integer courseID = g.getCourseID();
            
            weightage = weightage <= 1 ? weightage : weightage/100.0;
            
            String query = "select score from MS_Final.tests where testName = '" + testName + "' and "
                    + "studentID = " + studentID + " and facultyID = " + facultyID + " and courseID = " + courseID + ";";
            
            List<Double> scoreForTest = getJdbcTemplate().query(query, (ResultSet rs, int row) -> rs.getDouble("score"));
            
            if(scoreForTest.isEmpty()) {
                // This case does not happen
            }
            else {
                query = "update MS_Final.tests set score = " + score + ", weightage = " + weightage + " where facultyID = " +
                        facultyID + " and studentID = " + studentID + " and testName = '" + testName + "';";
                
                getJdbcTemplate().update(query);
                updates++;
            }
        }
        
        return updates;
    }

    @Override
    public int insertNewGrades(List<Grades> newGrades) {
        int updates = 0;
        String query;
        
        for (Grades g : newGrades) {
            query = "insert into MS_Final.tests (courseID, facultyID, testName, weightage, score, studentID, maxScore) values(?,?,?,?,?,?,?)";
            getJdbcTemplate().update(query, new Object[]{g.getCourseID(), g.getFacultyID(), g.getTestName(), g.getWeightage()/100.0, g.getScore(), g.getStudentID(), g.getMaxScore()});
            updates++;
        }
        
        return updates;
    }

    @Override
    public List<Integer> getStudentIDList(String courseID, String facultyID) {
        Integer course = Integer.parseInt(courseID);
        Integer faculty = Integer.parseInt(facultyID);
        
        String sql = "select studentID from MS_Final.registration where courseID = ? and facultyID = ?";
        
        List<Integer> studentList = getJdbcTemplate().query(sql, new Object[]{course, faculty}, (ResultSet rs, int row) -> rs.getInt("studentID")); 
        
        return studentList;
        
//        if(!studentList.isEmpty())
//            return studentList;
//        else    return null;
    }
    
}
