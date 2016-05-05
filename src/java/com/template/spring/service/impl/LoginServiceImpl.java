/**
 *
 * @author Dokuru
 */

package com.template.spring.service.impl;

import com.template.spring.dao.LoggedUsersDB;
import com.template.spring.domain.VerifyUserLoginData;
import com.template.spring.service.LoginService;
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService{

    @Autowired
    private LoggedUsersDB loggedUsersDB;

    public LoggedUsersDB getLoggedUsersDB() {
        return loggedUsersDB;
    }

    public void setLoggedUsersDB(LoggedUsersDB loggedUsersDB) {
        this.loggedUsersDB = loggedUsersDB;
    }

    
    
    @Override
    public void addLoggedUserSessionData(VerifyUserLoginData loggedUser, Timestamp timeStamp) {
        loggedUsersDB.insertRecord(loggedUser, timeStamp);
    }

    @Override
    public String verifyLoggedUserExistence(VerifyUserLoginData loggedUser) {
        return loggedUsersDB.verifyRecord(loggedUser);
    }
    
    @Override
    public boolean verifyUserCredentials(String user_type, VerifyUserLoginData verifyUserLoginData) {
        return loggedUsersDB.verifyUserCredentials(user_type, verifyUserLoginData);
    }

  }
