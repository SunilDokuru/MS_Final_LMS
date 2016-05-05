/**
 *
 * @author Dokuru
 */

package com.template.spring.domain;

import java.util.List;

public class AttendanceForm {
    private List<Attendance> userResponse;

    public List<Attendance> getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(List<Attendance> userResponse) {
        this.userResponse = userResponse;
    }
}
