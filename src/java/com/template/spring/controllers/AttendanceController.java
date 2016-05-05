/**
 *
 * @author Dokuru
 */
package com.template.spring.controllers;

import com.template.spring.domain.Attendance;
import com.template.spring.domain.AttendanceForm;
import com.template.spring.service.AttendanceServiceAdmin;
import com.template.spring.service.GetAttendanceService;
import com.template.spring.util.FindAttendanceDates;
import com.template.spring.validators.AttendanceValidation;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/attendance.htm")
public class AttendanceController {
    
    @Autowired
    private AttendanceValidation attendanceValidation;

    public AttendanceValidation getAttendanceValidation() {
        return attendanceValidation;
    }

    public void setAttendanceValidation(AttendanceValidation attendanceValidation) {
        this.attendanceValidation = attendanceValidation;
    }
    
    @Autowired
    private AttendanceServiceAdmin attendanceServiceAdmin;

    public AttendanceServiceAdmin getAttendanceServiceAdmin() {
        return attendanceServiceAdmin;
    }

    public void setAttendanceServiceAdmin(AttendanceServiceAdmin attendanceServiceAdmin) {
        this.attendanceServiceAdmin = attendanceServiceAdmin;
    }
    
    @Autowired
    private GetAttendanceService getAttendanceService;

    public GetAttendanceService getGetAttendanceService() {
        return getAttendanceService;
    }

    public void setGetAttendanceService(GetAttendanceService getAttendanceService) {
        this.getAttendanceService = getAttendanceService;
    }
    
