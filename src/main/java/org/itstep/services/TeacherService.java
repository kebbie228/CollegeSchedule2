package org.itstep.services;



import org.itstep.model.Group;
import org.itstep.model.Lesson;
import org.itstep.model.Teacher;
import org.itstep.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    public Teacher findById(Long id) {
        Optional<Teacher> foundTeacher = teacherRepository.findById(id);
        return foundTeacher.orElse(null);
    }
    public void save(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    public void update(Long id, Teacher updatedTeacher) {
        updatedTeacher.setId(id);
        teacherRepository.save(updatedTeacher);
    }
    public void delete(Long id) {
        teacherRepository.deleteById(id);
    }
    public Teacher findByTeacherName(String teacherName){
        Optional<Teacher> foundTeacher= Optional.ofNullable(teacherRepository.findByTeacherName(teacherName));
        return foundTeacher.orElse(null);
    }
    public List<Teacher> findByTeacherNameContainingIgnoreCase(String firstLetters){
        return  teacherRepository.findByTeacherNameContainingIgnoreCase(firstLetters);
    }
}

