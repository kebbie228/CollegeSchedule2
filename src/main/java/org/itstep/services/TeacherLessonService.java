package org.itstep.services;

import org.itstep.model.*;
import org.itstep.repositories.TeacherLessonRepository;
import org.springframework.stereotype.Service;

@Service
public class TeacherLessonService {
    private final TeacherLessonRepository teacherLessonRepository;

    public TeacherLessonService(TeacherLessonRepository teacherLessonRepository) {
        this.teacherLessonRepository = teacherLessonRepository;
    }

    public TeacherLesson findByTeacherAndAndLesson(Teacher teacher, Lesson lesson){
        return teacherLessonRepository.findByTeacherAndAndLesson(teacher,lesson);
    }
    public void delete(TeacherLesson teacherLesson){
        teacherLessonRepository.delete(teacherLesson);
    }
    public void save(TeacherLesson teacherLesson){
        teacherLessonRepository.save(teacherLesson);
    }



}
