/**
 *
 * @author Dokuru
 */

package com.template.spring.dao;

import com.template.spring.domain.ChangePassword;

public interface ResetPasswordDAO {
    public int resetPassword(ChangePassword passwords, String user, String table);
}
