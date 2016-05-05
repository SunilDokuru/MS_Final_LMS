/**
 *
 * @author Dokuru
 */

package com.template.spring.service.impl;

import com.template.spring.dao.GradingDAOService;
import com.template.spring.domain.Grades;
import com.template.spring.service.GradingService;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GradingServiceImpl implements GradingService {

    @Autowired
    private GradingDAOService gradingDAOService;

    public GradingDAOService getGradingDAOService() {
        return gradingDAOService;
    }

    public void setGradingDAOService(GradingDAOService gradingDAOService) {
        this.gradingDAOService = gradingDAOService;
    }
    
    @Override
    public List<Grades> getGrades(LinkedHashMap<String, String> courseDetails, String userID) {
        return gradingDAOService.getGrades(courseDetails, userID);
    }

    @Override
    public List<Grades> getAllGrades(LinkedHashMap<String, String> courseDetails) {
        return gradingDAOService.getAllGrades(courseDetails);
    }

    @Override
    public HashMap<Integer, String> getStudentNames(List<Integer> studentID) {
        return gradingDAOService.getStudentNames(studentID);
    }
    
    @Override
    public int updateScores(List<Grades> fromForm) {
        return gradingDAOService.updateScores(fromForm);
    }

    @Override
    public int insertNewGrades(List<Grades> newGrades) {
        return gradingDAOService.insertNewGrades(newGrades);
    }

    @Override
    public List<Integer> getStudentIDList(String courseID, String facultyID) {
        return gradingDAOService.getStudentIDList(courseID, facultyID);
    }
    
}
