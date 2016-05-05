/**
 *
 * @author Dokuru
 */

package com.template.spring.controllers;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/logout.htm")
public class LogoutController {
    @RequestMapping(method=RequestMethod.GET)
    public String logout (HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/loginPage.htm";
    }
}
