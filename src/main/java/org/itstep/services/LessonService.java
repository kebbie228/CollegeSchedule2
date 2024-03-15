package org.itstep.services;



import org.itstep.model.Group;
import org.itstep.model.Lesson;
import org.itstep.model.Teacher;
import org.itstep.repositories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonService {
    private final LessonRepository lessonRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }


    public List<Lesson> findAll() {
        return lessonRepository.findAll();
    }

    public Lesson findById(Long id) {
        Optional<Lesson> foundLesson = lessonRepository.findById(id);
        return foundLesson.orElse(null);
    }

    public void save(Lesson lesson) {
        lessonRepository.save(lesson);
    }

    public void update(Long id, Lesson updatedLesson) {
        updatedLesson.setId(id);
        lessonRepository.save(updatedLesson);
    }

    public void delete(Long id) {
        lessonRepository.deleteById(id);
    }

    public Lesson findByLessonName(String lessonName){
        Optional<Lesson> foundLesson= Optional.ofNullable(lessonRepository.findByLessonName(lessonName));
        return foundLesson.orElse(null);
    }

    public List<Lesson> findByGroups(Group group){
        return lessonRepository.findByGroups(group);
    }
    public List<Lesson> findByTeachers(Teacher teacher){
        return lessonRepository.findByTeachers(teacher);
    }
}

