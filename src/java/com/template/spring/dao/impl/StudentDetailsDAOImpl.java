/**
 *
 * @author Dokuru
 */
package com.template.spring.dao.impl;

import com.template.spring.dao.StudentDetailsDAO;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDetailsDAOImpl implements StudentDetailsDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    
    @Override
    public HashMap<String, String> getUserInfo(String netID) {
        String sql = "select netID, userID, user_type, date_of_birth, firstName, lastName, homeCountry, status, ethnicity, city, department from MS_Final.users where netID = '" + netID + "';"; 
        
       /* List<Student> results = getJdbcTemplate().query("sql", new Object[]{data.getNetID()}, (ResultSet rs, int rowNum) -> new Student(rs.getString("firstName"), rs.getString("lastName"), rs.getString("country"), rs.getString("status"), rs.getString("ethnicity"), rs.getString("city"), rs.getString("department")));
        
        System.out.println(results.size());
        
        if(results.size() > 0) {
            System.out.println(results.get(0).getFirstName());
        }
        if(!results.isEmpty())
            return results.get(0);
        else
            return null;
    }*/
        
        LinkedHashMap<String, String> details = new LinkedHashMap<>();
        
        List results = getJdbcTemplate().queryForList(sql);
        
        for (Object result : results) {
            LinkedHashMap map = (LinkedHashMap) result;
            map.keySet().stream().forEach((key) -> {
                details.put(key+"", map.get(key)+"");
            });
        }
        
        if(!details.isEmpty())  return details;
        else    return null;
    }
}
