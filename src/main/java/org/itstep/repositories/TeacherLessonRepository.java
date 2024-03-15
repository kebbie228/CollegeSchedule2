package org.itstep.repositories;

import org.itstep.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherLessonRepository extends JpaRepository<TeacherLesson,Long> {
    TeacherLesson findByTeacherAndAndLesson(Teacher teacher, Lesson lesson);

}