/**
 *
 * @author Dokuru
 */

package com.template.spring.util;

import com.template.spring.domain.AdminScheduleWithDay;
import com.template.spring.domain.Schedule;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GetDays {
    public List<AdminScheduleWithDay> getDaysFromDate(List<Schedule> list) throws ParseException {
        List<AdminScheduleWithDay> resultList = new ArrayList<>();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        
        for(Schedule s: list) {
            String date = s.getDate();
            String startTime = s.getStartTime()+"";
            String endTime = s.getEndTime()+"";
            
            Date d = formatter.parse(date);
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            String day = "NAN";
            switch(dayOfWeek) {
                case 1:
                    day = "Sunday";
                    break;
                case 2:
                    day = "Monday";
                    break;
                case 3:
                    day = "Tuesday";
                    break;
                case 4:
                    day = "Wednesday";
                    break;
                case 5:
                    day = "Thursday";
                    break;
                case 6:
                    day = "Friday";
                    break;
                case 7:
                    day = "Saturday";
                    break;
            }
            resultList.add(new AdminScheduleWithDay(date, startTime, endTime, day)); 
        }
        
        for(AdminScheduleWithDay s: resultList) {
            System.out.println(s.getDay());
        }
        return resultList;
    }
}
