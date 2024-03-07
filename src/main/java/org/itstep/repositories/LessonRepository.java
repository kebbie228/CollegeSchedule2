package org.itstep.repositories;


import org.itstep.model.Lesson;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson,Long> {
    Lesson findByLessonName (String lessonName);
}
