/**
 *
 * @author Dokuru
 */
package com.template.spring.controllers;

import com.template.spring.domain.ChangePassword;
import com.template.spring.service.ResetPasswordService;
import com.template.spring.validators.ResetPasswordValidator;
import java.util.HashMap;
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
@RequestMapping("/resetPassword.htm")
public class PasswordResetController {
   
    @Autowired
    private ResetPasswordService resetPasswordService;

    public ResetPasswordService getResetPasswordService() {
        return resetPasswordService;
    }

    public void setResetPasswordService(ResetPasswordService resetPasswordService) {
        this.resetPasswordService = resetPasswordService;
    }
    
    @Autowired
    private ResetPasswordValidator resetPasswordValidator;

    public ResetPasswordValidator getResetPasswordValidator() {
        return resetPasswordValidator;
    }

    public void setResetPasswordValidator(ResetPasswordValidator resetPasswordValidator) {
        this.resetPasswordValidator = resetPasswordValidator;
    }
    
    
    @RequestMapping(method=RequestMethod.GET)
    public String getResetForm (HttpSession session, ModelMap model, RedirectAttributes attributes) {
        if(session.getAttribute("user") != null ) {
            ChangePassword resetPassword = new ChangePassword();
            HashMap<String, String> userDetails = (HashMap<String, String>)session.getAttribute("user");
            
            session.setAttribute("requested", userDetails.get("user_type"));
            model.addAttribute("resetPassword", resetPassword);
            return "resetPassword";
        }
        else {
            attributes.addFlashAttribute("errormessage", "Login to Access Content");
            return "redirect:/loginPage.htm";
        }
            
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String ChangePassword (@ModelAttribute("resetPassword") ChangePassword resetPassword, HttpSession session, RedirectAttributes attributes, BindingResult bindResult) {
        HashMap<String, String> details = (HashMap<String, String>) session.getAttribute("user");
        
        resetPasswordValidator.validate(resetPassword, bindResult);
                
        if(bindResult.hasErrors()) {
            return "resetPassword";
        }
        else {
            int commit = resetPasswordService.resetPassword(resetPassword, details.get("netID"), details.get("user_type"));
            
            if(commit == -2) {
                attributes.addFlashAttribute("errormessage", "Current password do not match");
                return "redirect:/resetPassword.htm";
            }
            else if(commit == 1) {
                attributes.addFlashAttribute("errormessage", "Password Successfully Changed");
                return "redirect:/resetPassword.htm";
            }
            else {
                attributes.addFlashAttribute("errormessage", "Password not Changed. Retry!!!");
                return "redirect:/resetPassword.htm";
            }
        }
    }
}
