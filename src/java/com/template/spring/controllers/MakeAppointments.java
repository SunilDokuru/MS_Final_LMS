/**
 *
 * @author Dokuru
 */

package com.template.spring.controllers;

import com.template.spring.domain.Schedule2;
import com.template.spring.service.AdminScheduleService2;
import com.template.spring.service.MakeAppointmentsService;
import com.template.spring.util.GetTwoWeekResults;
import com.template.spring.util.LeaveOldDates;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
public class MakeAppointments {
    
    @Autowired
    private MakeAppointmentsService makeAppointmentsService;

    public MakeAppointmentsService getMakeAppointmentsService() {
        return makeAppointmentsService;
    }

    public void setMakeAppointmentsService(MakeAppointmentsService makeAppointmentsService) {
        this.makeAppointmentsService = makeAppointmentsService;
    }
    
    @Autowired
    private AdminScheduleService2 adminScheduleService;

    public AdminScheduleService2 getAdminScheduleService() {
        return adminScheduleService;
    }

    public void setAdminScheduleService(AdminScheduleService2 adminScheduleService) {
        this.adminScheduleService = adminScheduleService;
    }
    
    @Autowired
    private GetTwoWeekResults getTwoWeekResults;

    public GetTwoWeekResults getGetTwoWeekResults() {
        return getTwoWeekResults;
    }

    public void setGetTwoWeekResults(GetTwoWeekResults getTwoWeekResults) {
        this.getTwoWeekResults = getTwoWeekResults;
    }
    
    @Autowired
    private LeaveOldDates leaveOldDates;

    public LeaveOldDates getLeaveOldDates() {
        return leaveOldDates;
    }

    public void setLeaveOldDates(LeaveOldDates leaveOldDates) {
        this.leaveOldDates = leaveOldDates;
    }
    
    
    @RequestMapping(value="makeAppointment.htm", method=RequestMethod.GET)
    public String redirectAppointmentsPage(HttpSession session, RedirectAttributes attributes) {
        
        if(session.getAttribute("user") != null) {
            HashMap<String, String> userDetails = (HashMap<String, String>) session.getAttribute("user");
            String user_type = userDetails.get("user_type");
            LinkedHashMap<String, String> courseDetails = (LinkedHashMap<String, String>) session.getAttribute("courseDetails");
            
            if(user_type.equals("student")) {
                
                session.setAttribute("appointMessage", "");
                
                List<Schedule2> getScheduleList;
                try {
                    getScheduleList = adminScheduleService.getAdminSchedule(courseDetails.get("courseID"), courseDetails.get("facultyID"));
                    
                    if(!getScheduleList.isEmpty()) {
                        session.setAttribute("listSize", getScheduleList.size());
                        
                        getScheduleList = leaveOldDates.leaveOlderDates(getScheduleList);
                        
                        getScheduleList = getTwoWeekResults.filterToTwoWeeks(getScheduleList);
                        //session.setAttribute("scheduleList", getScheduleList);
                        session.setAttribute("scheduleList", getScheduleList);
                    }
                    else {
                        session.setAttribute("listSize", 0);
                        session.setAttribute("appointMessage", "No Current Schedules Found on the System");
                    }
                }catch(NullPointerException ex) {
                    session.setAttribute("appointMessage", "No Open Slots to Display");
                    return "makeAppointment";
                } catch(Exception ex) {
                    attributes.addFlashAttribute("appointMessage", "Something brokedown and couldn't recover from the last error.<br />Please try later to get your schedule");
                    return "redirect:/makeAppointment.htm";
                }
            } else {
                attributes.addFlashAttribute("errormessage", "Unauthorized Access to Content");
                return "redirect:/logout.htm";
            }
        
        } else {
            attributes.addFlashAttribute("errormessage", "Login To Access Requested Page");
            return "redirect:/loginPage.htm";
        }
      
        return "makeAppointment";
    }
    
    @RequestMapping(value="/book/{date}/start/{startTime}/end/{endTime}.htm", method=RequestMethod.GET)
    public String bookAnAppointment(@PathVariable("date") String date, @PathVariable("startTime") Time startTime, @PathVariable("endTime") Time endTime, ModelMap model, HttpSession session, RedirectAttributes attributes) {        
        
        if(session.getAttribute("user") != null) {
            HashMap<String, String> loggedUserDetails = (HashMap<String, String>) session.getAttribute("user");
            
            String user_type = loggedUserDetails.get("user_type");
            
            HashMap<String, String> courseDetails = (HashMap<String, String>) session.getAttribute("courseDetails");            
            
            if(user_type.equals("student")) {
                try {
                    // TODO: Atmost appointments allowed from currentDate are 2. If user exceeds then exit the system with a error message
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    
                    Date currentDate = new Date();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(currentDate);
                    
                    cal.add(Calendar.DATE, 14);
                    
                    Date futureDate = sdf.parse(sdf.format(cal.getTime()));
                    
                    int numAppointments = adminScheduleService.getCurrentNumAppointments(futureDate, courseDetails, loggedUserDetails);
                    
                    if(numAppointments < 2) {
                        int updates = adminScheduleService.slotBooking(date, startTime, endTime, courseDetails, loggedUserDetails);
                    
                        if(updates == 1)    attributes.addFlashAttribute("appointMessage", "Appointment has been successfully scheduled.<br />Details for Appointment: " + date + " starting at: " + startTime);
                        else    attributes.addFlashAttribute("appointMessage", "There was an error creating the appointments. Please try again later");
                    } else {
                        attributes.addFlashAttribute("appointMessage", "Sorry Appointment Cannont be scheduled as you have reached your maximum limit for the week.<br /> Maximum appointments scheduled in a week are 2");
                    }
                    
                } catch(ParseException ex) {
                    attributes.addFlashAttribute("appointMessage", "Something Went Wrong!!! Please Try Again");
                    return "redirect:/makeAppointment.htm";
                }
                
                return "redirect:/makeAppointment.htm";
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
