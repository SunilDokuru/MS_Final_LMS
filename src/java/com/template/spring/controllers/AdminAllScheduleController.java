/**
 *
 * @author Dokuru
 */

package com.template.spring.controllers;

import com.template.spring.domain.AllSchedules;
import com.template.spring.service.AdminScheduleService2;
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
public class AdminAllScheduleController {
    
    @Autowired
    private AdminScheduleService2 adminScheduleService;

    public AdminScheduleService2 getAdminScheduleService() {
        return adminScheduleService;
    }

    public void setAdminScheduleService(AdminScheduleService2 adminScheduleService) {
        this.adminScheduleService = adminScheduleService;
    }
    
    @RequestMapping(value="adminAllSchedule", method=RequestMethod.GET)
    public String showAllSchedule(ModelMap model, HttpSession session, RedirectAttributes attributes) {
        if(session.getAttribute("user")!=null) {
            HashMap<String, String> loggedUserDetails = (HashMap<String, String>)session.getAttribute("user");
            String user = loggedUserDetails.get("user_type");
            session.setAttribute("scheduleList", "");
            session.setAttribute("appointmentsList", "");
            /*
                Retrieve the schedule + appointments
                    HashMap<Appointments, List<Pojo(List<Pojo(cID, cName, sID, sName, date, startTime, endTime)Appointments>
                    HashMap<Schedule, List<Pojo(List<Pojo(cID, cName, sID, sName, date, startTime, endTime)Schedue>
            */
            Integer facultyID = Integer.parseInt(loggedUserDetails.get("userID"));
            
            List<AllSchedules> appointments;
            List<AllSchedules> schedule;
            
            if(user.equals("admin")) {
                appointments = adminScheduleService.getAllScheduleList("appointments", facultyID);
                schedule = adminScheduleService.getAllScheduleList("schedule", facultyID);
                
                session.setAttribute("scheduleList", schedule);
                session.setAttribute("appointmentsList", appointments);
                return "adminAllSchedule";
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
