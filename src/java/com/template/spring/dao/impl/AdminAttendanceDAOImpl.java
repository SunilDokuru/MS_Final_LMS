/**
 *
 * @author Dokuru
 */
package com.template.spring.dao.impl;

import com.template.spring.dao.AdminAttendanceDAO;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdminAttendanceDAOImpl implements AdminAttendanceDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public int getCurrentWeek(LinkedHashMap<String, String> courseDetails) {
        String query = "select presentWeek from MS_Final.attendances where facultyID = " + Integer.parseInt(courseDetails.get("facultyID")) +" and courseID = " + Integer.parseInt(courseDetails.get("courseID")) + ";";
        
        /* Week contains multiple values of the present week for the given facultyID and CourseID */
        List<Integer> week = getJdbcTemplate().query(query, (ResultSet rs, int rowNum) -> rs.getInt("presentWeek"));
        
        Collections.sort(week, (Integer o1, Integer o2) -> o2.compareTo(o1));
        
        if(week.isEmpty() || week.get(0) == 0)  return 0;
        else    return week.get(0);
    }

    @Override
    public List<Integer> getStudentID(String courseID) {
        
        
        Integer courseId = Integer.parseInt(courseID);
        String query = "select studentID from MS_Final.registration where courseID = " + courseId + ";";
        
        List<Integer> result = getJdbcTemplate().query(query, (ResultSet rs, int row) -> rs.getInt("studentID"));
        
        if(!result.isEmpty())   return result;
        else    return null;
    }

    @Override
    public String getAttendanceForPreviousWeeks(String date, String courseID, Integer facultyID, Integer studentID) {
        /* This returns a hash Map with <studentID, attendnace> for the Date given */
        LinkedHashMap<Integer, String> data = new LinkedHashMap<>();
        
        String query = "select userResponse from MS_Final.attendances where courseID = " +Integer.parseInt(courseID) + " and facultyID = " + facultyID + " and studentID = " + studentID + " and date = '" + date + "';";
        List<String> attend = getJdbcTemplate().query(query, (ResultSet rs, int row) -> rs.getString("userResponse"));
        
        query = "select notes from MS_Final.attendances where courseID = " +Integer.parseInt(courseID) + " and facultyID = " + facultyID + " and studentID = " + studentID + " and date = '" + date + "';";
        List<String> notes = getJdbcTemplate().query(query, (ResultSet rs, int row) -> rs.getString("notes"));
        
        
        if(attend.isEmpty())    return null;
        else {
            String note = notes.get(0);
            
            if("".equals(note) || note == null) note = "No Comments";
            return attend.get(0) + ":" + note;
        }
    }

    @Override
    public int insertNewRecord(int currentWeek, HashMap<String, String> fromForm, LinkedHashMap<String, String> courseDetails, String date, LinkedHashMap<String, String> dates) {
        int updates = 0;
        int courseID = Integer.parseInt(courseDetails.get("courseID"));
        int facultyID = Integer.parseInt(courseDetails.get("facultyID"));
        
        
        System.out.println(courseDetails.toString());
        Set set = fromForm.entrySet();
        Iterator it = set.iterator();
        
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            Integer studentID = Integer.parseInt(entry.getKey()+"");
            String attendance = entry.getValue()+"";
            
            String query = "select userResponse from MS_Final.attendances where courseID = " + courseID + " and facultyID = " + facultyID + " and studentID = " + studentID + " and date = '" + date + "';";
            List<String> result = getJdbcTemplate().query(query, (ResultSet rs, int rowNum) -> rs.getString("userResponse"));
            
            if(!result.isEmpty()) {
                // Logic for update
                query = "update MS_Final.attendances set presentWeek = " + (currentWeek + 1) + ", userResponse = '" + attendance + "' "
                            + "where courseID = " + courseID + " and studentID = " + studentID + " and facultyID = " + facultyID + " and "
                            + "date = '" + date + "';";
                getJdbcTemplate().update(query);
                updates++;
            }
            else {
                // Logic for Insert
                System.out.println(courseDetails.get("meetingDays").toUpperCase().charAt(0));
                query = "insert into MS_Final.attendances (courseID, facultyID, studentID, date, day, userResponse, presentWeek) values(?,?,?,?,?,?,?);";
                jdbcTemplate.update(query, new Object[]{courseID, facultyID, studentID, date, dates.get(date), attendance, (currentWeek + 1)});
                updates++;
            }
        }
        
        return updates;
    }

    @Override
    public int updateRecord(HashMap<String, String> fromForm, LinkedHashMap<String, String> courseDetails, String date) {
        int updates = 0;
        int courseID = Integer.parseInt(courseDetails.get("courseID"));
        int facultyID = Integer.parseInt(courseDetails.get("facultyID"));
        
        System.out.println(courseDetails.toString());
        Set set = fromForm.entrySet();
        Iterator it = set.iterator();
        
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            Integer studentID = Integer.parseInt(entry.getKey()+"");
            String attendance = entry.getValue()+"";
            
            String query = "select userResponse from MS_Final.attendances where courseID = " + courseID + " and facultyID = " + facultyID + " and studentID = " + studentID + " and date = '" + date + "';";
            List<String> result = getJdbcTemplate().query(query, (ResultSet rs, int rowNum) -> rs.getString("userResponse"));
            
            if(!result.isEmpty()) {
                // Logic for update
                query = "update MS_Final.attendances set userResponse = '" + attendance + "' "
                            + "where courseID = " + courseID + " and studentID = " + studentID + " and facultyID = " + facultyID + " and "
                            + "date = '" + date + "';";
                getJdbcTemplate().update(query);
                updates++;
            }
            else {
                return 0;
            }
        }
        return updates;
    }
    
}
