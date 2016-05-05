/**
 *
 * @author Dokuru
 */

package com.template.spring.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public interface AttendanceServiceAdmin {
    public int getCurrentWeek(LinkedHashMap<String, String> courseDetails);
    public List<Integer> getStudentID(String courseID);
    public String getAttendanceForPreviousWeeks(String date, String courseID, Integer facultyID, Integer studentID);
    
    /*Following are used in the Post Methods*/
    public int insertNewRecord(int currentWeek, HashMap<String, String> fromForm, LinkedHashMap<String, String> courseDetails, String date, LinkedHashMap<String, String> dates);
    public int updateRecord(HashMap<String, String> fromForm, LinkedHashMap<String, String> courseDetails, String date);
}
