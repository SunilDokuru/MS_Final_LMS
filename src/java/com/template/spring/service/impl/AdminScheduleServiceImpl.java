/**
 *
 * @author Dokuru
 */

package com.template.spring.service.impl;

import com.template.spring.dao.AdminScheduleDAOService;
import com.template.spring.domain.AdminSchedule;
import com.template.spring.domain.Schedule;
import com.template.spring.service.AdminScheduleService;
import java.sql.Time;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminScheduleServiceImpl implements AdminScheduleService {

    @Autowired
    private AdminScheduleDAOService adminScheduleDAOService;

    public AdminScheduleDAOService getAdminScheduleDAOService() {
        return adminScheduleDAOService;
    }

    public void setAdminScheduleDAOService(AdminScheduleDAOService adminScheduleDAOService) {
        this.adminScheduleDAOService = adminScheduleDAOService;
    }
    
    
    @Override
    public int insertNewScheduleRecord(HashMap<String, String> userDetails, LinkedHashMap<String, String> courseDetails, List<Schedule> adminSchedule) throws ParseException {
        return adminScheduleDAOService.insertNewScheduleRecord(userDetails, courseDetails, adminSchedule);
    }

    @Override
    public boolean checkPreviousTimes(AdminSchedule schedule, String facultyID) throws ParseException {
        return adminScheduleDAOService.checkPreviousTimes(schedule, facultyID);
    }

    @Override
    public List<Schedule> getAdminSchedule(String courseID, String facultyID) {
        return adminScheduleDAOService.getAdminSchedule(courseID, facultyID);
    }

    @Override
    public int deleteScheduledSlot(String date, Time startTime, Integer facultyID, Integer courseID, String user_type) {
        return adminScheduleDAOService.deleteScheduledSlot(date, startTime, facultyID, courseID, user_type);
    }
    
}
