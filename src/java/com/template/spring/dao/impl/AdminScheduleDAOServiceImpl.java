/**
 *
 * @author Dokuru
 */
package com.template.spring.dao.impl;

import com.template.spring.dao.AdminScheduleDAOService;
import com.template.spring.domain.AdminSchedule;
import com.template.spring.domain.Schedule;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdminScheduleDAOServiceImpl implements AdminScheduleDAOService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public int insertNewScheduleRecord(HashMap<String, String> userDetails, LinkedHashMap<String, String> courseDetails, List<Schedule> adminSchedule) throws ParseException{
        int updates = 0;
        String query;
        Integer courseID = Integer.parseInt(courseDetails.get("courseID"));
        Integer facultyID = Integer.parseInt(userDetails.get("userID"));
            
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        
        for(Schedule s: adminSchedule) {
            //Date date = sdf.parse(s.getDate());
            Time startTime = s.getStartTime();
            Time endTime = s.getEndTime();
            
            /*Update is required here....... Before inserting call the below function for each date and if it returns true only then insert else skip*/
            AdminSchedule s1 = new AdminSchedule(s.getDate(), s.getStartTime()+"", s.getEndTime()+"", s.getStartTimeOfDay(), s.getEndTimeOfDay(), "Yes");
            
            boolean check = checkPreviousTimes(s1, userDetails.get("userID"));
            if(check) {
                query = "insert into MS_Final.adminSchedule (courseID, facultyID, date, startTime, endTime, startTimeOfDay, endTimeOfDay, booked) values (?,?,?,?,?,?,?,?);";
                getJdbcTemplate().update(query, new Object[] {courseID, facultyID, s.getDate(), startTime, endTime, s.getStartTimeOfDay(), s.getEndTimeOfDay(), "false"});
            
                updates ++;
            }
        }
        
        return updates;
    }

    @Override
    public boolean checkPreviousTimes(AdminSchedule schedule, String facultyID) throws ParseException {
        
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date newDate = sdf.parse(schedule.getDate());
        
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        String sTime = schedule.getStartTime();
        String eTime = schedule.getEndTime();
        
        System.out.println("Start Time from DAO layer:\t" + sTime + "\t End Time too\t" + eTime);
        
        Time newStart = new java.sql.Time(formatter.parse(sTime).getTime());
        Time newEnd = new java.sql.Time(formatter.parse(eTime).getTime());
        
        
        String query = "select * from MS_Final.adminSchedule where facultyID = " + Integer.parseInt(facultyID) + ";";
        List result = getJdbcTemplate().queryForList(query);
        HashMap<String, String> details = new HashMap<>();
        
       int count = 0; 
        if(result.size() > 0) {
            for(Object tuple: result) {
                LinkedHashMap map = (LinkedHashMap) tuple;
                map.keySet().stream().forEach((key) -> {
                   details.put(key+"", map.get(key)+"");
                });
                
                String startTime = details.get("startTime");
                Time start = new java.sql.Time(formatter.parse(startTime).getTime());
            
                String endTime = details.get("endTime");
                Time end = new java.sql.Time(formatter.parse(endTime).getTime());
            
                Date date = sdf.parse(details.get("date"));
                
                System.out.println("\n From DB:\t" + date + "\t" + start + "\t" + end + "\n");
                if(newStart.after(end)) {
                    
                }
                else if(
                        (newDate.equals(date)) && 
                        ((
                            (newStart.before(end) && newStart.after(start)) || 
                            (newStart.equals(end) && newStart.equals(start))) || 
                            (newEnd.after(start) || newEnd.equals(start))
                        )) {
                    count++;
                }
                
                
                /*
                
                */
                details.clear();
            }
        }
        return count == 0;
    }

    @Override
    public List<Schedule> getAdminSchedule(String courseID, String facultyID) {
        List<Schedule> resultList;
        
        Integer course = Integer.parseInt(courseID);
        Integer faculty = Integer.parseInt(facultyID);
        
        String query = "select count(*) from MS_Final.adminSchedule where courseID = ? and facultyID = ? and booked = ?";
        
        //int records = getJdbcTemplate().queryForInt(query, new Object[]{courseID, facultyID});
        
        int records = getJdbcTemplate().queryForObject(query, new Object[] {course, faculty, "false"}, Integer.class);
        
        if(records > 0) {
            query = "select date, startTime, endTime, startTimeOfDay, endTimeOfDay, booked from MS_Final.adminSchedule where courseID = ?  and facultyID = ? and booked = ? order by date ASC;";
            
            resultList = getJdbcTemplate().query(query, new Object[]{course, faculty, "false"}, (ResultSet rs, int i) -> {
                Schedule s = new Schedule();
                    
                String booked = rs.getString("booked");
                if(booked.equals("false")) {
                    //s.setKey(rs.getInt("scheduleID"));
                    String date = rs.getString("date");
                    date = date.replaceAll("/", "-");
                    
                    s.setDate(date);
                    s.setStartTimeOfDay(rs.getString("startTimeOfDay"));
                    s.setEndTimeOfDay(rs.getString("endTimeOfDay"));
                    s.setStartTime(rs.getTime("startTime"));
                    s.setEndTime(rs.getTime("endTime"));
                }
                return s;
            });
            
        return resultList;
        }
        
        return null;
    }

    // Admin Deleteting a scheduled slot
    @Override
    public int deleteScheduledSlot(String date, Time startTime, Integer facultyID, Integer courseID, String user_type) {
        date = date.replaceAll("-", "/");
        String query;
        
        int changes = 0;
        
        if(user_type.equals("admin")) {
            query = "delete from MS_Final.adminSchedule where date = ? and startTime = ? and facultyID = ? and courseID = ?";
            changes = getJdbcTemplate().update(query, new Object[]{date, startTime, facultyID, courseID});
        }
            
        return changes;
    }
    
    
}
