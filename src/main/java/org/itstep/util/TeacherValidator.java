package org.itstep.util;


import org.itstep.model.Teacher;
import org.itstep.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component

public class TeacherValidator implements Validator {

    private final TeacherService teacherService;

    @Autowired
    public TeacherValidator(TeacherService teacherService) {
        this.teacherService = teacherService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Teacher.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        //   Specialization specialization= (Specialization) o;
        if( teacherService.findByTeacherName(((Teacher) o).getTeacherName())!=null)
            errors.rejectValue("teacherName", "","Такой преподаватель уже есть!");
    }
}
