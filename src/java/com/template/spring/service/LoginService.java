/**
 *
 * @author Dokuru
 */
package com.template.spring.service;

import com.template.spring.domain.VerifyUserLoginData;
import java.sql.Timestamp;

public interface LoginService {
    public void addLoggedUserSessionData (VerifyUserLoginData loggedUser, Timestamp timeStamp);
    public String verifyLoggedUserExistence (VerifyUserLoginData loggedUser);
    public boolean verifyUserCredentials(String user_type, VerifyUserLoginData verifyUserLoginData);
}
