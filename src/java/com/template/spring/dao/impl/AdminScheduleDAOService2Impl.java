/**
 *
 * @author Dokuru
 */
package com.template.spring.dao.impl;

import com.template.spring.dao.AdminScheduleDAOService2;
import com.template.spring.domain.AdminSchedule2;
import com.template.spring.domain.AllSchedules;
import com.template.spring.domain.AppointmentInfo;
import com.template.spring.domain.Schedule2;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdminScheduleDAOService2Impl implements AdminScheduleDAOService2 {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public boolean checkForSlotClashes(AdminSchedule2 schedule, String facultyID) throws ParseException {
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date newDate = sdf.parse(schedule.getDate());
        
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        String sTime = schedule.getStartTime();
        String eTime = schedule.getEndTime();
        
        Time newStart = new java.sql.Time(formatter.parse(sTime).getTime());
        Time newEnd = new java.sql.Time(formatter.parse(eTime).getTime());
        
        
        String query = "select * from MS_Final.adminSchedule2 where facultyID = " + Integer.parseInt(facultyID) + ";";
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

                details.clear();
            }
        }
        return count == 0;
    }

    @Override
    public int insertWholeRecord(HashMap<String, String> userDetails, LinkedHashMap<String, String> courseDetails, List<Schedule2> adminSchedule, List<Schedule2> slotList) throws ParseException {
        int updates = 0;
        String query;
        Integer courseID = Integer.parseInt(courseDetails.get("courseID"));
        Integer facultyID = Integer.parseInt(userDetails.get("userID"));
            
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        
        for(Schedule2 s: adminSchedule) {
            Time startTime = s.getStartTime();
            Time endTime = s.getEndTime();  
            
            AdminSchedule2 admin = new AdminSchedule2(s.getDate(), startTime+"", endTime+"", "PM", "PM", "true", s.getDayOfWeek());

            boolean check = checkForSlotClashes(admin, userDetails.get("userID"));
            
            if(check) {
                query = "insert into MS_Final.adminSchedule2 (courseID, facultyID, date, startTime, endTime, startTimeOfDay, endTimeOfDay, booked, dayOfWeek) values (?,?,?,?,?,?,?,?,?);";
                
                getJdbcTemplate().update(query, new Object[] {courseID, facultyID, s.getDate(), startTime, endTime, "PM", "PM", "false", s.getDayOfWeek()});
                updates ++;
                
                for(Schedule2 slot: slotList) {
                  Date oDate = sdf.parse(s.getDate());
                  Date sDate = sdf.parse(slot.getDate());
                  
                  if(oDate.equals(sDate)) {
                      String query1 = "insert into MS_Final.adminSlotSchedule (courseID, facultyID, date, startTime, endTime, dayOfWeek, booked) values (?,?,?,?,?,?, ?);";
                      getJdbcTemplate().update(query1, new Object[] {courseID, facultyID, s.getDate(), slot.getStartTime(), slot.getEndTime(), slot.getDayOfWeek(), "false"});
                  }
                }
                
            }
        }
        
        return updates;
    }

    @Override
    public List<Schedule2> getAdminSchedule(String courseID, String facultyID) {
        List<Schedule2> resultList = new ArrayList<>();
        
        Integer course = Integer.parseInt(courseID);
        Integer faculty = Integer.parseInt(facultyID);
        
        String query = "select count(*) from MS_Final.adminSlotSchedule where courseID = ? and facultyID = ? and booked = ?";
        int records = getJdbcTemplate().queryForObject(query, new Object[] {course, faculty, "false"}, Integer.class);
        
        if(records > 0) {
            query = "select date, startTime, endTime, dayOfWeek, booked from MS_Final.adminSlotSchedule where courseID = ?  and facultyID = ? and booked = ? order by date ASC;";
            
            resultList = getJdbcTemplate().query(query, new Object[]{course, faculty, "false"}, (ResultSet rs, int i) -> {
                Schedule2 s = new Schedule2();
                    
                String booked = rs.getString("booked");
                if(booked.equals("false")) {
                    String date = rs.getString("date");
                    date = date.replaceAll("/", "-");
                    
                    s.setDate(date);
                    s.setDayOfWeek(rs.getString("dayOfWeek"));
                    s.setStartTime(rs.getTime("startTime"));
                    s.setEndTime(rs.getTime("endTime"));
                }
                return s;
            });
            
        return resultList;
        }
    return resultList;
    }

    @Override
    public int deleteScheduledSlot(String date, Time startTime, HashMap<String, String> courseDetails, HashMap<String, String> loggedUserDetails) {
        date = date.replaceAll("-", "/");
        
        String user_type, query;
        int changes = 0;
        
        Integer facultyID = Integer.parseInt(courseDetails.get("facultyID"));
        Integer courseID = Integer.parseInt(courseDetails.get("courseID"));
                
        user_type = loggedUserDetails.get("user_type");
        
        switch(user_type) {
            case "admin":
                query = "select count(*) from MS_Final.appointments where facultyID = ? and courseID = ? and date = ? and startTime = ?;";
                int count = getJdbcTemplate().queryForObject(query, new Object[] {facultyID, courseID, date, startTime}, Integer.class);
                
                if(count == 1) {
                    query = "delete from MS_Final.appointments where date = ? and startTime = ? and courseID = ? and facultyID = ?;";
                    getJdbcTemplate().update(query, new Object[]{date, startTime, courseID, facultyID});
                }
                
                query = "delete from MS_Final.adminSlotSchedule where date = ? and startTime = ? and facultyID = ? and courseID = ?";
                changes = getJdbcTemplate().update(query, new Object[]{date, startTime, facultyID, courseID});
                break;
            case "student":
                Integer studentID = Integer.parseInt(loggedUserDetails.get("userID"));
                
                query = "delete from MS_Final.appointments where date = ? and startTime = ? and studentID = ? and courseID = ?;";
                changes = getJdbcTemplate().update(query, new Object[]{date, startTime, studentID, courseID});
                
                if(changes == 1) {
                    query = "update MS_Final.adminSlotSchedule set booked = ? where courseID = ? and facultyID = ? and date = ? and startTime = ?;";
                    changes = getJdbcTemplate().update(query, new Object[]{"false", courseID, facultyID, date, startTime});
                }
        }
    return changes;
    }
    
    /* Write a suppliment method for delete where when a slot is deleted, it updates
       the value in adminSchedule2 table. or ignore 
    */

    @Override
    public int slotBooking(String date, Time startTime, Time endTime, HashMap<String, String> courseDetails, HashMap<String, String> loggedUserDetails) throws ParseException {
        String user_type = loggedUserDetails.get("user_type");
        int updates = 0;
        System.out.println(user_type);
        
        if(user_type.equals("student")) {
            Integer studentID = Integer.parseInt(loggedUserDetails.get("userID"));
            Integer courseID = Integer.parseInt(courseDetails.get("courseID"));
            Integer facultyID = Integer.parseInt(courseDetails.get("facultyID"));
            
            String dayOfWeek = getDay(date);
            date = date.replaceAll("-", "/");

            String query = "insert into MS_Final.appointments (courseID, facultyID, studentID, date, startTime, endTime, day) values (?,?,?,?,?,?,?);";
            updates = getJdbcTemplate().update(query, new Object[]{courseID, facultyID, studentID, date, startTime, endTime, dayOfWeek});
            
            if(updates == 1) {
                query = "update MS_Final.adminSlotSchedule set booked = ? where facultyID = ? and courseID = ? and date = ? and startTime = ? and endTime  = ?;";
                updates = getJdbcTemplate().update(query, new Object[]{"true", facultyID, courseID, date, startTime, endTime});
            }
            
            if(updates == 1) {
                
            } else {
                query = "delete from MS_Final.appointments where studentID = ? and courseID = ? and facultyID = ? and date = ? and startTime = ? and endTime = ?";
                getJdbcTemplate().update(query, new Object[]{studentID, courseID, facultyID, date, startTime, endTime});
            }
        }
        
        return updates;
    }
    
    public String getDay(String date) throws ParseException {
        
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date d = formatter.parse(date);
        
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        String day = "NAN";
        
        switch(dayOfWeek) {
                case 1:
                    day = "Sunday";
                    break;
                case 2:
                    day = "Monday";
                    break;
                case 3:
                    day = "Tuesday";
                    break;
                case 4:
                    day = "Wednesday";
                    break;
                case 5:
                    day = "Thursday";
                    break;
                case 6:
                    day = "Friday";
                    break;
                case 7:
                    day = "Saturday";
                    break;
        }
        return day;
    }

    // CourseWise Appointment Details
    
    @Override
    public <T> List<T> getAppointmentDetails(HashMap<String, String> loggedUserDetails, HashMap<String, String> courseDetails) {
        List<Schedule2> resultList;
        List<AppointmentInfo> adminResult;
        
        String user_type = loggedUserDetails.get("user_type");
        String query;
        Integer courseID = Integer.parseInt(courseDetails.get("courseID"));
        int records;
        
        switch(user_type) {
            case "admin":
                Integer facultyID = Integer.parseInt(loggedUserDetails.get("userID"));
                
                query = "select count(*) from MS_Final.appointments where facultyID = ? and courseID = ?;";
                records = getJdbcTemplate().queryForObject(query, new Object[] {facultyID, courseID}, Integer.class);
                
                if(records > 0) {
                    query = "select date, startTime, endTime, day, studentID from MS_Final.appointments where courseID = ?  and facultyID = ?";
            
                    adminResult = getJdbcTemplate().query(query, new Object[]{courseID, facultyID}, (ResultSet rs, int i) -> {
                    AppointmentInfo s = new AppointmentInfo();
                    
                    String date = rs.getString("date");
                    date = date.replaceAll("/", "-");
                    
                    Integer studentID = rs.getInt("studentID");
                    String studentName = getStudentName(studentID);
                    
                    s.setStudentName(studentName);
                    s.setDate(date);
                    s.setDayOfWeek(rs.getString("day"));
                    s.setStartTime(rs.getTime("startTime"));
                    s.setEndTime(rs.getTime("endTime"));
                return s;
            });
            
        return (List<T>)adminResult;
                }
                break;
            case "student":
                Integer studentID = Integer.parseInt(loggedUserDetails.get("userID"));
                
                query = "select count(*) from MS_Final.appointments where studentID = ? and courseID = ?;";
                records = getJdbcTemplate().queryForObject(query, new Object[] {studentID, courseID}, Integer.class);
                
                if(records > 0) {
                    query = "select date, startTime, endTime, day from MS_Final.appointments where courseID = ?  and studentID = ?";
            
                    resultList = getJdbcTemplate().query(query, new Object[]{courseID, studentID}, (ResultSet rs, int i) -> {
                    Schedule2 s = new Schedule2();
                    
                    String date = rs.getString("date");
                    date = date.replaceAll("/", "-");
                    
                    s.setDate(date);
                    s.setDayOfWeek(rs.getString("day"));
                    s.setStartTime(rs.getTime("startTime"));
                    s.setEndTime(rs.getTime("endTime"));
                return s;
                });
                    
        return (List<T>)resultList;
            }    
            break;
        }
        
        return null;
    }
    
    public String getStudentName(Integer studentID) {
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
    public int getCurrentNumAppointments(Date futureDate, HashMap<String, String> courseDetails, HashMap<String, String> loggedUserDetails) throws ParseException {
        int result = 0;
        Integer studentID = Integer.parseInt(loggedUserDetails.get("userID"));
        Integer courseID = Integer.parseInt(courseDetails.get("courseID"));
        Integer facultyID = Integer.parseInt(courseDetails.get("facultyID"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        
        String user_type = loggedUserDetails.get("user_type");
        
        if(user_type.equals("student")) {
            String query = "select date from MS_Final.appointments where studentID = ? and courseID = ? and facultyID = ?;";
            
            List<String> dates = getJdbcTemplate().query(query, new Object[]{studentID, courseID, facultyID}, (ResultSet rs, int row) -> {
               return rs.getString("date");
            });
            
            Date currentDate = new Date();
            
            if(!dates.isEmpty()) {
                for(String date: dates) {
                
                    Date dbDate = sdf.parse(date);
                
                    if((dbDate.equals(currentDate) || dbDate.after(currentDate)) && (dbDate.before(futureDate) || dbDate.equals(futureDate)))
                        result++;
                }
                return result;
            }
            
        }
        return 0;
    }

    @Override
    public List<AllSchedules> getAllScheduleList(String purpose, Integer facultyID) {
        List<AllSchedules> resultList = new ArrayList<>();
        String query; int count;
        
        switch(purpose) {
            case "schedule":
                query = "select count(*) from MS_Final.adminSlotSchedule where facultyID = ? and booked = ?";
                count = getJdbcTemplate().queryForObject(query, new Object[]{facultyID, "false"}, Integer.class);
                
                if(count > 0) {
                    query = "select distinct c.courseName, c.courseID, a.date, a.startTime, a.endTime, a.dayOfWeek from courses as c, adminSlotSchedule as a where c.courseID = a.courseID and a.facultyID = ?;";
                    
                    resultList = getJdbcTemplate().query(query, new Object[]{facultyID}, (ResultSet rs, int row) -> {
                       AllSchedules a = new AllSchedules();
                       a.setCourseName(rs.getString("courseName"));
                       a.setCourseID(rs.getInt("courseID"));
                       a.setDate(rs.getString("date"));
                       a.setStartTime((rs.getTime("startTime")));
                       a.setEndTime(rs.getTime("endTime"));
                       a.setDayOfWeek(rs.getString("dayOfWeek"));
                       return a;
                    });
                }
                break;
            case "appointments":
                query = "select count(*) from MS_Final.appointments where facultyID = ?";
                count = getJdbcTemplate().queryForObject(query, new Object[]{facultyID}, Integer.class);
                
                if(count > 0) {
                    query = "select distinct c.courseName, c.courseID, a.date, a.startTime, a.endTime, a.StudentID, a.day, b.firstName, b.lastName from courses as c, appointments as a, users as b where a.courseID = c.courseID and a.studentID = b.userID and a.facultyID = ?;";
                    
                    resultList = getJdbcTemplate().query(query, new Object[]{facultyID}, (ResultSet rs, int row) -> {
                       AllSchedules a = new AllSchedules();
                       a.setCourseName(rs.getString("courseName"));
                       a.setCourseID(rs.getInt("courseID"));
                       a.setDate(rs.getString("date"));
                       a.setDayOfWeek(rs.getString("day"));
                       a.setStartTime(rs.getTime("startTime"));
                       a.setEndTime((rs.getTime("endTime")));
                       a.setStudentID(rs.getInt("studentID"));
                       a.setFirstName(rs.getString("firstName"));
                       a.setLastName(rs.getString("lastName"));
                       
                       return a;
                    });
                }
                break;
        }
        
        return resultList;
    }
}
