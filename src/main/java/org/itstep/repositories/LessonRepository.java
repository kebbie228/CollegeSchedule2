package org.itstep.repositories;


import org.itstep.model.Group;
import org.itstep.model.Lesson;


import org.itstep.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson,Long> {
    Lesson findByLessonName (String lessonName);
    List<Lesson> findByGroups(Group group);
    List<Lesson> findByTeachers(Teacher teacher);
}
