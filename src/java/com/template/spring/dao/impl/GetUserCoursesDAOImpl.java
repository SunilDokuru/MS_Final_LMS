/**
 *
 * @author Dokuru
 */
package com.template.spring.dao.impl;

import com.template.spring.dao.GetUserCoursesDAO;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GetUserCoursesDAOImpl implements GetUserCoursesDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public List<LinkedHashMap> getUserCourses(String userID, String tableName) {
        String sql;
      
        List<LinkedHashMap> result = new ArrayList<>();
        
        
        switch (tableName) {
            case "Registration":
                
                sql = "select courseID from MS_Final." + tableName + " where StudentID = '" + userID + "';";
                List<String> courseID  = getJdbcTemplate().query(sql, (ResultSet rs, int rowNum) -> rs.getString("courseID"));
                
                if(!courseID.isEmpty())
                    for (String course : courseID) {
                        sql = "select courseID, courseName, courseLables, TotalWeeks, MeetingDays, StartDate, Status from MS_Final.Courses where courseID = " + Integer.parseInt(course) + ";";
                        //details.put(course, executeQuery(sql));
                        result.add(executeQuery(sql));
                }
                break;
            case "Courses":
                sql = "select courseID from MS_Final." + tableName + " where FacultyID = '" + userID + "';";
                courseID  = getJdbcTemplate().query(sql, (ResultSet rs, int rowNum) -> rs.getString("courseID"));
                
                if(!courseID.isEmpty())
                    for (String course : courseID) {
                        sql = "select courseID, courseName, courseLables, TotalWeeks, MeetingDays, StartDate, Status from MS_Final.Courses where courseID = '" + course + "';";
                        //details.put(course, executeQuery(sql));
                        result.add(executeQuery(sql));
                }
                break;
        }
        
        return result;
    }

    private LinkedHashMap<String, Object> executeQuery(String sql) {
        List results = getJdbcTemplate().queryForList(sql);
        LinkedHashMap<String, Object> details = new LinkedHashMap<>();
        
        for (Object result : results) {
            LinkedHashMap map = (LinkedHashMap) result;
            map.keySet().stream().forEach((key) -> {
                details.put(key + "", map.get(key));
            });
        }
        return details;
    }

    @Override
    public HashMap<String, String> getCourseContent(String courseID) {
        String sql = "select * from MS_Final.courseContent where courseID = " + Integer.parseInt(courseID) + ";";
        HashMap<String, String> send = new HashMap<>();
        
        List results = getJdbcTemplate().queryForList(sql);
        
        for (Object result : results) {
            LinkedHashMap map = (LinkedHashMap) result;
            map.keySet().stream().forEach((key) -> {
                send.put(key + "", map.get(key)+"");
            });
        }
        
        //System.out.println("Values for Course Content" + send.toString());
    return send;
    }
    
}
