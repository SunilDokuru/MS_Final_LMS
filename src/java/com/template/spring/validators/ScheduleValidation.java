/**
 *
 * @author Dokuru
 */

package com.template.spring.validators;

import com.template.spring.domain.AdminSchedule;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ScheduleValidation implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return AdminSchedule.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        AdminSchedule schedule = (AdminSchedule)obj;
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        
        String startTime = schedule.getStartTime();
        String endTime = schedule.getEndTime();
        
        try {
            Time startT = new java.sql.Time(formatter.parse(startTime).getTime());
            Time endT = new java.sql.Time(formatter.parse(endTime).getTime());
            
            long diff = endT.getTime() - startT.getTime();
            long hour = 2*60*60*1000;
            long halfHour = 60*60*1000;
            
            if(endT.before(startT)) {
                errors.reject("startTime", "schedule.incorrect");
            }
            if(!(diff >= halfHour && diff <= hour)) {
                errors.rejectValue("startTime", "schedule.tooLong");
            } 
            
            
        } catch (ParseException ex) {
            Logger.getLogger(ScheduleValidation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
