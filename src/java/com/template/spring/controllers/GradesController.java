/**
 *
 * @author Dokuru
 */

/*
 *  Verify User Session. If(!session)    redirect:/loginPage
 *  If(session) - Get the user_type and userID from the userSession
 *  
 */
package com.template.spring.controllers;

import com.template.spring.domain.Grades;
import com.template.spring.domain.GradesList;
import com.template.spring.service.AdminTestGrades;
import com.template.spring.service.GetAttendanceService;
import com.template.spring.service.GradingService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class GradesController {
    
    @Autowired
    private GetAttendanceService getAttendanceService;

    public GetAttendanceService getGetAttendanceService() {
        return getAttendanceService;
    }

    public void setGetAttendanceService(GetAttendanceService getAttendanceService) {
        this.getAttendanceService = getAttendanceService;
    }
    
    @Autowired
    private GradingService gradingService;

    public GradingService getGradingService() {
        return gradingService;
    }

    public void setGradingService(GradingService gradingService) {
        this.gradingService = gradingService;
    }
    
    @Autowired
    private AdminTestGrades adminTestGrades;

    public AdminTestGrades getAdminTestGrades() {
        return adminTestGrades;
    }

    public void setAdminTestGrades(AdminTestGrades adminTestGrades) {
        this.adminTestGrades = adminTestGrades;
    }
    
    
    @RequestMapping(value = "/grades.htm", method=RequestMethod.GET)
    public String getGradesView(ModelMap model, HttpSession session, RedirectAttributes attributes) {
        
        if(session.getAttribute("user") != null) {
            HashMap<String, String> userDetails = (HashMap<String, String>) session.getAttribute("user");
            String user_type = userDetails.get("user_type");
            
            List<Grades> grades;
            GradesList gradesList = new GradesList();
            
            HashMap<String, String> courseRequested = (HashMap<String, String>)session.getAttribute("courseRequested");
            courseRequested.put("user_type", user_type);
            
            LinkedHashMap<String, String> courseDetails = getAttendanceService.getCourseDetails(courseRequested.get("courseID"), userDetails.get("userID"), userDetails.get("user_type"));
            session.setAttribute("courseDetails", courseDetails);
            
            String form="";
            switch (user_type) {
                case "student":
                    session.setAttribute("gradesRequestedBy", "student");
                    grades = gradingService.getGrades(courseDetails, userDetails.get("userID"));
                    
                    gradesList.setGradesList(grades);
                    model.addAttribute("gradeList", gradesList);
                    
                    return "grades";
                case "admin":
                    session.setAttribute("gradesRequestedBy", "admin");
                    
                    grades = gradingService.getAllGrades(courseDetails);
                    session.setAttribute("allGrades", grades);
                    
                    List<String> testList = new ArrayList<>();
                    grades.stream().filter((g) -> (!testList.contains(g.getTestName()))).forEach((g) -> {
                        testList.add(g.getTestName());
                    });
                    
                    /*Below logic is used, if when the admin wants to add new test scores for the class
                    */
                    List<Integer> studentIDList = new ArrayList<>();
                    grades.stream().filter((g) -> (!studentIDList.contains(g.getStudentID()))).forEach((g) -> {
                        studentIDList.add(g.getStudentID());
                    });
                    
                    
                    HashMap<Integer, String> studentNames = gradingService.getStudentNames(studentIDList);
                    gradesList.setGradesList(grades);
                    
                    session.setAttribute("gradeListAdmin", testList);
                    session.setAttribute("studentNames", studentNames);
                    session.setAttribute("size", grades.size());
                    
                    model.addAttribute("addGrades", gradesList);
                    return "grades";
            }
            return form;
        }
        else {
            attributes.addFlashAttribute("errormessage", "Login To Access Requested Page");
            return "redirect:/loginPage.htm";
        }
    }
//    @RequestMapping(value = "/grades.htm", method=RequestMethod.POST)
//    public String addTestScore(@ModelAttribute("addGrades")GradesList gradesList, HttpSession session, RedirectAttributes attributes) {
//        System.out.println("Entered Post Method");
//        
//        attributes.addFlashAttribute("message", "Added Successfully");
//        return "redirect:/grades.htm";
//    }
    
    @RequestMapping(value="/grades/{name}", method=RequestMethod.GET)
    public String getTestViewForm(@PathVariable("name") String testName, ModelMap model, HttpSession session, RedirectAttributes attributes) {

        /*  Get scores of all the students for Test (testName) for the course Requested
         *  The course Details are retrieved from the session object "courseDetails"
         */
        if(session.getAttribute("user") != null) {
            try {
               
                HashMap<String, String> userDetails = (HashMap<String, String>) session.getAttribute("user");
                String user_type = userDetails.get("user_type");
                HashMap<String, String> courseDetails = (HashMap<String, String>)session.getAttribute("courseDetails");
                
                
                if(user_type.equals("admin")) {
                    session.setAttribute("testName", testName);
                    //List<Grades> allGrades = (List<Grades>) session.getAttribute("allGrades");
                
                    List<Grades> allGrades = adminTestGrades.getTestGrades(testName, Integer.parseInt(userDetails.get("userID")), Integer.parseInt(courseDetails.get("courseID")));
                    
                    /*List<Integer> studentIDList = new ArrayList<>();
                    allGrades.stream().filter((g) -> (!studentIDList.contains(g.getStudentID()))).forEach((g) -> {
                        studentIDList.add(g.getStudentID());
                    });*/
                    
                    
                    GradesList gradeList = new GradesList();
                
                    int size = 0;
                    size = allGrades.stream().filter((g) -> (g.getTestName().equals(testName))).map((_item) -> 1).reduce(size, Integer::sum);
                    session.setAttribute("studentSize", size);
                
                    int temp = 0, max = 0;
                    for(Grades a: allGrades) {
                        Grades grade = a;
                        if(a.getTestName().equals(testName) && temp == 0) {
                            max = a.getMaxScore();
                            temp = 1;
                        }
                    }
                    session.setAttribute("maxScore", max);
                    gradeList.setGradesList(allGrades);
                
                    model.addAttribute("gradeUpdates", gradeList);
                    return "gradesAdminView";
                }
                else {
                    attributes.addFlashAttribute("errormessage", "Unauthorized Access to Content");
                    return "redirect:/logout.htm";
                }
            } catch(NullPointerException ex) {
                attributes.addFlashAttribute("errormessage", "Unexpected Error Occurred");
                return "redirect:/grades.htm";
            }
        }
        else {
            attributes.addFlashAttribute("errormessage", "Admin's Login Required to gain access to this Content");
            return "redirect:/loginPage.htm";
        }
        
    }
    
    @RequestMapping(value="/grades/{name}", method=RequestMethod.POST)
    public String showUpdatedScores(@PathVariable("name") String name, @ModelAttribute("gradeUpdates") GradesList gradesList, @PathVariable("name") String testName, ModelMap model, HttpSession session, RedirectAttributes attributes) {
        
        if(session.getAttribute("user") != null) {
            try {
                List<Grades> fromForm = gradesList.getGradesList();
                //fromForm.removeAll(Collections.singleton(null));
                /*System.out.println("Reached Post Controller");
                for(Grades g: fromForm) {
                    System.out.println(g.getTestName()  + "\t" + g.getScore() + "\t" + g.getStudentID());
                }*/
                
                int updates = getGradingService().updateScores(fromForm);
                if (updates > 0); else {
                    attributes.addFlashAttribute("message10", "Retry");
                    return "redirect:/gradesAdminView.htm";
                }
            } catch(NullPointerException ex) {
                attributes.addFlashAttribute("message10", "Please Check your Value");
                    return "redirect:/gradesAdminView.htm";
            }
            return "gradesAdminView";
        }
        else {
            attributes.addFlashAttribute("errormessage", "Login To Access Requested Page");
            return "redirect:/loginPage.htm";
        }
    }
    
}