    @RequestMapping(method=RequestMethod.GET)
    //@ModelAttribute("attendanceData")
    public String getAttendanceForm(ModelMap model, HttpSession session, RedirectAttributes attributes) {
        if(session.getAttribute("user") != null) {
            String form = "";
            
            /*---------------------------Setting to CommandObject--------------------------------*/
            AttendanceForm attendanceForm = new AttendanceForm();
            List<Attendance> data = new ArrayList<>();
            /*-----------------------------------------------------------------------------------*/
            
            /* Object to get all scheduled Dates */
            FindAttendanceDates findAttendanceDates = new FindAttendanceDates();
            
            /* Retrieving logged user session Object */
            HashMap<String, String> userDetails = (HashMap<String, String>) session.getAttribute("user");
            String user_type = userDetails.get("user_type");
            
            
            //System.out.println("User from Attendance Controller" + userDetails.get("userID"));
            /* Geeting Requested Course Details and */
            HashMap<String, String> courseRequested = (HashMap<String, String>) session.getAttribute("courseRequested");
            
            courseRequested.put("user_type", user_type);
            
            /* Adding courseDetails to the Session */
            //session.setAttribute("requestedBy", courseRequested);     // Incase anything fails uncomment this. It should not happen as it was not used elsewhere
            
            
            /* Setting course details information in the session */
            LinkedHashMap<String, String> courseDetails = getAttendanceService.getCourseDetails(courseRequested.get("courseID"), userDetails.get("userID"), userDetails.get("user_type"));
            
            session.setAttribute("courseDetails", courseDetails);
                
            /*----------------------------Retrieve Scheduled Dates-------------------------------*/
            LinkedHashMap<String, String> dates;    
            try {
                dates = findAttendanceDates.getAttendanceDays(courseDetails.get("startDate"), courseDetails.get("endDate"), courseDetails.get("meetingDays"));
            } catch (ParseException ex) {
                Logger.getLogger(AttendanceController.class.getName()).log(Level.SEVERE, null, ex);
                attributes.addFlashAttribute("message", "There was an error getting attendance sheets");
                return "redirect:/courses.htm";
            }
            //session.setAttribute("days", dates.size());

            session.setAttribute("dates", dates);
            /*------------------------------------------------------------------------------------*/
            
            /*----------------------------Setting Values to the List-------------------------------*/
            Set set = dates.entrySet();
            Iterator i = set.iterator();
            List<String> scheduledDates = new ArrayList<>();
            session.setAttribute("scheduledDates", scheduledDates);
            
            Iterator i2 = set.iterator();
            while(i2.hasNext()) {
                Map.Entry entry = (Map.Entry)i2.next();
                scheduledDates.add(entry.getKey()+"");
            }
            

            /*-------------------------------------------------------------------------------------*/
            switch (user_type) {
                case "student":
                    String user = userDetails.get("userID");
                    courseDetails.put("studentID", user);
                    
                    /*Get all the values of attendances for all the dates and store in attendanceInfo*/
                    
                    /*Stored all the dates in an List.
                     * Now send this list to the getAttendanceData Service Layer and get the data for the attendances available in the DB
                     */
                    
                    LinkedHashMap<String, String> attendanceInfo = getAttendanceService.getAttendanceData(courseDetails, scheduledDates);
                    //LinkedHashMap<String, String> notesInfo = getAttendanceService.getNotesData(courseDetails, scheduledDates);
                    
                    /* All the values of the attendance are stored in attendanceInfo, Now add these values to the List and set the List to the values retrieved from DB
                     * These values are set to the attendance Form in view page
                     */
                    
                    while(i.hasNext()) {
                        Map.Entry entry = (Map.Entry)i.next();
                        String date = entry.getKey()+"";
                        //String attend = getAttendanceService.getUserResponseForDate(date, courseDetails);
                        String temp = attendanceInfo.get(date);
                        //System.out.println("Value of temp from controller\t" + temp+"\n");
                        
                        String [] temp1 = temp.split(":");
                        String attend = temp1[0];
                        String notes = temp1[1];
                        
                        //String attend = attendanceInfo.get(date);
                        //System.out.println("Attendance value from AttendanceController: " + attend);
                        data.add(new Attendance(userDetails.get("userID"), courseDetails.get("facultyID"), date, entry.getValue()+"", attend, notes));
                    }
                    attendanceForm.setUserResponse(data);
                    
                    int presentWeek = getAttendanceService.getPresentWeek(courseDetails);         
                    session.setAttribute("presentWeek", presentWeek);
                    session.setAttribute("attendRequestBy", "student");
                    
                    model.addAttribute("attendanceData", attendanceForm);
                    return "attendance";
                    
                case "admin":
                    session.setAttribute("attendRequestBy", "admin");
                    int currentWeek = attendanceServiceAdmin.getCurrentWeek(courseDetails);
                    
                    if(currentWeek == 0)
                        currentWeek = 1;
                    session.setAttribute("currentWeek", currentWeek);
                    form = "attendanceAdminView";
                    return form;
            }
            return form;
        }
        else {
            attributes.addFlashAttribute("errormessage", "Login To Access Requested Page");
            return "redirect:/loginPage.htm";
        }
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public String showUpdatedAttendance(@ModelAttribute("attendanceData") AttendanceForm attendanceForm, HttpSession session, BindingResult bindResult, RedirectAttributes attributes) {
        String formReturn;
        
        List<Attendance> fromUser = attendanceForm.getUserResponse();
        LinkedHashMap<String, String> fromForm = new LinkedHashMap<>();
        LinkedHashMap<String, String> attendanceNotes = new LinkedHashMap<>();
        
        /*Retrieve Only the values which are updated by the user and values > presentWeek as these need to be updated as well*/
        fromUser.stream().filter((a) -> (!"none".equals(a.getAttendance()))).forEach((a) -> {
            fromForm.put(a.getDate(), a.getAttendance());
            
            String note = a.getNotes();
                                                                                //date + "- " + a.getNotes()
            attendanceNotes.put(a.getDate(), note);
        });
        
        /*Removing null values. These values are for the readonly section present in the form*/
        Iterator<Map.Entry<String, String>> it = fromForm.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> e = it.next();
            String key = e.getKey();
            String value = e.getValue();
            if (value == null) {
                it.remove();
            }
        }

        Iterator<Map.Entry<String, String>> itNotes = attendanceNotes.entrySet().iterator();
        while(itNotes.hasNext()) {
            Map.Entry<String, String> e = itNotes.next();
            String key = e.getKey();
            String value = e.getValue();
            if(value == null)
                itNotes.remove();
        }
        
        //System.out.println("Printing List" + fromForm.toString());
        
        
        LinkedHashMap<String, String> courseDetails = (LinkedHashMap<String, String>)session.getAttribute("courseDetails");
        LinkedHashMap<String, String> dates = (LinkedHashMap<String, String>)session.getAttribute("dates");
        
        HashMap<String, String> userDetails = (HashMap<String, String>) session.getAttribute("user");
        String user_type = userDetails.get("user_type");
        
        if(user_type.equals("student")) {
            String studentID = userDetails.get("userID");
            courseDetails.put("studentID", studentID);
            
            int updates = getAttendanceService.updateAttendances(courseDetails, fromForm, dates);
            
            if(updates > 0) {
                int notesUpdates = getAttendanceService.updateNotes(courseDetails, attendanceNotes, dates);
                
                attributes.addFlashAttribute("errormessage", "Updated Successfully");
                formReturn = "redirect:/attendance.htm";
            }
            else {
                attributes.addFlashAttribute("errormessage", "There was an error. Please try Again!!!");
                formReturn = "redirect:/attendance.htm";
            }
        }
        else {
            // This is the admin Part
            return "redirect:/courses.htm";
        }
        return formReturn;
    }
}
