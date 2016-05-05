package com.template.spring.controllers;

import com.template.spring.domain.VerifyUserLoginData;
import com.template.spring.service.GetStudentDetailsService;
import com.template.spring.service.LoginService;
import com.template.spring.validators.NewLoginValidator;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
@RequestMapping("/loginPage.htm")
@SessionAttributes("details")
public class LoginController {
    
    @Autowired
    private GetStudentDetailsService getStudentDetailsService;

    public GetStudentDetailsService getGetStudentDetailsService() {
        return getStudentDetailsService;
    }

    public void setGetStudentDetailsService(GetStudentDetailsService getStudentDetailsService) {
        this.getStudentDetailsService = getStudentDetailsService;
    }
    
    
    @Autowired
    private NewLoginValidator newLoginValidator;

    public NewLoginValidator getNewLoginValidator() {
        return newLoginValidator;
    }

    public void setNewLoginValidator(NewLoginValidator newLoginValidator) {
        this.newLoginValidator = newLoginValidator;
    }
    
    @Autowired
    private LoginService loginService;

    public LoginService getLoginService() {
        return loginService;
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }
    
    
    @RequestMapping(method = RequestMethod.GET)
    public String getLoginPage(ModelMap model, HttpSession session) {
        try {
            if(session.getAttribute("user") == null) {
            VerifyUserLoginData verifyUserLoginData = new VerifyUserLoginData();
            model.addAttribute("loginData", verifyUserLoginData);      // loginData written here should be same as the one defined in the form
            return "loginPage";
        }
        else {
            LinkedHashMap<String, String> details = (LinkedHashMap<String, String>) session.getAttribute("user");
            return details.get("user_type") + "DashBoard";
        }
        }catch(NullPointerException ex) {
            return "redirect:/Error404.htm";
        }
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String CaptureLoginData(@ModelAttribute("loginData") VerifyUserLoginData verifyData, BindingResult bindResult, HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
        newLoginValidator.validate(verifyData, bindResult);
        
        if(bindResult.hasErrors())
            return "loginPage";
        else {
            String user_type = loginService.verifyLoggedUserExistence(verifyData);
            boolean validUser;
            if(user_type != null)
                validUser = loginService.verifyUserCredentials(user_type, verifyData);
            
            else {
                model.addAttribute("errormessage", "Invalid Credentials");
                return "loginPage";
            }
            if(validUser) {
                HashMap<String, String> personalDetails = getStudentDetailsService.getUserInfo(verifyData.getNetID());
                
                /*System.out.println("This is from Controller" + personalDetails.get("firstName"));*/
                if(!personalDetails.isEmpty())
                    session.setAttribute("user", personalDetails);
                else
                    return "Error404";
                //session.setAttribute("user", personalDetails);
                return "redirect:/" + user_type + "DashBoard.htm";
            }
            else {
                request.setAttribute("errormessage", "Invalid Credentials");
                return "loginPage";
            }
        }
    }
 }
