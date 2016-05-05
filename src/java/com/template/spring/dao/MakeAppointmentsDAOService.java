/**
 *
 * @author Dokuru
 */
package com.template.spring.dao;

import com.template.spring.domain.Schedule;
import java.util.List;

public interface MakeAppointmentsDAOService {
    public List<Schedule> getOpenSlots(Integer courseID, Integer facultyID);
}
