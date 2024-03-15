package org.itstep.controllers;

import org.itstep.model.*;
import org.itstep.services.GroupLessonService;
import org.itstep.services.LessonService;
import org.itstep.services.TeacherLessonService;
import org.itstep.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/teacherLesson")
public class TeacherLessonController {
    private final TeacherLessonService teacherLessonService;
    private final TeacherService teacherService;
    private final LessonService lessonService;

    @Autowired
    public TeacherLessonController( TeacherLessonService teacherLessonService, TeacherService teacherService, LessonService lessonService) {
        this.teacherLessonService = teacherLessonService;
        this.teacherService = teacherService;
        this.lessonService = lessonService;
    }

    @DeleteMapping("/deleteLessonFromTeacher")
    public String delete(@RequestParam("teacherId") Long teacherId, @RequestParam("lessonId") Long lessonId){
        Teacher teacher=  teacherService.findById(teacherId);
        Lesson lesson= lessonService.findById(lessonId);
        teacherLessonService.delete(teacherLessonService.findByTeacherAndAndLesson(teacher,lesson));
        String redirectUrl = "redirect:/lessons/teacher/" + teacherId;
        return redirectUrl;
    }

    @PostMapping("/addLessonToTeacher")
    public String addLessonToGroup(@ModelAttribute("teacherLesson") TeacherLesson teacherLesson,
                                   @RequestParam("lesson.id") Long lessonId,
                                   @RequestParam("teacherId") Long teacherId,
                                   Model model
    ) {
        Teacher teacher=teacherService.findById(teacherId);
        Lesson lesson= lessonService.findById(lessonId);
        teacherLesson.setTeacher(teacher);
        teacherLesson.setLesson(lesson);
        teacherLessonService.save(teacherLesson);
        String redirectUrl = "redirect:/lessons/teacher/" + teacherId;
        return redirectUrl;
    }
}
