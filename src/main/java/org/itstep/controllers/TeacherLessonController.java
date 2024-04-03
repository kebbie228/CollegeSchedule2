package org.itstep.controllers;

import org.itstep.model.*;
import org.itstep.services.*;
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
    private final ScheduleTeacherService scheduleTeacherService;
    private final ScheduleService scheduleService;

    @Autowired
    public TeacherLessonController(TeacherLessonService teacherLessonService, TeacherService teacherService,
                                   LessonService lessonService, ScheduleTeacherService scheduleTeacherService, ScheduleService scheduleService) {
        this.teacherLessonService = teacherLessonService;
        this.teacherService = teacherService;
        this.lessonService = lessonService;
        this.scheduleTeacherService = scheduleTeacherService;
        this.scheduleService = scheduleService;
    }

    @DeleteMapping("/deleteLessonFromTeacher")
    public String delete(@RequestParam("teacherId") Long teacherId, @RequestParam("lessonId") Long lessonId){
        Teacher teacher=  teacherService.findById(teacherId);
        Lesson lesson= lessonService.findById(lessonId);
        teacherLessonService.delete(teacherLessonService.findByTeacherAndAndLesson(teacher,lesson));

        for(ScheduleTeacher scheduleTeacher: teacher.getScheduleTeacherList()){
           if( scheduleTeacher.getLesson()==lesson){
            scheduleTeacher.setLesson(null);
            scheduleTeacher.setAudience(null);
            scheduleTeacher.setGroup(null);
           }
            scheduleTeacherService.save(scheduleTeacher);
        }


        for(Schedule schedule: teacher.getScheduleList()){
            if( schedule.getLesson()==lesson){
                schedule.setLesson(null);
                schedule.setAudience(null);
                schedule.setTeacher(null);
            }
            scheduleService.save(schedule);
        }

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
