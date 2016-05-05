/**
 *
 * @author Dokuru
 */

package com.template.spring.domain;

import org.springframework.web.multipart.MultipartFile;

public class ProfilePicture {
    private MultipartFile profilePicture;

    public MultipartFile getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(MultipartFile profilePicture) {
        this.profilePicture = profilePicture;
    }
    
    
}
