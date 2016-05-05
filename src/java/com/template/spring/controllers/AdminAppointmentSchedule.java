//package com.template.spring.controllers;
//
//import com.template.spring.domain.AdminSchedule;
//import com.template.spring.domain.AdminScheduleWithDay;
//import com.template.spring.domain.Schedule;
//import com.template.spring.service.AdminScheduleService;
//import com.template.spring.util.GetDays;
//import com.template.spring.util.SubsequentAppointmentDatesGenerator;
//import com.template.spring.validators.ScheduleValidation;
//import java.sql.Time;
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.List;
//import javax.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@Controller
//public class AdminAppointmentSchedule {
//    
//    @Autowired
//    private AdminScheduleService adminScheduleService;
//
//    public AdminScheduleService getAdminScheduleService() {
//        return adminScheduleService;
//    }
//
//    public void setAdminScheduleService(AdminScheduleService adminScheduleService) {
//        this.adminScheduleService = adminScheduleService;
//    }
//    
//    @Autowired 
//    private SubsequentAppointmentDatesGenerator subsequentAppointmentDatesGenerator;
//
//    public SubsequentAppointmentDatesGenerator getSubsequentAppointmentDatesGenerator() {
//        return subsequentAppointmentDatesGenerator;
//    }
//
//    public void setSubsequentAppointmentDatesGenerator(SubsequentAppointmentDatesGenerator subsequentAppointmentDatesGenerator) {
//        this.subsequentAppointmentDatesGenerator = subsequentAppointmentDatesGenerator;
//    }
//    
//    @Autowired
//    private ScheduleValidation scheduleValidation;
//
//    public ScheduleValidation getScheduleValidation() {
//        return scheduleValidation;
//    }
//
//    public void setScheduleValidation(ScheduleValidation scheduleValidation) {
//        this.scheduleValidation = scheduleValidation;
//    }
//    
//    @Autowired
//    private GetDays getDays;
//
//    public GetDays getGetDays() {
//        return getDays;
//    }
//
//    public void setGetDays(GetDays getDays) {
//        this.getDays = getDays;
//    }
//    
//    @RequestMapping(value="/adminAppointmentSchedule.htm", method=RequestMethod.GET)
//    public String getAdminScheduleChanger(ModelMap model, HttpSession session, RedirectAttributes attributes) {
//        String returnForm;
//        
//        if(session.getAttribute("user") != null) {
//           HashMap<String, String> userDetails = (HashMap<String, String>) session.getAttribute("user");
//            String user_type = userDetails.get("user_type");
//            
//            AdminSchedule adminSchedule = new AdminSchedule();
//            
//            if(user_type.equals("admin")) {
//                returnForm = "adminAppointmentSchedule";
//                LinkedHashMap<String, String> courseDetails = (LinkedHashMap<String, String>) session.getAttribute("courseDetails");
//                
//                session.setAttribute("listSize", "");
//                session.setAttribute("appointMessage", "");
//                session.setAttribute("appointMessage2", "");
//                /*  Here before sending the control to the view page get the details of the
//                    schedule and store it in a session Attribute. Get the values either in a List of hashMap
//                    or List<AdvisingSchedule2> to access the data.
//                */
//                List<Schedule> getScheduleList;
//                List<AdminScheduleWithDay> scheduleList;
//                try {
//                    getScheduleList = adminScheduleService.getAdminSchedule(courseDetails.get("courseID"), userDetails.get("userID"));
//                    
//                    if(getScheduleList != null) {
//                        session.setAttribute("listSize", getScheduleList.size());
//                        
//                        scheduleList = getDays.getDaysFromDate(getScheduleList);
//                        //session.setAttribute("scheduleList", getScheduleList);
//                        session.setAttribute("scheduleList", scheduleList);
//                    }
//                    else {
//                        session.setAttribute("listSize", 0);
//                        session.setAttribute("appointMessage2", "No Available Slots Found on the System");
//                    }
//                }catch(NullPointerException ex) {
//                    attributes.addFlashAttribute("errormessage", "You have been gone for long.<br /> Please Login to access your account");
//                    return "redirect:/loginPage.htm";
//                } catch(Exception ex) {
//                    attributes.addFlashAttribute("appointMessage2", "Something brokedown and couldn't recover from the last error.<br />Please try later to get your schedule");
//                    return "redirect:/adminAppointmentSchedule.htm";
//                }
//                
//                
//            model.addAttribute("scheduledDates", adminSchedule);    
//            return returnForm;
//            }
//            else {
//                attributes.addFlashAttribute("errormessage", "Unauthorized Access to Content");
//                return "redirect:/logout.htm";
//            }
//        }
//        else {
//            attributes.addFlashAttribute("errormessage", "Login To Access Requested Page");
//            return "redirect:/loginPage.htm";
//        }
//    }
//    
//    @RequestMapping(value="/adminAppointmentSchedule.htm", method=RequestMethod.POST)
//    public String getScheduledDates(@ModelAttribute("scheduledDates") AdminSchedule schedule, HttpSession session, RedirectAttributes attributes, BindingResult bindResult) {
//
//        /*
//                Insert the tuples into database.
//                Before Inserting few things have to be taken care of:
//                    Check if the inserting record already exists for the same faculty and same course.
//                    If record exists for the course, faculty, and date: update the time for the appointment
//                    Check the appointment time should not overlap for two courses with same faculty
//                    
//                    Make this event available for all the days until the end of the semester.
//                 
//        What all you need? CourseDetails (ToGet FacultyID and CourseID), AdminSchedule, UserSessionDetails (To match the facultyID against the facultyID in the courses)
//        */
//        
////        scheduleValidation.validate(schedule, bindResult);
////        if(bindResult.hasErrors())
////            return "adminAppointmentSchedule";
//        
//        
//        HashMap<String, String> loggedUserDetails = (HashMap<String, String>) session.getAttribute("user");
//        /*Retrieve the userID as this is faculty Module need not verify the user_type*/
//        
//        LinkedHashMap<String, String> courseDetails = (LinkedHashMap<String, String>) session.getAttribute("courseDetails");
//        
//        // Method to parseTime and get List of subsequent dates until the EndDate of the Course
//        /*Better to Get List<T> as return type as it helps in setting the values as well*/
//        /* Before populating verify if the user has a schedule at the same time*/        
//        
//        String generateMultiple = schedule.getSaveCurrent();
//        try {
//            
//            boolean noClash = adminScheduleService.checkPreviousTimes(schedule, loggedUserDetails.get("userID"));
//            System.out.println("noClash value:\t" + noClash);
//            if(noClash) {
//                List<Schedule> list = new ArrayList<>();
//                
//                if(generateMultiple.equalsIgnoreCase("yes"))
//                    list = subsequentAppointmentDatesGenerator.getAutoPopulatedScheduledList(schedule, courseDetails.get("endDate"));
//                else {
//                    
//                    DateFormat formatter = new SimpleDateFormat("HH:mm");
//                    
//                    String date = schedule.getDate();
//                    String sTime = schedule.getStartTime();
//                    String eTime = schedule.getEndTime();
//                    String sTOD = schedule.getStartTimeOfDay();
//                    String eTOD = schedule.getEndTimeOfDay();
//                    
//                    Time startTime = new java.sql.Time(formatter.parse(sTime).getTime());
//                    Time endTime = new java.sql.Time(formatter.parse(eTime).getTime());
//                    
//                    list.clear();
//                    list.add(new Schedule(date, startTime, endTime, sTOD, eTOD));
//                }
//                
//                
//                int updates = adminScheduleService.insertNewScheduleRecord(loggedUserDetails, courseDetails, list);
//                
//                if(updates > 0 && updates == list.size()) {
//                    attributes.addFlashAttribute("appointMessage12", "Added Successfully");
//                    return "redirect:/adminAppointmentSchedule.htm";
//                } else  if(updates < list.size()) {
//                    attributes.addFlashAttribute("appointMessage12", "Could Not add for all recurring dates as the same timeslot clashes with other schedule of yours");
//                    return "redirect:/adminAppointmentSchedule.htm";
//                }
//            } else {
//                attributes.addFlashAttribute("appointMessage12", "Sorry You Have an appointment at the same time in your schedule.<br />If you wish you can delete the slot from current schedule and update your new time slot.");
//                return "redirect:/adminAppointmentSchedule.htm";
//            }
//            
//            
//        } catch(ParseException | NullPointerException | DataIntegrityViolationException ex) {
//            attributes.addFlashAttribute("appointMessage12", "Something Went Wrong!!! Please Try Again");
//            return "redirect:/adminAppointmentSchedule.htm";
//        }
//        
//        
//        session.setAttribute("appointMessage12", "Added Successfully");
//        return "adminAppointmentSchedule";
//    }
//    
//    @RequestMapping(value="/date/{date}/start/{startTime}.htm", method=RequestMethod.GET)
//    public String deleteTimeSlot(@PathVariable("date") String date, @PathVariable("startTime") Time startTime, @ModelAttribute("scheduledDates") AdminSchedule schedule, ModelMap model, HttpSession session, RedirectAttributes attributes) {
//        
//        if(session.getAttribute("user") != null) {
//            HashMap<String, String> loggedUserDetails = (HashMap<String, String>) session.getAttribute("user");
//            
//            String user_type = loggedUserDetails.get("user_type");
//            
//            if(user_type.equals("admin")) {
//                
//                LinkedHashMap<String, String> courseDetails = (LinkedHashMap<String, String>) session.getAttribute("courseDetails");
//                Integer facultyID = Integer.parseInt(loggedUserDetails.get("userID"));
//                Integer courseID = Integer.parseInt(courseDetails.get("courseID"));
//                
//                int deleted = adminScheduleService.deleteScheduledSlot(date, startTime, facultyID, courseID, user_type);
//                
//                if(deleted == 1)    attributes.addFlashAttribute("appointMessage2", "Successfully Deleted requested item");
//                else    attributes.addFlashAttribute("appointMessage2", "Something went wrong. Please Retry!!!");
//                
//                return "redirect:/adminAppointmentSchedule.htm";
//            } else {
//                attributes.addFlashAttribute("errormessage", "Unauthorized Access to Content");
//                return "redirect:/logout.htm";
//            }
//        } else {
//            attributes.addFlashAttribute("errormessage", "Login To Access Requested Page");
//            return "redirect:/loginPage.htm";
//        }
//
//    }
//    
//}
