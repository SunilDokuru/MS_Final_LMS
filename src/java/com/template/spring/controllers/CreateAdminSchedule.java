/**
 *
 * @author Dokuru
 */

package com.template.spring.controllers;

import com.template.spring.domain.AdminSchedule2;
import com.template.spring.domain.Schedule2;
import com.template.spring.service.AdminScheduleService2;
import com.template.spring.util.DivideToSlots;
import com.template.spring.util.GenerateSubsequentAppointmentDates;
import com.template.spring.util.LeaveOldDates;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CreateAdminSchedule {
    
    @Autowired
    private AdminScheduleService2 adminScheduleService;

    public AdminScheduleService2 getAdminScheduleService() {
        return adminScheduleService;
    }

    public void setAdminScheduleService(AdminScheduleService2 adminScheduleService) {
        this.adminScheduleService = adminScheduleService;
    }
    
    @Autowired 
    private GenerateSubsequentAppointmentDates generateSubsequentAppointmentDates;

    public GenerateSubsequentAppointmentDates getGenerateSubsequentAppointmentDates() {
        return generateSubsequentAppointmentDates;
    }

    public void setGenerateSubsequentAppointmentDates(GenerateSubsequentAppointmentDates generateSubsequentAppointmentDates) {
        this.generateSubsequentAppointmentDates = generateSubsequentAppointmentDates;
    }
    
    @Autowired
    private DivideToSlots divideToSlots;

    public DivideToSlots getDivideToSlots() {
        return divideToSlots;
    }

    public void setDivideToSlots(DivideToSlots divideToSlots) {
        this.divideToSlots = divideToSlots;
    }
    
    @Autowired
    private LeaveOldDates leaveOldDates;

    public LeaveOldDates getLeaveOldDates() {
        return leaveOldDates;
    }

    public void setLeaveOldDates(LeaveOldDates leaveOldDates) {
        this.leaveOldDates = leaveOldDates;
    }
    
    
    @RequestMapping(value="/adminAppointmentSchedule.htm", method=RequestMethod.GET)
    public String getAdminDchedule(ModelMap model, HttpSession session, RedirectAttributes attributes) {
        String returnForm;
        
        if(session.getAttribute("user") != null) {
           HashMap<String, String> userDetails = (HashMap<String, String>) session.getAttribute("user");
            String user_type = userDetails.get("user_type");
            
            AdminSchedule2 adminSchedule = new AdminSchedule2();
            
            if(user_type.equals("admin")) {
                returnForm = "adminAppointmentSchedule";
                LinkedHashMap<String, String> courseDetails = (LinkedHashMap<String, String>) session.getAttribute("courseDetails");
                
                session.setAttribute("listSize", "");
                session.setAttribute("appointMessage", "");
                session.setAttribute("appointMessage2", "");
                     
                List<Schedule2> getScheduleList;
                List<Schedule2> newScheduleList;
                try {
                    getScheduleList = adminScheduleService.getAdminSchedule(courseDetails.get("courseID"), userDetails.get("userID"));
                    
                    if(!getScheduleList.isEmpty()) {
                        session.setAttribute("listSize", getScheduleList.size());
                        
                        newScheduleList = leaveOldDates.leaveOlderDates(getScheduleList);
                        
                        session.setAttribute("scheduleList", newScheduleList);
                    } else {
                        session.setAttribute("listSize", 0);
                        session.setAttribute("appointMessage2", "There are No Available Slots To Display at this Time.<br />Please Try again Later.");
                    }
                } catch(NullPointerException ex) {
                    attributes.addFlashAttribute("errormessage", "You have been gone for long.<br /> Please Login to access your account");
                    return "redirect:/logout.htm";
                } catch(Exception ex) {
                    attributes.addFlashAttribute("appointMessage2", "Something brokedown and couldn't recover from the last error.<br />Please try later to get your schedule");
                    return "redirect:/loginPage.htm";
                }
                
                    
                model.addAttribute("scheduledDates", adminSchedule);    
                return returnForm;
            } else {
                attributes.addFlashAttribute("errormessage", "Unauthorized Access to Content");
                return "redirect:/logout.htm";
            }
        }
        else {
            attributes.addFlashAttribute("errormessage", "Login To Access Requested Page");
            return "redirect:/loginPage.htm";
        }
    }
    
    @RequestMapping(value="/adminAppointmentSchedule.htm", method=RequestMethod.POST)
    public String getScheduledDates(@ModelAttribute("scheduledDates") AdminSchedule2 schedule, HttpSession session, RedirectAttributes attributes, BindingResult bindResult) {
 
        HashMap<String, String> loggedUserDetails = (HashMap<String, String>) session.getAttribute("user");
        /*Retrieve the userID as this is faculty Module need not verify the user_type*/
        
        String user = loggedUserDetails.get("userID");
        LinkedHashMap<String, String> courseDetails = (LinkedHashMap<String, String>) session.getAttribute("courseDetails");

        String generateMultiple = schedule.getSaveCurrent();
        
        try {
            boolean noClash = adminScheduleService.checkForSlotClashes (schedule, user);
            if(noClash) {
                List<Schedule2> list = new ArrayList<>();
                
                if(generateMultiple.equalsIgnoreCase("yes")) {
                    list = generateSubsequentAppointmentDates.generateList(schedule, courseDetails.get("endDate"));
                } else {
                    DateFormat formatter = new SimpleDateFormat("HH:mm");
                    String date = schedule.getDate();
                    String sTime = schedule.getStartTime();
                    String eTime = schedule.getEndTime();
                    String dayOfWeek = schedule.getDayOfWeek();
                    
                    Time startTime = new java.sql.Time(formatter.parse(sTime).getTime());
                    Time endTime = new java.sql.Time(formatter.parse(eTime).getTime());
                    
                    list.clear();
                    
                    list.add(new Schedule2(date, dayOfWeek, startTime, endTime));
                }
                
                List<Schedule2> slotList;
                slotList = divideToSlots.divideTimeFrameToSlots(list);
                int updates = adminScheduleService.insertWholeRecord(loggedUserDetails, courseDetails, list, slotList);
                
                if(updates > 0 && updates == list.size()) {
                    attributes.addFlashAttribute("appointMessage12", "Added Successfully");
                    return "redirect:/adminAppointmentSchedule.htm";
                } else  if(updates < list.size()) {
                    attributes.addFlashAttribute("appointMessage12", "Could Not add for all recurring dates as the same timeslot clashes with other schedule of yours");
                    return "redirect:/adminAppointmentSchedule.htm";
                }
            } else {
                attributes.addFlashAttribute("appointMessage12", "Sorry You Have an appointment at the same time in your schedule.<br />If you wish you can delete the slot from current schedule and update your new time slot.");
                return "redirect:/adminAppointmentSchedule.htm";
            }
        } catch(NullPointerException | DataIntegrityViolationException | ParseException ex) {
            attributes.addFlashAttribute("appointMessage12", "Something Went Wrong!!! Please Try Again");
            return "redirect:/adminAppointmentSchedule.htm";
        }
        
        /*
        System.out.println("From New Controller\t" + schedule.getDayOfWeek());
        System.out.println("Generate Multiple\t" + generateMultiple);
        System.out.println("Date and Times:\t" + schedule.getDate() + "\t" + schedule.getStartTime() + "\t" + schedule.getEndTime());
        
        
            for(Schedule2 s : list) {
                    System.out.println("From New Controller Loop\t" + s.getDayOfWeek());
                    System.out.println("Date and Times:\t" + s.getDate() + "\t" + s.getStartTime() + "\t" + s.getEndTime());
            }
        */
        session.setAttribute("appointMessage12", "Added Successfully");
        return "adminAppointmentSchedule";
    }
    
    @RequestMapping(value="/delete/{date}/start/{startTime}.htm", method=RequestMethod.GET)
    public String deleteTimeSlot(@PathVariable("date") String date, @PathVariable("startTime") Time startTime, @ModelAttribute("scheduledDates") AdminSchedule2 schedule, ModelMap model, HttpSession session, RedirectAttributes attributes) {
        
        if(session.getAttribute("user") != null) {
            HashMap<String, String> loggedUserDetails = (HashMap<String, String>) session.getAttribute("user");
            
            String user_type = loggedUserDetails.get("user_type");
            
            if(user_type.equals("admin")) {
                
                LinkedHashMap<String, String> courseDetails = (LinkedHashMap<String, String>) session.getAttribute("courseDetails");
                int deleted = adminScheduleService.deleteScheduledSlot(date, startTime, courseDetails, loggedUserDetails);
                
                if(deleted == 1)    attributes.addFlashAttribute("appointMessage2", "Successfully Deleted requested item");
                else    attributes.addFlashAttribute("appointMessage2", "Something went wrong. Please Retry!!!");
                
                return "redirect:/adminAppointmentSchedule.htm";
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
