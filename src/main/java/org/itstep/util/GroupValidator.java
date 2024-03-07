package org.itstep.util;

import org.itstep.model.Group;
import org.itstep.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class GroupValidator implements Validator {
    private final GroupService groupService;

    @Autowired
    public GroupValidator(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Group.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        //   Specialization specialization= (Specialization) o;
        if(groupService.findByGroupName(((Group) o).getGroupName())!=null)
            errors.rejectValue("groupName", "","Такая группа уже существует!");
    }
}
