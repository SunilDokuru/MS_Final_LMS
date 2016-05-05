/**
 *
 * @author Dokuru
 */

package com.template.spring.service;

import com.template.spring.domain.RegistrationData;

public interface RegistrationService {
    public String checkUser(RegistrationData userData);
    public int registerUserData(RegistrationData userData, String user_type);
    public boolean checkEmail(RegistrationData userData);
}
