package com.template.spring.dao.impl;

/**
 *
 * @author Dokuru
 */


import com.template.spring.dao.LoggedUsersDB;
import com.template.spring.domain.VerifyUserLoginData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LoggedUsersDBImpl implements LoggedUsersDB{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }

    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public void insertRecord (VerifyUserLoginData loggedUser, Timestamp timeStamp) {
        String sql = "insert into MS_Final.LoggedUsers values(?, ?)";
        
        //Object[] parameters = new Object[] {loggedUser.getNetID(), timeStamp};
        //int[] types = new int[] {Types.VARCHAR, Types.TIMESTAMP};
        //jdbcTemplate.update(sql, params, types);
        
        
        jdbcTemplate.update(sql,new Object[]{loggedUser.getNetID(), timeStamp});
    }
    
    @Override
    public String verifyRecord (VerifyUserLoginData verifyLoginData) {
        
        String sql = "select user_type from users where netID='"+verifyLoginData.getNetID()+"';";
        
        
        /*  Lambda expression for below RowMapper
         *  List<String> userList  = getJdbcTemplate().query(sql, (ResultSet rs, int rowNum) -> rs.getString("user_type"));
         */

        List<String> userList  = getJdbcTemplate().query(sql,new RowMapper() {
                @Override
                public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString("user_type");
                }
        });
     

        if ( userList.isEmpty() ){
            return null;
        }else 
            return userList.get(0);
    }
    
    @Override
    public boolean verifyUserCredentials(String user_type, VerifyUserLoginData verifyUserLoginData) {
        String query = "Select userID from " + user_type + " where netID = '"+verifyUserLoginData.getNetID()+"' and password = '"+verifyUserLoginData.getPassword()+"';";
       
        List<String> userList  = getJdbcTemplate().query(query, (ResultSet rs, int rowNum) -> rs.getString("userID")+"");
        
        if (0 == userList.size())
            return false;
        else return userList.get(0).length() != 0;
    }
}
