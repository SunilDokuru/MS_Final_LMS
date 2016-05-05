/**
 *
 * @author Dokuru
 */
package com.template.spring.dao;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public interface GetUserCoursesDAO {
    public List<LinkedHashMap> getUserCourses(String userID, String tableName);
    public HashMap<String, String> getCourseContent(String courseID);
}
