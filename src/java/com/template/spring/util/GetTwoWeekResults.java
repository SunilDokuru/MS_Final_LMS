/**
 *
 * @author Dokuru
 */

/**
 *
 * @author Dokuru
 */
package com.template.spring.util;

import com.template.spring.domain.Schedule2;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GetTwoWeekResults {
    public List<Schedule2> filterToTwoWeeks(List<Schedule2> scheduleList) throws ParseException {
             
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        
        List<Schedule2> newList = new ArrayList<>();
        
        /* Get the Current Date and Add 7 to the current Date.*/
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        
        c.add(Calendar.DATE, 14);
        String newDate = formatter.format(c.getTime());
        
        Date dateLimit = formatter.parse(newDate);
        /*------------END---------------------*/
        
        
        /*Populate a Schedule Lisit which contains the dates of only the dates for the current week*/
        for(Schedule2 s: scheduleList) {
            String dateFromList = s.getDate();
            
            Date listDate = formatter.parse(dateFromList);
            
            if(listDate.before(dateLimit)) { 
                newList.add(s);
            }            
        }
    return newList;
    }    
}
