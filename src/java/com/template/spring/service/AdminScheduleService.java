/**
 *
 * @author Dokuru
 */

package com.template.spring.service;

import com.template.spring.domain.AdminSchedule;
import com.template.spring.domain.Schedule;
import java.sql.Time;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public interface AdminScheduleService {
    public int insertNewScheduleRecord(HashMap<String, String> userDetails, LinkedHashMap<String, String> courseDetails, List<Schedule> list) throws ParseException;
    
    public boolean checkPreviousTimes(AdminSchedule schedule, String facultyID) throws ParseException;

    public List<Schedule> getAdminSchedule(String courseID, String facultyID);

    public int deleteScheduledSlot(String date, Time startTime, Integer facultyID, Integer courseID, String user_type);
}
