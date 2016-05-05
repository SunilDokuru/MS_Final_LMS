/**
 *
 * @author Dokuru
 */

package com.template.spring.service;

import com.template.spring.domain.Grades;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public interface GradingService {
    public List<Grades> getGrades (LinkedHashMap<String, String> courseDetails, String userID);
    public List<Grades> getAllGrades (LinkedHashMap<String, String> courseDetails);
    
    public HashMap<Integer, String> getStudentNames (List<Integer> studentID);
    public int updateScores(List<Grades> fromForm);
    public int insertNewGrades(List<Grades> newGrades);
    
    public List<Integer> getStudentIDList(String courseID, String facultyID);
}
