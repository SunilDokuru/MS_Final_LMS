/**
 *
 * @author Dokuru
 */

package com.template.spring.service.impl;

import com.template.spring.dao.MakeAppointmentsDAOService;
import com.template.spring.domain.Schedule;
import com.template.spring.service.MakeAppointmentsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MakeAppointmentsServiceImpl implements MakeAppointmentsService {
    
    @Autowired
    private MakeAppointmentsDAOService makeAppointmentsDAOService;

    public MakeAppointmentsDAOService getMakeAppointmentsDAOService() {
        return makeAppointmentsDAOService;
    }

    public void setMakeAppointmentsDAOService(MakeAppointmentsDAOService makeAppointmentsDAOService) {
        this.makeAppointmentsDAOService = makeAppointmentsDAOService;
    }
    
    @Override
    public List<Schedule> getOpenSlots(Integer courseID, Integer facultyID) {
        return makeAppointmentsDAOService.getOpenSlots(courseID, facultyID);
    }
    
}
