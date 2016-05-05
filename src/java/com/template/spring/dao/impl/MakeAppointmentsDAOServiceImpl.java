/**
 *
 * @author Dokuru
 */

package com.template.spring.dao.impl;

import com.template.spring.dao.MakeAppointmentsDAOService;
import com.template.spring.domain.Schedule;
import java.sql.ResultSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MakeAppointmentsDAOServiceImpl implements MakeAppointmentsDAOService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public List<Schedule> getOpenSlots(Integer courseID, Integer facultyID) {
        
       
        return null;
    }
    
}
