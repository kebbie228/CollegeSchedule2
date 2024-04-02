package org.itstep.repositories;

import org.itstep.model.Group;

import org.itstep.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GroupRepository extends JpaRepository<Group,Long> {

    Group findByGroupName (String groupName);
    List<Group> findByGroupNameContainingIgnoreCase(String firstLetters);

}
