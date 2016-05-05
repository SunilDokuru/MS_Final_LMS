/**
 *
 * @author Dokuru
 */

package com.template.spring.controllers;

import com.template.spring.dao.GetUserCoursesDAO;
import com.template.spring.service.GetAttendanceService;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CoursesDynamic {
    @Autowired
    private GetAttendanceService getAttendanceService;

    public GetAttendanceService getGetAttendanceService() {
        return getAttendanceService;
    }

    public void setGetAttendanceService(GetAttendanceService getAttendanceService) {
        this.getAttendanceService = getAttendanceService;
    }
    
    @Autowired
    private GetUserCoursesDAO getUserCoursesDAO;

    public GetUserCoursesDAO getGetUserCoursesDAO() {
        return getUserCoursesDAO;
    }

    public void setGetUserCoursesDAO(GetUserCoursesDAO getUserCoursesDAO) {
        this.getUserCoursesDAO = getUserCoursesDAO;
    }
    
    @RequestMapping(value="/courses/{name}", method=RequestMethod.GET)
    public String getCoursePage(@PathVariable("name")String courseID, ModelMap model, HttpSession session, RedirectAttributes attributes) {
        if(session.getAttribute("user") != null) {
            HashMap<String, String> loggedUserDetails = (HashMap<String, String>) session.getAttribute("user");
            String user_type = loggedUserDetails.get("user_type");
            
            HashMap<String, String> courseRequested = new HashMap<>();
            courseRequested.put("courseID", courseID);
            courseRequested.put("userID", loggedUserDetails.get("userID"));
            
            session.setAttribute("courseRequested", courseRequested);
            
            
            LinkedHashMap<String, String> courseDetails = getAttendanceService.getCourseDetails(courseID, loggedUserDetails.get("userID"), loggedUserDetails.get("user_type"));
            
            session.setAttribute("courseDetails", courseDetails);
            
            // Set Course Related Details to the Session
            HashMap<String, String> courseContent = getUserCoursesDAO.getCourseContent(courseID);
            
            session.setAttribute("courseRequestedUser", user_type);
            session.setAttribute("courseContent", courseContent);
            return "courseContent";
        }
        else{
            attributes.addFlashAttribute("errormessage", "Login To Access Requested Page");
            return "redirect:/loginPage.htm";
        }
    }
}
