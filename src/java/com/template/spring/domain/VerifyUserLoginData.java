/**
 *
 * @author Dokuru
 */

package com.template.spring.domain;

import org.springframework.context.annotation.Scope;

@Scope("session")
public class VerifyUserLoginData {
    private String netID, password;

    public String getNetID() {
        return netID;
    }

    public void setNetID(String netID) {
        this.netID = netID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
