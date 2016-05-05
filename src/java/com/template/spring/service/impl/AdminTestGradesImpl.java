/**
 *
 * @author Dokuru
 */

package com.template.spring.service.impl;

import com.template.spring.dao.AdminTestGradesDAO;
import com.template.spring.domain.FinalGrade;
import com.template.spring.domain.Grades;
import com.template.spring.service.AdminTestGrades;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminTestGradesImpl implements AdminTestGrades {

    @Autowired
    private AdminTestGradesDAO adminTestGradesDAO;

    public AdminTestGradesDAO getAdminTestGradesDAO() {
        return adminTestGradesDAO;
    }

    public void setAdminTestGradesDAO(AdminTestGradesDAO adminTestGradesDAO) {
        this.adminTestGradesDAO = adminTestGradesDAO;
    }
    
    @Override
    public List<Grades> getTestGrades(String testName, Integer facultyID, Integer courseID) {
        return adminTestGradesDAO.getTestGrades(testName, facultyID, courseID);
    }

    @Override
    public Double getTotalWeightage(Integer courseID, Integer facultyID) {
        return adminTestGradesDAO.getTotalWeightage(courseID, facultyID);
    }

    @Override
    public List<FinalGrade> getFinalGradesList(HashMap<String, String> courseDetails, Integer facultyID) {
        return adminTestGradesDAO.getFinalGradesList(courseDetails, facultyID);
    }

    @Override
    public int updateFinalGrade(List<FinalGrade> gradesList) {
        return adminTestGradesDAO.updateFinalGrade(gradesList);
    }

    @Override
    public List<FinalGrade> getStudentFinalGrades(Integer studentID) {
        return adminTestGradesDAO.getStudentFinalGrades(studentID);
    }
    
}
