/**
 *
 * @author Dokuru
 */

package com.template.spring.dao;

import com.template.spring.domain.FinalGrade;
import com.template.spring.domain.Grades;
import java.util.HashMap;
import java.util.List;

public interface AdminTestGradesDAO {
    public List<Grades> getTestGrades(String testName, Integer facultyID, Integer courseID);
    public Double getTotalWeightage(Integer courseID, Integer facultyID);
    
    public List<FinalGrade> getFinalGradesList(HashMap<String, String> courseDetails, Integer facultyID);
    public int updateFinalGrade(List<FinalGrade> gradesList);
    
    public List<FinalGrade> getStudentFinalGrades(Integer studentID);
}
