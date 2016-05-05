/**
 *
 * @author Dokuru
 */

package com.template.spring.controllers;

import com.template.spring.util.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class HttpErrorHandler {
    String path = "/error";
    
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    //@RequestMapping(value="/404")
    public String error404(){
        return path+"/404";
    }
}
