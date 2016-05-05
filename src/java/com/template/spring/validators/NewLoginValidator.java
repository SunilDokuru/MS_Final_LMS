/**
 *
 * @author Dokuru
 */
package com.template.spring.validators;

import com.template.spring.domain.VerifyUserLoginData;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class NewLoginValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return VerifyUserLoginData.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "netID", "netID.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required");
    }
    
}
