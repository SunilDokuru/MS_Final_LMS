/**
 *
 * @author Dokuru
 */
package com.template.spring.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FindAttendanceDates {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar c = Calendar.getInstance();
    DateFormat format = new SimpleDateFormat("E");
    List<String> scheduledDays = new ArrayList<>();
    Map<String, Integer> offSets = new HashMap<>();
    
    public LinkedHashMap<String, String> getAttendanceDays(String startDate, String endDate, String daysScheduled) throws ParseException {
        LinkedHashMap<String, String> toGo = new LinkedHashMap<>();
        daysScheduled = daysScheduled.replaceAll(" ", "");
        
        for(char day: daysScheduled.toCharArray()) {
            scheduledDays.add(day + "");
            
            if (day == 'M') {
                offSets.put("M", 1);
            } else if (day == 'T') {
                offSets.put("T", 2);
            } else if (day == 'W') {
                offSets.put("W", 3);
            } else if (day == 'R') {
                offSets.put("R", 4);
            } else if (day == 'F') {
                offSets.put("F", 5);
            } else if (day == 'S') {
                offSets.put("S", 6);
            } else {
                offSets.put("U", 7);
            }
        }
        if (!offSets.containsKey("U")) {
            offSets.put("U", 7);
        }
                
        // Get the Day of the StartDate
		Date start = sdf.parse(startDate);

		String startDay = format.format(start).toUpperCase().charAt(0)+"";
		
		boolean condition;
		
		// Checking if the startDate is one of the scheduledDays.
		condition = checkStartDays(startDay);
		
		String scheduledDate = startDate;
		boolean  Continue = true;

		
		/*		Getting the Correct Start Date as per the Scheduled Dates
		 * 
		 * 			Example: 	If the StartingDate is the 14th of January 2017 which is Saturday and
		 * 							the class is scheduled on a Wednesday, and Thursday, then we need
		 * 							the exact starting date which is the date of Wednesday or the first
		 * 							Scheduled day of the class. 
		 * */
		if(condition);
		else {
			while(Continue) {
				c.setTime(sdf.parse(scheduledDate));
				c.add(Calendar.DATE, 1); 
				scheduledDate = sdf.format(c.getTime());
				
				start = sdf.parse(scheduledDate);
				startDay = format.format(start);
				
				Continue = checkStartDays2(startDay.toUpperCase().charAt(0)+"");
			}
		}	//	END
		
		// Update start day of the class; stored in ScheduledDate, here 16th Jan 2017
		
		// Converting or parsing the date in String format to Date format
		Date currentDate = sdf.parse(scheduledDate);		// Getting the exact startDate
		Date eDate = sdf.parse(endDate);							// Getting the endDate from the system
		
		String currentDay = getDay(scheduledDate).toUpperCase().charAt(0)+"", nextDay;
		toGo.put(scheduledDate, currentDay);
		
		// Store the list of all the scheduled Dates of the class in arrayList until the end Date
		while(currentDate.compareTo(eDate) < 0 || currentDate.before(eDate)) {
			nextDay = getNextDay(currentDay);
			String nextDate = getNextDate(scheduledDate, currentDay, nextDay);

			currentDay = nextDay;
			scheduledDate = nextDate;

			currentDate = sdf.parse(scheduledDate);
			if(currentDate.before(eDate))	toGo.put(nextDate, currentDay);
		}
		return toGo;
	}
	public String getNextDate(String scheduledDate, String currentDay, String nextDay) throws ParseException {
		int offSet = getOffSets(currentDay, nextDay);
		c.setTime(sdf.parse(scheduledDate));
		c.add(Calendar.DATE, offSet);  				// adds offSet number of days to the scheduledDate
		
		return sdf.format(c.getTime());  // newDate is now the new date
		
	}
	public int getOffSets (String currentDay, String nextDay) {
		int currentOffSet = offSets.get(currentDay);
		int nextOffSet = offSets.get(nextDay);
		
		if(nextOffSet <= currentOffSet) {
			return (offSets.get("U") - currentOffSet) + nextOffSet;
		}
		else {
			return Math.abs(nextOffSet - currentOffSet);
		}
	}
	
	public boolean checkStartDays(String startDay) {
		int i = 0;
		while(i < scheduledDays.size()) {
			String temp = scheduledDays.get(i);
			if(startDay.equals(temp.toUpperCase()))
				return true;
			i++;
		}
		return false;
	}
	public boolean checkStartDays2(String startDay) {
		int i = 0;
		while(i < scheduledDays.size()) {
			String temp = scheduledDays.get(i);
			if(startDay.equals(temp.toUpperCase()))
				return false;
         i++;
		}
		return true;
	}
	public String getDay(String date) throws ParseException {
	    c.setTime(sdf.parse(date));
	    date = sdf.format(c.getTime());
	    Date d = sdf.parse(date);
	    
	    return format.format(d);
	}
	
	public String getNextDay(String currentDay) {
		int i = 0;
		boolean condition = true; String day = "";
		while(condition) {
			if((currentDay.toUpperCase().charAt(0)+"").equals(scheduledDays.get(i))) {
				if(i < scheduledDays.size() - 1) {
					condition = false;
					day = scheduledDays.get(i + 1);
				} else {
					condition = false;
					day =  scheduledDays.get(0);
				}
			}
			i++;
		}
		return day;
	}
}
