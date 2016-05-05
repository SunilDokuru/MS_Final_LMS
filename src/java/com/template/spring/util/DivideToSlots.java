/**
 *
 * @author Dokuru
 */
package com.template.spring.util;

import com.template.spring.domain.Schedule2;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DivideToSlots {
    public List<Schedule2> divideTimeFrameToSlots(List<Schedule2> scheduleList) throws ParseException {
        List<Schedule2> resultList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        for(Schedule2 s: scheduleList) {
            String dateFromList = s.getDate();
            String dayOfWeek = s.getDayOfWeek();
           
            Time startTime = s.getStartTime();
            Time endTime = s.getEndTime();
           
            while(startTime.getTime() < endTime.getTime()) {
                String newSTime = startTime + "";
                String newETime;
               
                Date d = sdf.parse(newSTime);
                Calendar cal = Calendar.getInstance();
                cal.setTime(d);
                cal.add(Calendar.MINUTE, 30);
                
                newETime = sdf.format(cal.getTime());
                
                Time newStartTime = new java.sql.Time(sdf.parse(newSTime).getTime());
                Time newEndTime = new java.sql.Time(sdf.parse(newETime).getTime());
                
                resultList.add(new Schedule2(dateFromList, dayOfWeek, newStartTime, newEndTime));
                startTime = new java.sql.Time(sdf.parse(newETime).getTime());
            }
        }
            
       return resultList;
    }
}
