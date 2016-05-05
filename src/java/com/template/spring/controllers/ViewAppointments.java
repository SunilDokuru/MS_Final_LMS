/**
 *
 * @author Dokuru
 */

package com.template.spring.controllers;

import com.template.spring.domain.AppointmentInfo;
import com.template.spring.domain.Schedule2;
import com.template.spring.service.AdminScheduleService2;
import com.template.spring.util.LeaveOldDates;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ViewAppointments {
   
   @Autowired
   private AdminScheduleService2 adminScheduleService;

    public AdminScheduleService2 getAdminScheduleService() {
        return adminScheduleService;
    }

    public void setAdminScheduleService(AdminScheduleService2 adminScheduleService) {
        this.adminScheduleService = adminScheduleService;
    }
    
    @Autowired
    private LeaveOldDates leaveOldDates;

    public LeaveOldDates getLeaveOldDates() {
        return leaveOldDates;
    }

    public void setLeaveOldDates(LeaveOldDates leaveOldDates) {
        this.leaveOldDates = leaveOldDates;
    }
    
   
   @RequestMapping(value="viewAppointments", method=RequestMethod.GET)
    public String showViewAppointments(ModelMap model, HttpSession session, RedirectAttributes attributes) {
        if(session.getAttribute("user") != null) {
            HashMap<String, String> loggedUserDetails = (HashMap<String, String>)session.getAttribute("user");
            String user_type = loggedUserDetails.get("user_type");
            
            session.setAttribute("viewRequested", "");
            session.setAttribute("appointmentsMessage", "");
            session.setAttribute("appointmentsData", "");
            session.setAttribute("listSize", 0);
           
            HashMap<String, String> courseDetails = (HashMap<String, String>) session.getAttribute("courseDetails");
            
            List<Schedule2> appointments;
            List<AppointmentInfo> adminInfo;
            
            if(user_type.equals("student")) {
                try {
                    appointments = adminScheduleService.getAppointmentDetails(loggedUserDetails, courseDetails);
                    
                    if(appointments != null) {
                        session.setAttribute("listSize", appointments.size());
                        
                        appointments = leaveOldDates.leaveOlderDates(appointments);
                        
                        session.setAttribute("appointmentsData", appointments);
                        session.setAttribute("viewRequested", "student");
                    } else {
                        session.setAttribute("listSize", 0);
                        session.setAttribute("viewRequested", "student");
                        session.setAttribute("appointmentsMessage", "No Appointments to dispay at this time");
                    }
                    
                } catch(NullPointerException ex) {
                    session.setAttribute("appointmentsMessage", "No Appointments To display");
                    return "viewAppointments";
                } catch(Exception ex) {
                    attributes.addFlashAttribute("errormessaage", "Unknown Exception occurred.<br />You have been logged out for your safety");
                    return "redirect:/logout.htm";
                }
            }
            if(user_type.equals("admin")) {
                try {
                    adminInfo = adminScheduleService.getAppointmentDetails(loggedUserDetails, courseDetails);
                
                    if(adminInfo != null) {
                        session.setAttribute("listSize", adminInfo.size());
                    
                        adminInfo = leaveOldDates.leaveOldDatesAdmin(adminInfo);
                    
                        session.setAttribute("appointmentsData", adminInfo);
                        session.setAttribute("viewRequested", "admin");
                    } else {
                        session.setAttribute("listSize", 0);
                        session.setAttribute("viewRequested", "admin");
                        session.setAttribute("appointmentsMessage", "No data to dispay at this time");
                    }
                } catch(NullPointerException ex) {
                    session.setAttribute("appointmentsMessage", "No Appointments To display");
                    return "viewAppointments";
                } catch(Exception ex) {
                    attributes.addFlashAttribute("errormessaage", "Unknown Exception occurred.<br />You have been logged out for your safety");
                    return "redirect:/logout.htm";
                }
            }
            
        return "viewAppointments";
       } else {
            attributes.addFlashAttribute("errormessage", "Login To Access Requested Page");
            return "redirect:/loginPage.htm";
        }
    }
    
    @RequestMapping(value="/appointment/delete/{date}/start/{startTime}/end/{endTime}", method=RequestMethod.GET)
    public String deleteAppointment(@PathVariable("date") String date, @PathVariable("startTime") Time startTime, @PathVariable("endTime") Time endTime, HttpSession session, RedirectAttributes attributes) {
        
        HashMap<String, String> userDetails = (HashMap<String, String>)session.getAttribute("user");
        String user_type = userDetails.get("user_type");
        HashMap<String, String> courseDetails = (HashMap<String, String>)session.getAttribute("courseDetails");
        
        int changes;
        switch (user_type) {
            case "student":
                changes = adminScheduleService.deleteScheduledSlot(date, startTime, courseDetails, userDetails);
                
                if(changes == 1)    attributes.addFlashAttribute("appointmentsMessage", "Sucessfully Deleted The Appointment");
                else    attributes.addFlashAttribute("appointmentsMessage", "There was an error trying to Delete the Appointment.<br />Please Try Again later");
                session.setAttribute("viewRequested", "student");
            break;
            case "admin":
                changes = adminScheduleService.deleteScheduledSlot(date, startTime, courseDetails, userDetails);
                
                if(changes == 1)    attributes.addFlashAttribute("appointmentsMessage", "Sucessfully Deleted The Appointment");
                else    attributes.addFlashAttribute("appointmentsMessage", "There was an error trying to Delete the Appointment.<br />Please Try Again later");
                session.setAttribute("viewRequested", "admin");
            break;
        }        
        return "redirect:/viewAppointments.htm";
    }
}
