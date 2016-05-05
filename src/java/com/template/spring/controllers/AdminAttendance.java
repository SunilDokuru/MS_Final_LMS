/**
 *
 * @author Dokuru
 */
package com.template.spring.controllers;

import com.template.spring.domain.Attendance;
import com.template.spring.domain.AttendanceForm;
import com.template.spring.service.AttendanceServiceAdmin;
import com.template.spring.service.GradingService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class AdminAttendance {
    @Autowired
    private AttendanceServiceAdmin attendanceServiceAdmin;

    public AttendanceServiceAdmin getAttendanceServiceAdmin() {
        return attendanceServiceAdmin;
    }

    public void setAttendanceServiceAdmin(AttendanceServiceAdmin attendanceServiceAdmin) {
        this.attendanceServiceAdmin = attendanceServiceAdmin;
    }
    
    @Autowired
    private GradingService gradingService;

    public GradingService getGradingService() {
        return gradingService;
    }

    public void setGradingService(GradingService gradingService) {
        this.gradingService = gradingService;
    }
    
    @RequestMapping(value="/attendance/{name}", method=RequestMethod.GET)
    public String getAttendanceSheet(@PathVariable("name") String date, ModelMap model, HttpSession session, RedirectAttributes attributes) {
        
        if (session.getAttribute("user") != null) {
            HashMap<String, String> userDetails = (HashMap<String, String>) session.getAttribute("user");
            String user_type = userDetails.get("user_type");
            
            session.setAttribute("requestedDate", date);

            /*---------------------------Setting to CommandObject--------------------------------*/
                AttendanceForm attendanceForm = new AttendanceForm();
                List<Attendance> data = new ArrayList<>();
            /*-----------------------------------------------------------------------------------*/
                        
            if (user_type.equals("admin")) {
                /*Get the Course Details From The Session*/
                LinkedHashMap<String, String> courseDetails = (LinkedHashMap<String, String>) session.getAttribute("courseDetails");                
                
                Integer facultyID = Integer.parseInt(userDetails.get("userID"));
                String meetingDay = courseDetails.get("meetingDays");
                
                
                int currentWeek = (int) session.getAttribute("currentWeek");
                List<String> scheduledDates = (List<String>) session.getAttribute("scheduledDates");
                
                if(scheduledDates.get(currentWeek-1).equals(date))
                    session.setAttribute("disableButton", "disable");
                else
                    session.setAttribute("disableButton", "");
                
                /*Using the CourseID from the courseDetails and FacultyID from the userDetails get the students list populated*/
                List<Integer> studentIDList = attendanceServiceAdmin.getStudentID(courseDetails.get("courseID"));
                
                try {
                    if(!studentIDList.isEmpty()) {
                    }
                } catch(NullPointerException ex) {
                    attributes.addFlashAttribute("errormessage", "Unable to generate class list. As there are no registrations.<br /> Comeback Later");
                    return "redirect:/attendance.htm";
                }
                
                HashMap<Integer, String> studentNames = gradingService.getStudentNames(studentIDList);
                session.setAttribute("studentNames", studentNames);
                
                studentIDList.stream().forEach((studentID) -> {
                    String attendance = attendanceServiceAdmin.getAttendanceForPreviousWeeks(date, courseDetails.get("courseID"), facultyID, studentID);
                    if(attendance != null) {
                        String [] temp = attendance.split(":");
                        String attend = temp[0];
                        String note = temp[1];
                        
                        data.add(new Attendance(studentID+"", facultyID+"", date, meetingDay, attend, note));
                    }
                    else
                        data.add(new Attendance(studentID+"", facultyID+"", date, meetingDay, "none", ""));
                });     
                attendanceForm.setUserResponse(data);
                
                model.addAttribute("attendanceData", attendanceForm);
                return "attendanceSheet";
            } else {
                attributes.addFlashAttribute("errormessage", "Unauthorized Access to Content");
                return "redirect:/logout.htm";
            }
        } else {
            attributes.addFlashAttribute("errormessage", "Login To Access Requested Page");
            return "redirect:/loginPage.htm";
        }
    }
    
    @RequestMapping(value="/attendance/{name}", method=RequestMethod.POST)
    public String updateAttendance(@PathVariable("name") String date, @ModelAttribute("attendanceData") AttendanceForm attendanceForm, ModelMap model, HttpSession session, BindingResult bindResult, RedirectAttributes attributes) {
        
        HashMap<String, String> fromUser = new HashMap<>();
        List<Attendance> fromForm = attendanceForm.getUserResponse();
        
        LinkedHashMap<String, String> courseDetails = (LinkedHashMap<String, String>) session.getAttribute("courseDetails");                
        
        int currentWeek = (int)session.getAttribute("currentWeek");
        fromForm.stream().forEach((a) -> {
            fromUser.put(a.getStudentID(), a.getAttendance());
        });
        
        LinkedHashMap<String, String> dates = (LinkedHashMap<String, String>)session.getAttribute("dates");
        
        if(session.getAttribute("disableButton").equals("disable")) {
            // Logic to Insert Attendance Data with presentWeek value "currentWeek + 1"
            int updates = attendanceServiceAdmin.insertNewRecord(currentWeek, fromUser, courseDetails, date, dates);
            if(updates > 0)
                attributes.addFlashAttribute("errormessage", "UpdatedSuccessfully");
            else
                attributes.addFlashAttribute("errormessage", "Sorry, There was an error. Please re-try");
        }
        else {
            // Update the value of the attendances
            int updates = attendanceServiceAdmin.updateRecord(fromUser, courseDetails, date);
            if(updates > 0)
                attributes.addFlashAttribute("errormessage", "UpdatedSuccessfully");
            else
                attributes.addFlashAttribute("errormessage", "Sorry, There was an error. Please re-try");
        }
        
        return "redirect:/attendance/" + date + ".htm";
    }
}
