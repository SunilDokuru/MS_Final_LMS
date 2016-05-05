/**
 *
 * @author Dokuru
 */

package com.template.spring.controllers;

import com.template.spring.domain.Grades;
import com.template.spring.domain.GradesList;
import com.template.spring.service.AdminTestGrades;
import com.template.spring.service.GradingService;
import com.template.spring.validators.GradesValidator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AddGrades {
    
    @Autowired
    private GradingService gradingService;

    public GradingService getGradingService() {
        return gradingService;
    }

    public void setGradingService(GradingService gradingService) {
        this.gradingService = gradingService;
    }
    
    @Autowired
    private GradesValidator gradesValidator;

    public GradesValidator getGradesValidator() {
        return gradesValidator;
    }

    public void setGradesValidator(GradesValidator gradesValidator) {
        this.gradesValidator = gradesValidator;
    }
    
    @Autowired
    private AdminTestGrades adminTestGrades;

    public AdminTestGrades getAdminTestGrades() {
        return adminTestGrades;
    }

    public void setAdminTestGrades(AdminTestGrades adminTestGrades) {
        this.adminTestGrades = adminTestGrades;
    }
    
    
    @RequestMapping(value="/addGrades.htm", method=RequestMethod.GET)
    public String getAllTestScores(ModelMap model, HttpSession session, RedirectAttributes attributes) {
        
        if(session.getAttribute("user") != null) {
            HashMap<String, String> userDetails = (HashMap<String, String>) session.getAttribute("user");
            String user_type = userDetails.get("user_type");
            
            String returnForm;
            if(user_type.equals("admin")) {
                LinkedHashMap<String, String> courseDetails = (LinkedHashMap<String, String>) session.getAttribute("courseDetails");
                /*Get all the students List from the registration if there the allGrades List populates empty*/
                List<Integer> studentIDList = gradingService.getStudentIDList(courseDetails.get("courseID"), courseDetails.get("facultyID"));
                Double totalWeightage = adminTestGrades.getTotalWeightage(Integer.parseInt(courseDetails.get("courseID")), Integer.parseInt(userDetails.get("userID")));
                
                try {
                    if(studentIDList == null) {
                        attributes.addFlashAttribute("errormessage", "No Users Registered In the Class");
                        returnForm = "redirect:/grades.htm";
                    } else if(totalWeightage == 100) {
                        attributes.addFlashAttribute("errormessage", "Total Weightage of all exams is hundred.<br />Please delete some tests or update weightages for previous tests to <br />upload more scores");
                        returnForm = "redirect:/grades.htm";
                    }
                    else {
                        HashMap<Integer, String> studentNames = gradingService.getStudentNames(studentIDList);
                        
                        totalWeightage = (int)(totalWeightage*100)/100.0;
                        session.setAttribute("totalWeightage", totalWeightage);
                        
                        session.setAttribute("studentNamesList", studentNames);
                
                        List<Grades> data = new ArrayList<>();
                
                        List<Grades> allGrades = gradingService.getAllGrades(courseDetails);
                        GradesList list = new GradesList();
                
                        if(allGrades.isEmpty()) {
                            for (Integer studentIDList1 : studentIDList) {
                                    Integer facultyID = Integer.parseInt(courseDetails.get("facultyID"));
                                    Integer studentID = studentIDList1;
                                    Integer courseID = Integer.parseInt(courseDetails.get("courseID"));
                                    data.add(new Grades("", facultyID, studentID, courseID, 0.0, 0.0, 0));
                            }
                            session.setAttribute("listSize", studentIDList.size());
                        }
                
                        else {
                            List<Integer> students = new ArrayList<>();

                            for (Grades g : allGrades) {
                                if (!students.contains(g.getStudentID())) {
                                    students.add(g.getStudentID());

                                    data.add(new Grades(g.getTestName(), g.getFacultyID(), g.getStudentID(), g.getCourseID(), g.getScore(), g.getWeightage(), g.getMaxScore()));
                                }
                                session.setAttribute("listSize", students.size());
                            }
                        }
                
                        list.setGradesList(data);
                        model.addAttribute("addGrades", list);
                        returnForm = "addGrades";
                    }
                } catch(NullPointerException ex) {
                    attributes.addFlashAttribute("errormessage", "No Users Registered In the Class");
                    return "redirect:/grades.htm";
                }
                
              return returnForm;  
            }
            else {
                attributes.addFlashAttribute("errormessage", "Unauthorized Access to Content");
                return "redirect:/logout.htm";
            }
        }
        else {
            attributes.addFlashAttribute("errormessage", "Login To Access Requested Page");
            return "redirect:/loginPage.htm";
        }
   }
   
   @RequestMapping(value="/addGrades.htm", method=RequestMethod.POST)
   public String addNewGradeRecord(@ModelAttribute("addGrades")GradesList gradesList, HttpSession session, RedirectAttributes attributes, BindingResult bindResults) {
       
        gradesValidator.validate(gradesList, bindResults);
       
        if(bindResults.hasErrors()) {
            //attributes.addFlashAttribute("flashMessage", "Check your Values");
            return "addGrades";
        }
       
        List<Grades> newGrades = gradesList.getGradesList();
       
       
        int updates = gradingService.insertNewGrades(newGrades);
       
        if(updates > 0)
            attributes.addFlashAttribute("flashMessage", "Grades Added. Do you want to add more grades?");
        else
            attributes.addFlashAttribute("flashMessage", "There was an error trying to add grades. Please Try Again");
       return "redirect:/addGrades.htm";
   }
}
