/**
 *
 * @author Dokuru
 */

package com.template.spring.dao;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public interface GetAttendanceDetailsDAO {
    public List<LinkedHashMap> getAttendanceDetails(HashMap<String, String> courseRequested, String user_type);
    public int getCourseTotalWeeks(String courseID);
    public LinkedHashMap<String, String> getCourseDetails(String courseID, String facultyID, String user_type);
    
    public LinkedHashMap<String, String> getAttendanceData(HashMap<String, String> courseDetails, List<String> scheduledDates);
    public String getUserResponseForDate(String date, LinkedHashMap<String, String> courseDetails);
    
    public int getPresentWeek(LinkedHashMap<String, String> courseDetails);
    public String getPresentWeekDate(LinkedHashMap<String, String> courseDetails);
    
    
    public boolean validateBeforeValues(LinkedHashMap<String, String> courseDetails, LinkedHashMap<String, String> beforePresentDate);
    public int updateAttendances(LinkedHashMap<String, String> courseDetails, LinkedHashMap<String, String> beforePresentDate, LinkedHashMap<String, String> dates);
    
    public int updateNotes(LinkedHashMap<String, String> courseDetails, LinkedHashMap<String, String> notes, LinkedHashMap<String, String> dates);
}
