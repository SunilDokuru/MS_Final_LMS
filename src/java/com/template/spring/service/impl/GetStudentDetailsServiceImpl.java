/**
 *
 * @author Dokuru
 */

package com.template.spring.service.impl;

import com.template.spring.dao.StudentDetailsDAO;
import com.template.spring.domain.VerifyUserLoginData;
import com.template.spring.service.GetStudentDetailsService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetStudentDetailsServiceImpl implements GetStudentDetailsService {
    
    @Autowired
    private StudentDetailsDAO studentDetailsDAO;

    public StudentDetailsDAO getStudentDetailsDAO() {
        return studentDetailsDAO;
    }

    public void setStudentDetailsDAO(StudentDetailsDAO studentDetailsDAO) {
        this.studentDetailsDAO = studentDetailsDAO;
    }
    
    
    @Override
    public HashMap<String, String> getUserInfo(String netID) {
        return studentDetailsDAO.getUserInfo(netID);
    }
    
}
