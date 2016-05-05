/**
 *
 * @author Dokuru
 */

package com.template.spring.validators;

import com.template.spring.domain.ChangePassword;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ResetPasswordValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return ChangePassword.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cPWD", "current-password.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nPWD", "new-password.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rPWD", "re-enter-new-password.required");
        
        ChangePassword reset = (ChangePassword)obj;
        
        if(reset.getcPWD().equals(reset.getnPWD()))
            errors.rejectValue("cPWD", "password.same");
        String pwdRegex = "(?=^.{6,10}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&amp;*()_+}{&quot;:;'?/&gt;.&lt;,])(?!.*\\s).*$";
        Pattern pwd = Pattern.compile(pwdRegex);
        
        Matcher matcher = pwd.matcher(reset.getnPWD());
        if(!matcher.matches())
            errors.rejectValue("nPWD", "password.requirementsFail");
        
        else if(!reset.getnPWD().equals(reset.getrPWD()))
            errors.rejectValue("nPWD", "passwords.noMatch");
        
        
    }
    
}
