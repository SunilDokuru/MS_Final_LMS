/**
 *
 * @author Dokuru
 */

package com.template.spring.dao;

import java.util.HashMap;

public interface StudentDetailsDAO {
    public HashMap<String, String> getUserInfo(String netID);
}
