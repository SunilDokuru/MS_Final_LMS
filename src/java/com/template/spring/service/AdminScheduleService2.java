/**
 *
 * @author Dokuru
 */

package com.template.spring.service;

import com.template.spring.domain.AdminSchedule2;
import com.template.spring.domain.AllSchedules;
import com.template.spring.domain.Schedule2;
import java.sql.Time;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author Dokuru
 */
public interface AdminScheduleService2 {
    public boolean checkForSlotClashes(AdminSchedule2 schedule, String facultyID) throws ParseException;
    
    public int insertWholeRecord(HashMap<String, String> userDetails, LinkedHashMap<String, String> courseDetails, List<Schedule2> list, List<Schedule2> slotList) throws ParseException;
    
    public List<Schedule2> getAdminSchedule(String courseID, String facultyID);
    
    public int deleteScheduledSlot(String date, Time startTime, HashMap<String, String> courseDetails, HashMap<String, String> loggedUserDetails);
    
    public int slotBooking(String date, Time startTime, Time endTime, HashMap<String, String> courseDetails, HashMap<String, String> loggedUserDetails) throws ParseException;
    
    public <T> List<T> getAppointmentDetails(HashMap<String, String> loggedUserDetails, HashMap<String, String> courseDetails);
    
    public int getCurrentNumAppointments(Date futureDate, HashMap<String, String> loggedUserDetails, HashMap<String, String> courseDetails) throws ParseException;
    
    public List<AllSchedules> getAllScheduleList(String purpose, Integer facultyID);
}
