/**
 *
 * @author Dokuru
 */

package com.template.spring.controllers;

import com.template.spring.service.GetAttendanceService;
import com.template.spring.service.GetUserCoursesService;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CourseViewController {
    @Autowired
    private GetUserCoursesService getUserCoursesService;

    public GetUserCoursesService getGetUserCoursesService() {
        return getUserCoursesService;
    }

    public void setGetUserCoursesService(GetUserCoursesService getUserCoursesService) {
        this.getUserCoursesService = getUserCoursesService;
    }
    
    @Autowired
    private GetAttendanceService getAttendanceService;

    public GetAttendanceService getGetAttendanceService() {
        return getAttendanceService;
    }

    public void setGetAttendanceService(GetAttendanceService getAttendanceService) {
        this.getAttendanceService = getAttendanceService;
    }
    
    @RequestMapping(value="/courseContent.htm", method=RequestMethod.GET)
    public String returnCourseView(ModelMap model, HttpSession session, RedirectAttributes attributes) {
        
        if(session.getAttribute("user")!=null) {
            /*HashMap<String, String> loggedUserDetails = (LinkedHashMap<String, String>)session.getAttribute("user");
            Get the course Content and set it in a session Attribute
            HashMap<String, String> courseRequested = (HashMap<String, String>)session.getAttribute("courseRequested");
            String courseID = courseRequested.get("courseID");
            
            LinkedHashMap<String, String> courseDetails = getAttendanceService.getCourseDetails(courseID, loggedUserDetails.get("userID"), loggedUserDetails.get("user_type"));;
            
            session.setAttribute("courseDetails", courseDetails);*/
            
            
            return "courseContent";
        } else {
            attributes.addFlashAttribute("errormessage", "Login To Access Requested Page");
            return "redirect:/loginPage.htm";
        }
        
    }
}
