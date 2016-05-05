package com.template.spring.controllers;

import com.template.spring.service.GetUserCoursesService;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/courses.htm")
public class CoursesController {
    @Autowired
    private GetUserCoursesService getUserCoursesService;

    public GetUserCoursesService getGetUserCoursesService() {
        return getUserCoursesService;
    }

    public void setGetUserCoursesService(GetUserCoursesService getUserCoursesService) {
        this.getUserCoursesService = getUserCoursesService;
    }
    
    @RequestMapping(method=RequestMethod.GET)
    public String getStudentHomeView(HttpSession session, RedirectAttributes attributes) {
        if(session.getAttribute("user") != null) {
            HashMap<String, String> details = (HashMap<String, String>) session.getAttribute("user");
        
            System.out.println("From courses Controller:\t" + details.get("user_type") + "\t" + details.get("userID"));
        List<LinkedHashMap> courses;
        switch (details.get("user_type")) {
            case "student":                
                courses = getUserCoursesService.getUserCourses(details.get("userID"), "Registration");
                session.setAttribute("courses", courses);       // Has courseDetails.....
                break;
            case "admin":                
                courses = getUserCoursesService.getUserCourses(details.get("userID"), "Courses");
                
                session.setAttribute("courses", courses);
                break;
        }
        return "courses";
        }
        else {
            attributes.addFlashAttribute("errormessage", "Login Required to Access this Content");
            return "redirect:/loginPage.htm";
        }
    }
}
