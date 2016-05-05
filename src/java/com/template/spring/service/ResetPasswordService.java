/**
 *
 * @author Dokuru
 */
package com.template.spring.service;

import com.template.spring.domain.ChangePassword;
import org.springframework.stereotype.Service;

@Service
public interface ResetPasswordService {
    public int resetPassword(ChangePassword password, String user, String table);
}
