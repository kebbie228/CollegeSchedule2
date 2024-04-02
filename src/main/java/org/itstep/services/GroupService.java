package org.itstep.services;



import org.itstep.model.Group;
import org.itstep.model.Lesson;
import org.itstep.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    public Group findById(Long id) {
        Optional<Group> foundGroup = groupRepository.findById(id);
        return foundGroup.orElse(null);
    }

    public void save(Group group) {
        groupRepository.save(group);
    }

    public void update(Long id, Group updatedGroup) {
        updatedGroup.setId(id);
        groupRepository.save(updatedGroup);
    }

    public void delete(Long id) {
        groupRepository.deleteById(id);
    }

    public Group findByGroupName(String groupName){

        Optional<Group> foundGroup= Optional.ofNullable(groupRepository.findByGroupName(groupName));
        return foundGroup.orElse(null);
    }
    public List<Group> findByGroupNameContainingIgnoreCase(String firstLetters){
        return  groupRepository.findByGroupNameContainingIgnoreCase(firstLetters);
    }


}

