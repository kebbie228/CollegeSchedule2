package org.itstep.repositories;

import org.itstep.model.Group;

import org.itstep.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GroupRepository extends JpaRepository<Group,Long> {

    Lesson findByGroupName (String groupName);

}
