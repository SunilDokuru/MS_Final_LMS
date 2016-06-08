/**
 *
 * @author Dokuru
 */

package com.template.spring.controllers;

import com.template.spring.domain.FinalGrade;
import com.template.spring.service.AdminTestGrades;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FinalGradesController {
    
    @Autowired
    private AdminTestGrades adminTestGrades;

    public AdminTestGrades getAdminTestGrades() {
        return adminTestGrades;
    }

    public void setAdminTestGrades(AdminTestGrades adminTestGrades) {
        this.adminTestGrades = adminTestGrades;
    }
    
    @RequestMapping(value="finalGrades.htm", method=RequestMethod.GET)
    public String calculateFinalGrades(ModelMap map, HttpSession session, RedirectAttributes attributes) {
        if(session.getAttribute("user") != null) {
            HashMap<String, String> userDetails = (HashMap<String, String>)session.getAttribute("user");
            String user_type = userDetails.get("user_type");
            
            HashMap<String, String> courseDetails = (HashMap<String, String>)session.getAttribute("courseDetails");
            session.setAttribute("gradeRequestedBy", user_type);
            
            if(user_type.equals("admin")) {
                try {
                    Integer facultyID = Integer.parseInt(userDetails.get("userID"));
                    
                    List<FinalGrade> allFinalGrades = adminTestGrades.getFinalGradesList(courseDetails, facultyID);
                    int update;
                    
                    update = adminTestGrades.updateFinalGrade(allFinalGrades);
                    
                    if(update == allFinalGrades.size())
                        session.setAttribute("FinalGrades", allFinalGrades);
                    else {
                        attributes.addFlashAttribute("errormessage", "There was an error, Please Try Again");
                        return "redirect:/grades.htm";
                    }
                    
                } catch(NullPointerException ex) {
                    attributes.addFlashAttribute("errormessage", "There was an error, Please Try Again");
                    return "redirect:/grades.htm";
                }
                return "finalGrades";
            } else {
                attributes.addFlashAttribute("errormessage", "Unauthorized Access to Content");
                return "redirect:/logout.htm";
            }
            
        } else {
            attributes.addFlashAttribute("errormessage", "Login To Access Requested Page");
            return "redirect:/loginPage.htm";
        }
    }
    
    @RequestMapping(value="studentFinalGrades", method=RequestMethod.GET)
    public String getStudentFinalGrades(HttpSession session, RedirectAttributes attributes) {
        if(session.getAttribute("user") != null) {
            HashMap<String, String> userDetails = (HashMap<String, String>)session.getAttribute("user");
            String user_type = userDetails.get("user_type");
            
            if(user_type.equals("student")){
                try {
                    Integer studentID = Integer.parseInt(userDetails.get("userID"));
                    
                    List<FinalGrade> finalGrades = adminTestGrades.getStudentFinalGrades(studentID);
                    
                    for(FinalGrade g: finalGrades) {
                        System.out.println(g.getCourseID() + "\t" + g.getCourseName() + "\t" + g.getGrade() + "\t" + g.getStudentID());
                    }
                    
                    session.setAttribute("studentFinalGrades", finalGrades);                    
                } catch(NullPointerException ex) {
                        attributes.addFlashAttribute("errormessage", "There was an error while retrieving grades, Please Try Again");
                        return "redirect:/studentDashBoard.htm";
                }
                return "studentFinalGrades";
            } else {
                attributes.addFlashAttribute("errormessage", "Unauthorized Access to Content");
                return "redirect:/logout.htm";
            }
        
        } else {
            attributes.addFlashAttribute("errormessage", "Login To Access Requested Page");
            return "redirect:/loginPage.htm";
        }
    }
}
