/**
 *
 * @author Dokuru
 */

package com.template.spring.validators;

import com.template.spring.domain.ProfilePicture;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProfilePictureValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return ProfilePicture.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        
        ProfilePicture picture = (ProfilePicture)obj;
        
        if(picture.getProfilePicture().getSize() == 0)
            errors.rejectValue("profile-avatar", "picture.required");
        
        if(!picture.getProfilePicture().getContentType().equals("image/jpeg") || !picture.getProfilePicture().getContentType().equals("image/png"))
            errors.rejectValue("profile-avatar", "jpeg.required");
    }
    
}
