/**
 *
 * @author Dokuru
 */

package com.template.spring.util;

import com.template.spring.domain.AppointmentInfo;
import com.template.spring.domain.Schedule2;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class LeaveOldDates {
    public List<Schedule2> leaveOlderDates(List<Schedule2> currentList) throws ParseException {
        List<Schedule2> newList = new ArrayList<>();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        for(Schedule2 s: currentList) {
            String date = s.getDate();
            Date listDate = sdf.parse(date);
            
            Date currentDate =new Date();
            
            if(listDate.equals(currentDate) || listDate.after(currentDate))
                newList.add(s);
        }
        return newList;
    }
    
    public List<AppointmentInfo> leaveOldDatesAdmin(List<AppointmentInfo> oldList) throws ParseException {
        List<AppointmentInfo> newList = new ArrayList<>();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        for(AppointmentInfo a: oldList) {
            String date = a.getDate();
            Date listDate = sdf.parse(date);
            
            Date currentDate = new Date();
            
            if(listDate.equals(currentDate) || listDate.after(currentDate))
                newList.add(a);
        }
        return newList;
    }
}
