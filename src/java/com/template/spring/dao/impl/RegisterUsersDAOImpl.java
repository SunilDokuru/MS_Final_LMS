/**
 *
 * @author Dokuru
 */

package com.template.spring.dao.impl;

import com.template.spring.dao.RegisterUsersDAO;
import com.template.spring.domain.RegistrationData;
import java.sql.ResultSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class RegisterUsersDAOImpl implements RegisterUsersDAO {

//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
//    public BCryptPasswordEncoder getPasswordEncoder() {
//        return passwordEncoder;
//    }
//
//    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }
    
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public String checkUser(RegistrationData userData) {
        
        String sql = "select user_type from users where netID='"+userData.getNetID()+"';";
        List<String> userList  = getJdbcTemplate().query(sql, (ResultSet rs, int rowNum) -> rs.getString("user_type"));
        
        
        if ( userList.isEmpty() ){
            return null;
        } else {
            sql = "select user_type from users where userID='"+userData.getUserID()+"';";
            userList  = getJdbcTemplate().query(sql, (ResultSet rs, int rowNum) -> rs.getString("user_type"));
            
            if(userList.isEmpty())
                return null;
        }
        
        sql = "select user_type from users where userID='"+userData.getUserID()+"';";
        userList  = getJdbcTemplate().query(sql, (ResultSet rs, int rowNum) -> rs.getString("user_type"));
        
        if ( userList.isEmpty() ){
            return null;
        }
        else {
          sql = "select email from " + userList.get(0) + " where netID='" + userData.getNetID() +"';";
          List<String> userList1 = getJdbcTemplate().query(sql, (ResultSet rs, int rowNum) -> rs.getString("email"));
          
          if(userList1.isEmpty())
              return userList.get(0);
          else
              return "done";
        } 
    }

    @Override
    public int registerUserData(RegistrationData userData, String user_type) {
        String sql = "insert into MS_Final." + user_type + " values(?, ?, ?, ?, ?, ?)";
        
        return jdbcTemplate.update(sql,new Object[]{userData.getNetID(), userData.getUserID(), userData.getEmail(), userData.getSocial(), userData.getPassword(), userData.getPhone()});
    }

    @Override
    public boolean checkEmail(RegistrationData userData) {
        String sql = "select user_type from users where netID='"+userData.getNetID()+"';";
        List<String> userList  = getJdbcTemplate().query(sql, (ResultSet rs, int rowNum) -> rs.getString("user_type"));
        
        sql = "select email from " + userList.get(0) + " where netID='" + userData.getNetID() +"';";
        userList = getJdbcTemplate().query(sql, (ResultSet rs, int rowNum) -> rs.getString("email"));
        
        return userData.getEmail().equals(userList.get(0));
        
    }
    
}
