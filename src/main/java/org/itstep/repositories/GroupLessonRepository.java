package org.itstep.repositories;

import org.itstep.model.Group;
import org.itstep.model.GroupLesson;
import org.itstep.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupLessonRepository extends JpaRepository<GroupLesson,Long> {
    GroupLesson findByGroupAndLesson(Group group, Lesson lesson);

}
