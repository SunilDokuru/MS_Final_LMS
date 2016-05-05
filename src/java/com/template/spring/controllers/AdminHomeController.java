/**
 *
 * @author Dokuru
 */

package com.template.spring.controllers;

import java.util.HashMap;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/adminDashBoard.htm")
public class AdminHomeController {
    
    @RequestMapping(method=RequestMethod.GET)
    public String getAdminHomeView(HttpSession session, RedirectAttributes attributes) {
        HashMap<String, String> details = (HashMap<String, String>) session.getAttribute("user");
        try {
            if(details.get("firstName") == null) {
                
            }
        }catch(NullPointerException ex) {
            attributes.addFlashAttribute("errormessage", "Login To Access HomePage");
            return "redirect:/loginPage.htm";
        }
        
        return "adminDashBoard";
    }
}
