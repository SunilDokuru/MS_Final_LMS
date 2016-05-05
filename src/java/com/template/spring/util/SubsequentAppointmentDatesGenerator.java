/**
 *
 * @author Dokuru
 */

package com.template.spring.util;

import com.template.spring.domain.AdminSchedule;
import com.template.spring.domain.Schedule;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SubsequentAppointmentDatesGenerator {
    
    public List<Schedule> getAutoPopulatedScheduledList(AdminSchedule schedule, String endDate) throws ParseException {
        String date = schedule.getDate();
        
        String start = schedule.getStartTime();
        String end = schedule.getEndTime();
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        
        System.out.println("From Util Dates Generator in String format:\t" + date+"\tstartTime:\t" + start+"\tEndTime:\t" + end+"\n");
        Time startTime = new java.sql.Time(formatter.parse(start).getTime());
        Time endTime = new java.sql.Time(formatter.parse(end).getTime());
        
        System.out.println("From Util Dates Generator in Time format:\tstartTime:\t" + startTime+"\tEndTime:\t" + endTime+"\n");
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date loopEndDate = sdf.parse(endDate);
        
        sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date checkDate = sdf.parse(date);
        
        
        List<Schedule> list = new ArrayList<>();
        while(checkDate.before(loopEndDate)) {
            list.add(new Schedule(date, startTime, endTime, schedule.getStartTimeOfDay(), schedule.getEndTimeOfDay()));
            
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(date));
            
            c.add(Calendar.DATE, 7);
            String scheduledDate = sdf.format(c.getTime());
            Date temp = sdf.parse(scheduledDate);
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(temp);
		
            date = cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" +  cal.get(Calendar.DATE);
            
            checkDate = sdf.parse(date);
        }
        return list;
    }
}
