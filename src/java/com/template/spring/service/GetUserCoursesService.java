/**
 *
 * @author Dokuru
 */
package com.template.spring.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public interface GetUserCoursesService {
    public List<LinkedHashMap> getUserCourses(String userID, String tableName);
    public HashMap<String, String> getCourseContent(String courseID);
}
