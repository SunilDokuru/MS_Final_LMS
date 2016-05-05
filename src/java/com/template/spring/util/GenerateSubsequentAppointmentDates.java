/**
 *
 * @author Dokuru
 */
package com.template.spring.util;

import com.template.spring.domain.AdminSchedule2;
import com.template.spring.domain.Schedule2;
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
public class GenerateSubsequentAppointmentDates {
    public List<Schedule2> generateList(AdminSchedule2 schedule, String endDate) throws ParseException {
        List<Schedule2> resultList = new ArrayList<>();
        
        String date = schedule.getDate();
        String dayOfWeek = schedule.getDayOfWeek();
        String start = schedule.getStartTime();
        String end = schedule.getEndTime();
        
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        
        Time startTime = new java.sql.Time(formatter.parse(start).getTime());
        Time endTime = new java.sql.Time(formatter.parse(end).getTime());
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date loopEndDate = sdf.parse(endDate);
        
        sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date checkDate = sdf.parse(date);
        
        while(checkDate.before(loopEndDate)) {
            resultList.add(new Schedule2(date, dayOfWeek, startTime, endTime));
            
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
        return resultList;
    }
}
