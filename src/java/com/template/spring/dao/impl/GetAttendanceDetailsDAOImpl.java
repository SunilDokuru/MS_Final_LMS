/**
 *
 * @author Dokuru
 */

package com.template.spring.dao.impl;

import com.template.spring.dao.GetAttendanceDetailsDAO;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
public class GetAttendanceDetailsDAOImpl implements GetAttendanceDetailsDAO{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<LinkedHashMap> getAttendanceDetails(HashMap<String, String> courseRequested, String user_type) {
        
        List<LinkedHashMap> result = new ArrayList<>();
        
        String courseID = courseRequested.get("courseID");
        String userID = courseRequested.get("userID");
        String studentID = courseRequested.get("studentID");
        
        String sql;
        switch (user_type) {
            case "admin":
                sql = "select courseID, facultyID, studentID, totalWeeks from MS_Final.attendance where courseID = " + courseID + " and facultyID = " + userID + ";";
                result.add(executeQuery(sql));
                break;
            case "student":
                sql = "select courseID, facultyID, studentID, totalWeeks from MS_Final.attendance where courseID = " + courseID + " and studentID = " + studentID + ";";
                result.add(executeQuery(sql));
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
    public int getCourseTotalWeeks(String courseID) {
        String sql = "select totalweeks from MS_Final.courses where courseID = " + courseID;
        List<Integer> weeks  = getJdbcTemplate().query(sql, (ResultSet rs, int rowNum) -> rs.getInt("TotalWeeks"));
        
        if(!weeks.isEmpty())
            return weeks.get(0);
        else
            return 0;
    }

    @Override
    public LinkedHashMap<String, String> getCourseDetails(String courseID, String facultyID, String user_type) {
        String sql;
        
        if(user_type.equals("admin"))
            sql = "select courseID, facultyID, courseName, courseLables, meetingDays, startDate, endDate, totalWeeks from MS_Final.Courses where courseID = " + Integer.parseInt(courseID) + " and facultyID = " + Integer.parseInt(facultyID) + ";";
        else {
            Integer faculty;
            sql = "select facultyID from MS_Final.registration where studentID = " + Integer.parseInt(facultyID) + " and courseID = " + Integer.parseInt(courseID) + ";";
            List<Integer> result = getJdbcTemplate().query(sql, (ResultSet rs, int row) -> rs.getInt("facultyID"));
            
            faculty = result.get(0);
            if(faculty == null || faculty == 0) return null;
            else    sql = "select courseID, facultyID, courseName, courseLables, meetingDays, startDate, endDate, totalWeeks from MS_Final.Courses where courseID = " + Integer.parseInt(courseID) + " and facultyID = " + faculty + ";";
            
        }
            
        LinkedHashMap<String, String> details = new LinkedHashMap<>();
        
        List results = getJdbcTemplate().queryForList(sql);
        
        for (Object result : results) {
            LinkedHashMap map = (LinkedHashMap) result;
            map.keySet().stream().forEach((key) -> {
                details.put(key+"", map.get(key)+"");
            });
        }
        return details;
        
    }

    @Override
    public LinkedHashMap<String, String> getAttendanceData(HashMap<String, String> courseDetails, List<String> scheduledDates) {
        LinkedHashMap<String, String> result = new LinkedHashMap<>();
        
        for (String scheduledDate : scheduledDates) {
            String query = "select userResponse from MS_Final.attendances where courseID = " + Integer.parseInt(courseDetails.get("courseID")) + " and facultyID = '" + courseDetails.get("facultyID") + "' and studentID = '" + courseDetails.get("studentID") + "' and date = '" + scheduledDate + "';";
            List<String> list  = getJdbcTemplate().query(query, (ResultSet rs, int rowNum) -> rs.getString("userResponse")+"");
            
            query = "select notes from MS_Final.attendances where courseID = " + Integer.parseInt(courseDetails.get("courseID")) + " and facultyID = '" + courseDetails.get("facultyID") + "' and studentID = '" + courseDetails.get("studentID") + "' and date = '" + scheduledDate + "';";
            List<String> notes  = getJdbcTemplate().query(query, (ResultSet rs, int rowNum) -> rs.getString("notes")+"");
            
            if (list.isEmpty() || list.size() <= 0) {
                result.put(scheduledDate, "none:No Comment");
            } else {
                
                String note = notes.get(0);
                if(note == null || "".equals(note))  note = "No Comment";
                
                String temp = list.get(0) + ":" + note;
                result.put(scheduledDate, temp);
            }
        }
        
    //System.out.println("Attendance Data: \t" + result.toString() + "\n");
        return result;
    }

    @Override
    public String getUserResponseForDate(String date, LinkedHashMap<String, String> courseDetails) {
        String result;
        String query = "select userResponse from MS_Final.attendances where courseID = " + Integer.parseInt(courseDetails.get("courseID")) + " and facultyID = '" + courseDetails.get("facultyID") + "' and studentID = '" + courseDetails.get("studentID") + "' and date = " + date + " and courseID = " + Integer.parseInt(courseDetails.get("courseID")) + ";";
        List<String> attendance  = getJdbcTemplate().query(query, (ResultSet rs, int rowNum) -> rs.getString("userResponse")+"");
        
        if (attendance.isEmpty() || attendance.size() <= 0) {
                result = "none";
            } else {
                result = attendance.get(0);
            }
        
        //System.out.println("Value of Result indivdual = " + result + "\n");
        return result;
    }

    @Override
    public int getPresentWeek(LinkedHashMap<String, String> courseDetails) {
        String query = "select presentWeek from MS_Final.attendances where facultyID = " + Integer.parseInt(courseDetails.get("facultyID")) +" and studentID = " + Integer.parseInt(courseDetails.get("studentID")) + " and courseID = " + Integer.parseInt(courseDetails.get("courseID")) + ";";
        
        /* Week contains multiple values of the present week for the given facultyID*/
        List<Integer> week = getJdbcTemplate().query(query, (ResultSet rs, int rowNum) -> rs.getInt("presentWeek"));
        
        /* Sort the list in descending order or ascending order to get the final update by the faculty which gives
         *   you the correct present week value.
         * For this we are using collections sort method.
            
                Collections.sort(week, new Comparator<Integer>() {

                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2.compareTo(o1);
                }
            
            });
         */
        
        Collections.sort(week, (Integer o1, Integer o2) -> o2.compareTo(o1));
        
        if(week.isEmpty() || week.get(0) == 0)  return 0;
        else    return week.get(0);
        /*System.out.println("Value of Present Week: " + week.toString());
        
        if(week.isEmpty() || week.get(0) == 0)  return null;
        else {
            query = "select date from MS_Final.attendances where presentWeek = " + week.get(0) + " and facultyID = " + Integer.parseInt(courseDetails.get("facultyID")) +" and studentID = " + Integer.parseInt(courseDetails.get("studentID")) + ";";
            List<String> date = getJdbcTemplate().query(query, (ResultSet rs, int rowNum) -> rs.getString("date"));
            
            return date.get(0);
        }*/
        
    }
    
    @Override
    public String getPresentWeekDate(LinkedHashMap<String, String> courseDetails) {
        String query = "select presentWeek from MS_Final.attendances where facultyID = " + Integer.parseInt(courseDetails.get("facultyID")) +" and studentID = " + Integer.parseInt(courseDetails.get("studentID")) + " and courseID = " + Integer.parseInt(courseDetails.get("courseID")) + ";";
        
        /* Week contains multiple values of the present week for the given facultyID*/
        List<Integer> week = getJdbcTemplate().query(query, (ResultSet rs, int rowNum) -> rs.getInt("presentWeek"));
        
        /* Sort the list in descending order or ascending order to get the final update by the faculty which gives
         *   you the correct present week value.
         * For this we are using collections sort method.
            
                Collections.sort(week, new Comparator<Integer>() {

                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2.compareTo(o1);
                }
            
            });
         */
        
        Collections.sort(week, (Integer o1, Integer o2) -> o2.compareTo(o1));
        
       
        if(week.isEmpty() || week.get(0) == 0)  return null;
        else {
            query = "select date from MS_Final.attendances where presentWeek = " + week.get(0) + " and facultyID = " + Integer.parseInt(courseDetails.get("facultyID")) +" and studentID = " + Integer.parseInt(courseDetails.get("studentID")) + " and courseID = " + Integer.parseInt(courseDetails.get("courseID")) + ";";
            List<String> date = getJdbcTemplate().query(query, (ResultSet rs, int rowNum) -> rs.getString("date"));
            
            return date.get(0);
        }
    }
    @Override
    public boolean validateBeforeValues(LinkedHashMap<String, String> courseDetails, LinkedHashMap<String, String> beforePresentDate) {
        Set set = beforePresentDate.entrySet();
        Iterator i = set.iterator();
        
        while(i.hasNext()) {
            Map.Entry entry = (Map.Entry)i.next();
            String query = "select userResponse from MS_Final.attendances where date = '" + entry.getKey() + "' and facultyID = " + Integer.parseInt(courseDetails.get("facultyID")) +" and studentID = " + Integer.parseInt(courseDetails.get("studentID")) + " and courseID = " + Integer.parseInt(courseDetails.get("courseID")) + ";";
            List<String> attendance = getJdbcTemplate().query(query, (ResultSet rs, int rowNum) -> rs.getString("userResponse"));
            
            if(!attendance.isEmpty()) {
                if(!attendance.get(0).equals(entry.getValue()))
                    return false;
            }
        }
        return true;
    }

    @Override
    public int updateAttendances(LinkedHashMap<String, String> courseDetails, LinkedHashMap<String, String> afterPresentDate, LinkedHashMap<String, String> dates) {
        int courseID = Integer.parseInt(courseDetails.get("courseID"));
        int facultyID = Integer.parseInt(courseDetails.get("facultyID"));
        int studentID = Integer.parseInt(courseDetails.get("studentID"));
        
        
        int update = 0;
        Set set = afterPresentDate.entrySet();
        Iterator i = set.iterator();
        
        while(i.hasNext()) {
            Map.Entry entry = (Map.Entry)i.next();
            String date1 = entry.getKey()+"";
            String attend = entry.getValue()+"";
            
            String query = "select userResponse from MS_Final.attendances where courseID = " + courseID + " and facultyID = " + facultyID + " and studentID = " + studentID + " and date = '" + date1 + "';";
            List<String> attendance = getJdbcTemplate().query(query, (ResultSet rs, int rowNum) -> rs.getString("userResponse"));
                
            if(!attendance.isEmpty()) {
                query = "update MS_Final.attendances set userResponse = '" + attend + "' "
                            + "where courseID = " + courseID + " and studentID = " + studentID + " and facultyID = " + facultyID + " and "
                            + "date = '" + date1 + "';";
                
                getJdbcTemplate().update(query);
                //getJdbcTemplate().update(query, new Object[]{studentID, facultyID, courseID});
                update++;
            }
            else {
                if(!attend.equals("none")) {
                    query = "insert into MS_Final.attendances (courseID, facultyID, studentID, date, day, userResponse) values(?,?,?,?,?,?);";
                    jdbcTemplate.update(query, new Object[]{courseID, facultyID, studentID, date1, dates.get(date1), attend});
                    update++;
                }
            }
        }
        
      return update;  
    }

    @Override
    public int updateNotes(LinkedHashMap<String, String> courseDetails, LinkedHashMap<String, String> notes, LinkedHashMap<String, String> dates) {
        int courseID = Integer.parseInt(courseDetails.get("courseID"));
        int facultyID = Integer.parseInt(courseDetails.get("facultyID"));
        int studentID = Integer.parseInt(courseDetails.get("studentID"));
        
        
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        Date currentDate = new Date();
        String cDate = sdf.format(currentDate);
        
        
        int update = 0;   
        Set set = notes.entrySet();
        
        Iterator it = set.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String date = entry.getKey()+"";
            String note = entry.getValue()+"";
            
            if(!note.equalsIgnoreCase("No Comments")) {
                if(note.matches("^[0-9]{2}\\/[0-9]{2} ?-? ?:?[a-zA-Z 0-9./$%!@#^&*()-]*$")){}
                else    note = cDate +"- " + note;
            }
            
            String query = "select userResponse from MS_Final.attendances where courseID = " + courseID + " and facultyID = " + facultyID + " and studentID = " + studentID + " and date = '" + date + "';";
            List<String> attendance = getJdbcTemplate().query(query, (ResultSet rs, int rowNum) -> rs.getString("userResponse"));
            
            if(!attendance.isEmpty()) {
                query = "update MS_Final.attendances set notes = ? where courseID = ? and facultyID = ? and studentID = ? and date = ?;";
                getJdbcTemplate().update(query, new Object[]{note, courseID, facultyID, studentID, date});
                
                update++;
            }
        }
        
        return update;
    }
    
}
