package org.itstep.util;

import org.itstep.model.Group;
import org.itstep.model.Schedule;
import org.itstep.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ScheduleValidator implements Validator {
    private final ScheduleService scheduleService;
    @Autowired
    public ScheduleValidator(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Schedule.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if(scheduleService.hasSchedule(((Schedule) o).getGroup(), ((Schedule) o).getDay(), ((Schedule) o).getPara())==true)
            errors.rejectValue("id", "","В этот день уже есть пара!");
    }
}
