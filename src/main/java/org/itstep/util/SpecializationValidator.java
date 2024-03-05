package org.itstep.util;

import org.itstep.model.Specialization;
import org.itstep.services.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class SpecializationValidator implements Validator {

   private final SpecializationService specializationService;

    @Autowired
    public SpecializationValidator(SpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Specialization.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
     //   Specialization specialization= (Specialization) o;

       if( specializationService.findBySpecializationName(((Specialization) o).getSpecializationName())!=null)
           errors.rejectValue("specializationName", "","Специальность должна быть уникальной");//&&&&&&&&&&&&&&&&&&&&&
    }
}
