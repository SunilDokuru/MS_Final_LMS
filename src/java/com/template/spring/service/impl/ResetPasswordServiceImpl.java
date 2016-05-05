/**
 *
 * @author Dokuru
 */

package com.template.spring.service.impl;

import com.template.spring.dao.ResetPasswordDAO;
import com.template.spring.domain.ChangePassword;
import com.template.spring.service.ResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordServiceImpl implements ResetPasswordService {

    @Autowired
    private ResetPasswordDAO resetPasswordDAO;

    public ResetPasswordDAO getResetPasswordDAO() {
        return resetPasswordDAO;
    }

    public void setResetPasswordDAO(ResetPasswordDAO resetPasswordDAO) {
        this.resetPasswordDAO = resetPasswordDAO;
    }
    
    
    @Override
    public int resetPassword(ChangePassword password, String user, String table) {
        return resetPasswordDAO.resetPassword(password, user, table);
    }
    
}
