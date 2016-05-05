/**
 *
 * @author Dokuru
 */

package com.template.spring.service.impl;

import com.template.spring.dao.GetUserCoursesDAO;
import com.template.spring.service.GetUserCoursesService;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetUserCoursesServiceImpl implements GetUserCoursesService {
    @Autowired
    private GetUserCoursesDAO getUserCoursesDAO;

    public GetUserCoursesDAO getGetUserCoursesDAO() {
        return getUserCoursesDAO;
    }

    public void setGetUserCoursesDAO(GetUserCoursesDAO getUserCoursesDAO) {
        this.getUserCoursesDAO = getUserCoursesDAO;
    }
    
    @Override
    public List<LinkedHashMap> getUserCourses(String userID, String tableName) {
        return getUserCoursesDAO.getUserCourses(userID, tableName);
    }

    @Override
    public HashMap<String, String> getCourseContent(String courseID) {
        return getUserCoursesDAO.getCourseContent(courseID);
    }
}
