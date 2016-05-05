/**
 *
 * @author Dokuru
 */

package com.template.spring.dao.impl;

import com.template.spring.dao.ResetPasswordDAO;
import com.template.spring.domain.ChangePassword;
import java.sql.ResultSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ResetPasswordDAOImpl implements ResetPasswordDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public int resetPassword(ChangePassword passwords, String user, String table) {
        String sql = "select password from MS_Final." + table + " where netID = '" + user + "';";
        List<String> pwd  = getJdbcTemplate().query(sql, (ResultSet rs, int rowNum) -> rs.getString("password")+"");
        
        if(!pwd.isEmpty()) {
            if(!passwords.getcPWD().equals(pwd.get(0))) 
                return -2;
            else {
                sql = "update MS_Final." + table + " set password = '" + passwords.getnPWD() + "' where netID = '" + user + "';";
                int commit = getJdbcTemplate().update(sql, new Object[]{});
                return commit;
            }
        }
        
        return 0;
    }
    
}
