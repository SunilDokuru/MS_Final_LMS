/**
 *
 * @author Dokuru
 */

package com.template.spring.validators;

import com.template.spring.domain.AttendanceForm;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AttendanceValidation implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return AttendanceForm.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        
    }
    
    public Date convertToDate(String presentDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            Date date = sdf.parse(presentDate);
            
            return date;
        } catch (ParseException ex) {
            return null;
        }
    }
    
}
