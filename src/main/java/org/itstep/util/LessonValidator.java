package org.itstep.util;

import org.itstep.model.Lesson;
import org.itstep.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component

public class LessonValidator  implements Validator {

    private final LessonService lessonService;

    @Autowired
    public LessonValidator(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Lesson.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        //   Specialization specialization= (Specialization) o;

        if( lessonService.findByLessonName(((Lesson) o).getLessonName())!=null)
            errors.rejectValue("lessonName", "","Название предмета должно быть уникальным!");
    }
}
