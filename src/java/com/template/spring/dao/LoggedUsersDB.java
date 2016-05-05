/**
 *
 * @author Dokuru
 */package com.template.spring.dao;

import com.template.spring.domain.VerifyUserLoginData;
import java.sql.Timestamp;

public interface LoggedUsersDB {
    public String verifyRecord (VerifyUserLoginData verifyLoginData);
    public boolean verifyUserCredentials(String user_type, VerifyUserLoginData verifyUserLoginData);
    public void insertRecord (VerifyUserLoginData loggedUser, Timestamp timeStamp);
}
