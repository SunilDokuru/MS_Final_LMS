/**
 *
 * @author Dokuru
 */
package com.template.spring.service.impl;

import com.template.spring.dao.AdminScheduleDAOService2;
import com.template.spring.domain.AdminSchedule2;
import com.template.spring.domain.AllSchedules;
import com.template.spring.domain.Schedule2;
import com.template.spring.service.AdminScheduleService2;
import java.sql.Time;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminScheduleService2Impl implements AdminScheduleService2 {

    @Autowired
    private AdminScheduleDAOService2 adminScheduleDAOService;

    public AdminScheduleDAOService2 getAdminScheduleDAOService() {
        return adminScheduleDAOService;
    }

    public void setAdminScheduleDAOService(AdminScheduleDAOService2 adminScheduleDAOService) {
        this.adminScheduleDAOService = adminScheduleDAOService;
    }
    
    
    // Overriden Methods
    
    @Override
    public boolean checkForSlotClashes(AdminSchedule2 schedule, String facultyID) throws ParseException{
        return adminScheduleDAOService.checkForSlotClashes(schedule, facultyID);
    }

    @Override
    public int insertWholeRecord(HashMap<String, String> userDetails, LinkedHashMap<String, String> courseDetails, List<Schedule2> list, List<Schedule2> slotList) throws ParseException {
        return adminScheduleDAOService.insertWholeRecord(userDetails, courseDetails, list, slotList);
    }

    @Override
    public List<Schedule2> getAdminSchedule(String courseID, String facultyID) {
        return adminScheduleDAOService.getAdminSchedule(courseID, facultyID);
    }

    @Override
    public int deleteScheduledSlot(String date, Time startTime, HashMap<String, String> courseDetails, HashMap<String, String> loggedUserDetails) {
        return adminScheduleDAOService.deleteScheduledSlot(date, startTime, courseDetails, loggedUserDetails);
    }

    @Override
    public int slotBooking(String date, Time startTime, Time endTime, HashMap<String, String> courseDetails, HashMap<String, String> loggedUserDetails) throws ParseException {
        return adminScheduleDAOService.slotBooking(date, startTime, endTime, courseDetails, loggedUserDetails);
    }

    @Override
    public <T> List<T> getAppointmentDetails(HashMap<String, String> loggedUserDetails, HashMap<String, String> courseDetails) {
        return adminScheduleDAOService.getAppointmentDetails(loggedUserDetails, courseDetails);
    }

    @Override
    public int getCurrentNumAppointments(Date futureDate, HashMap<String, String> loggedUserDetails, HashMap<String, String> courseDetails) throws ParseException{
        return adminScheduleDAOService.getCurrentNumAppointments(futureDate, loggedUserDetails, courseDetails);
    }

    @Override
    public List<AllSchedules> getAllScheduleList(String purpose, Integer facultyID) {
        return adminScheduleDAOService.getAllScheduleList(purpose, facultyID);
    }
    
}
