/**
 *
 * @author Dokuru
 */

package com.template.spring.controllers;

import com.template.spring.domain.RegistrationData;
import com.template.spring.service.GetStudentDetailsService;
import com.template.spring.service.RegistrationService;
import com.template.spring.validators.RegistrationFormValidator;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/register.htm")
@SessionAttributes("registrationData")            // Should this be same as the one in loginForm

public class RegistrationController {
    
    
    @Autowired
    private GetStudentDetailsService getStudentDetailsService;

    public GetStudentDetailsService getGetStudentDetailsService() {
        return getStudentDetailsService;
    }

    public void setGetStudentDetailsService(GetStudentDetailsService getStudentDetailsService) {
        this.getStudentDetailsService = getStudentDetailsService;
    }
    
    
    @Autowired
    private RegistrationFormValidator registrationFormValidator;

    public RegistrationFormValidator getRegistrationFormValidator() {
        return registrationFormValidator;
    }

    public void setRegistrationFormValidator(RegistrationFormValidator registrationFormValidator) {
        this.registrationFormValidator = registrationFormValidator;
    }
    
    @Autowired
    private RegistrationService registrationService;

    public RegistrationService getRegistrationService() {
        return registrationService;
    }

    public void setRegistrationService(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }
    
    
    @RequestMapping(method = RequestMethod.GET)
    public String userToLoginPage (ModelMap model) {
        // Need to create an domain object for the POJO class to which the formData has to be set
        RegistrationData userInfo = new RegistrationData();
        model.addAttribute("registrationData", userInfo);      
        return "register";
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public String clickRegister(@ModelAttribute("registrationData")RegistrationData registerData, BindingResult bindResult, HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
        registrationFormValidator.validate(registerData, bindResult);
        
        /*
         *  Step1: Validate Form Fields
         *  Step2: Check if user exists in users table. If not return "Unauthorized Access"
         *  Step3: If user Exists then add or insert the data to the appropriate table and redirect to the logiPage
         */
        
        if(bindResult.hasErrors())
            return "register";
        else {
            String check_user = registrationService.checkUser(registerData);
            
            if(check_user == null) {
                request.setAttribute("errormessage", "UnAuthorized UserAccess. Check your NetID and UserID");
                return "register";
            }
            else if ("done".equals(check_user)) {
                request.setAttribute("errormessage", "User Found in System. Login by Clicking the Home Button");
                return "register";
            }
            else {
                int inserted = registrationService.registerUserData(registerData, check_user);
                if(inserted == 1) {
                    HashMap<String, String> personalDetails = getStudentDetailsService.getUserInfo(registerData.getNetID());
                    
                if(!personalDetails.isEmpty())
                    session.setAttribute("user", personalDetails);
                else
                    return "Error404";
                return "redirect:/" + check_user + "DashBoard.htm";
                }
                else {
                    request.setAttribute("errormessage", "Error Occurred!!! Please Try Again");
                    return "register";
                }
            }
        }
    }
    
}
