/**
 *
 * @author Dokuru
 */
package com.template.spring.service.impl;

import com.template.spring.dao.AdminAttendanceDAO;
import com.template.spring.service.AttendanceServiceAdmin;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceServiceAdminImpl implements AttendanceServiceAdmin {
    @Autowired
    private AdminAttendanceDAO adminAttendanceDAO;

    public AdminAttendanceDAO getAdminAttendanceDAO() {
        return adminAttendanceDAO;
    }

    public void setAdminAttendanceDAO(AdminAttendanceDAO adminAttendanceDAO) {
        this.adminAttendanceDAO = adminAttendanceDAO;
    }
    
    @Override
    public int getCurrentWeek(LinkedHashMap<String, String> courseDetails) {
       return  adminAttendanceDAO.getCurrentWeek(courseDetails);
    }

    @Override
    public List<Integer> getStudentID(String courseID) {
        return adminAttendanceDAO.getStudentID(courseID);
    }

    @Override
    public String getAttendanceForPreviousWeeks(String date, String courseID, Integer facultyID, Integer studentID) {
        return adminAttendanceDAO.getAttendanceForPreviousWeeks(date, courseID, facultyID, studentID);
    }

    @Override
    public int insertNewRecord(int currentWeek, HashMap<String, String> fromForm, LinkedHashMap<String, String> courseDetails, String date, LinkedHashMap<String, String> dates) {
        return adminAttendanceDAO.insertNewRecord(currentWeek, fromForm, courseDetails, date, dates);
    }

    @Override
    public int updateRecord(HashMap<String, String> fromForm, LinkedHashMap<String, String> courseDetails, String date) {
        return adminAttendanceDAO.updateRecord(fromForm, courseDetails, date);
    }
}
