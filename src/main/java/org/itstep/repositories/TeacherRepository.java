package org.itstep.repositories;


import org.itstep.model.Lesson;
import org.itstep.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    Teacher findByTeacherName (String teacherName);

}
