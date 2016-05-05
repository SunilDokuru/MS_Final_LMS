/**
 *
 * @author Dokuru
 */

package com.template.spring.service;

import com.template.spring.domain.Schedule;
import java.util.List;

public interface MakeAppointmentsService {
    public List<Schedule> getOpenSlots(Integer courseID, Integer facultyID);
}
