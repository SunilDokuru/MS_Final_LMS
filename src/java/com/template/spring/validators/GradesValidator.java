/**
 *
 * @author Dokuru
 */

package com.template.spring.validators;

import com.template.spring.domain.Grades;
import com.template.spring.domain.GradesList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class GradesValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return GradesList.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        GradesList list = (GradesList)obj;
        
        List<Grades> fromForm = list.getGradesList();
        int i = 0;
        for(Grades g: fromForm) {
            if(g.getScore() == null) {
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gradesList[" + i + "]", "scores.required");
                errors.rejectValue("gradesList[" + i + "]", "scores.required");
            }
            i++;
        }
    }
    
}
