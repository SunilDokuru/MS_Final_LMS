/**
 *
 * @author Dokuru
 */

package com.template.spring.validators;

import com.template.spring.domain.RegistrationData;
import com.template.spring.service.RegistrationService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RegistrationFormValidator implements Validator{

    @Autowired
    private RegistrationService registrationService;

    public RegistrationService getRegistrationService() {
        return registrationService;
    }

    public void setRegistrationService(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }
    
    
    @Override
    public boolean supports(Class<?> type) {
        return RegistrationData.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "netID", "netID.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userID", "userID.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cPassword", "cPassword.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "phone.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "social", "social.required");
        
        RegistrationData data = (RegistrationData)o;
        if(!data.getPassword().equals(data.getcPassword()))
            errors.rejectValue("cPassword", "passwords.noMatch");
        
        if(!data.getEmail().equals(data.getNetID()+"@neiu.edu"))
            errors.rejectValue("email", "email.noMatch");
        
        String pwdRegex = "(?=^.{6,10}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&amp;*()_+}{&quot;:;'?/&gt;.&lt;,])(?!.*\\s).*$";
        Pattern pwd = Pattern.compile(pwdRegex);
        
        Matcher matcher = pwd.matcher(data.getPassword());
        if(!matcher.matches())
            errors.rejectValue("password", "password.requirementsFail");
        
        Pattern social = Pattern.compile("(^[1-9]{3})-([1-9]{2})-([1-9]{4})$");
        matcher = social.matcher(data.getSocial());
        if(!matcher.matches())
            errors.rejectValue("social", "social.formatFailed");
        
        Pattern phone = Pattern.compile("^(\\+[1-9]{1,2})?\\(?[0-9]{3}\\)?[\\s.-][0-9]{3}[\\s.-][0-9]{4}$");
	matcher = phone.matcher(data.getPhone());
        if(!matcher.matches())
            errors.rejectValue("phone", "phone.formatFailed");
    }
    
}
