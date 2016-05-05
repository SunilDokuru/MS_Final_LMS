/**
 *
 * @author Dokuru
 */

package com.template.spring.service.impl;

import com.template.spring.dao.RegisterUsersDAO;
import com.template.spring.domain.RegistrationData;
import com.template.spring.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private RegisterUsersDAO registerUsersDAO;

    public RegisterUsersDAO getRegisterUsersDAO() {
        return registerUsersDAO;
    }

    public void setRegisterUsersDAO(RegisterUsersDAO registerUsersDAO) {
        this.registerUsersDAO = registerUsersDAO;
    }
    
    @Override
    public String checkUser(RegistrationData userData) {
        return registerUsersDAO.checkUser(userData);
    }

    @Override
    public int registerUserData(RegistrationData userData, String user_type) {
        return registerUsersDAO.registerUserData(userData, user_type);
    }

    @Override
    public boolean checkEmail(RegistrationData userData) {
        return registerUsersDAO.checkEmail(userData);
    }
    
}
