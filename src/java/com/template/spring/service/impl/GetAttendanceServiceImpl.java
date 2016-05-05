/**
 *
 * @author Dokuru
 */

package com.template.spring.service.impl;

import com.template.spring.dao.GetAttendanceDetailsDAO;
import com.template.spring.service.GetAttendanceService;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetAttendanceServiceImpl implements GetAttendanceService {

    @Autowired
    private GetAttendanceDetailsDAO getAttendanceDetailsDAO;

    public GetAttendanceDetailsDAO getGetAttendanceDetailsDAO() {
        return getAttendanceDetailsDAO;
    }

    public void setGetAttendanceDetailsDAO(GetAttendanceDetailsDAO getAttendanceDetailsDAO) {
        this.getAttendanceDetailsDAO = getAttendanceDetailsDAO;
    }
    
    @Override
    public List<LinkedHashMap> getAttendanceDetails(HashMap<String, String> courseRequested, String user_type) {
        return getAttendanceDetailsDAO.getAttendanceDetails(courseRequested, user_type);
    }

    @Override
    public int getCourseTotalWeeks(String courseID) {
        return getAttendanceDetailsDAO.getCourseTotalWeeks(courseID);
    }

    @Override
    public LinkedHashMap<String, String> getCourseDetails(String courseID, String facultyID, String user_type) {
        return getAttendanceDetailsDAO.getCourseDetails(courseID, facultyID, user_type);
    }

    @Override
    public LinkedHashMap<String, String> getAttendanceData(HashMap<String, String> courseDetails, List<String> scheduledDates) {
        return getAttendanceDetailsDAO.getAttendanceData(courseDetails, scheduledDates);
    }

    @Override
    public String getUserResponseForDate(String date, LinkedHashMap<String, String> courseDetails) {
        return getAttendanceDetailsDAO.getUserResponseForDate(date, courseDetails);
    }

    @Override
    public int getPresentWeek(LinkedHashMap<String, String> courseDetails) {
        return getAttendanceDetailsDAO.getPresentWeek(courseDetails);
    }
    
    @Override
    public boolean validateBeforeValues(LinkedHashMap<String, String> courseDetails, LinkedHashMap<String, String> beforePresentDate) {
        return getAttendanceDetailsDAO.validateBeforeValues(courseDetails, beforePresentDate);
    }

    @Override
    public int updateAttendances(LinkedHashMap<String, String> courseDetails, LinkedHashMap<String, String> beforePresentDate, LinkedHashMap<String, String> dates) {
        return getAttendanceDetailsDAO.updateAttendances(courseDetails, beforePresentDate, dates);
    }

    @Override
    public String getPresentWeekDate(LinkedHashMap<String, String> courseDetails) {
        return getAttendanceDetailsDAO.getPresentWeekDate(courseDetails);
    }

    @Override
    public int updateNotes(LinkedHashMap<String, String> courseDetails, LinkedHashMap<String, String> notes, LinkedHashMap<String, String> dates) {
        return getAttendanceDetailsDAO.updateNotes(courseDetails, notes, dates);
    }
    
}
