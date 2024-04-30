package org.itstep.controllers;

import org.itstep.model.*;
import org.itstep.security.PersonDetails;
import org.itstep.services.*;
import org.itstep.util.LessonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/lessons")
public class LessonController {

    private final LessonService lessonService;

    private  final LessonValidator lessonValidator;
    private final GroupService groupService;
    private final GroupLessonService groupLessonService;
    private final TeacherService teacherService;
    private final TeacherLessonService teacherLessonService;
    private final ScheduleService scheduleService;

    @Autowired
    public LessonController(LessonService lessonService, LessonValidator lessonValidator, GroupService groupService, GroupLessonService groupLessonService, TeacherService teacherService, TeacherLessonService teacherLessonService, ScheduleService scheduleService) {
        this.lessonService = lessonService;
        this.lessonValidator = lessonValidator;
        this.groupService = groupService;
        this.groupLessonService = groupLessonService;
        this.teacherService = teacherService;
        this.teacherLessonService = teacherLessonService;
        this.scheduleService = scheduleService;
    }


    @GetMapping()
    public String show(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if( authentication.getPrincipal().equals("anonymousUser")) {
            model.addAttribute("person", null);
            return "redirect:/search";
        }
        else {
            PersonDetails personDetails= (PersonDetails)authentication.getPrincipal();
            model.addAttribute("person",personDetails.getPerson());
        }
        model.addAttribute("lessons", lessonService.findAll());
        return "lessons/show";
    }

    @GetMapping("/{id}")
    public String index(@PathVariable("id") Long id, Model model, @ModelAttribute("lesson") Lesson lesson) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if( authentication.getPrincipal().equals("anonymousUser")) {
            model.addAttribute("person", null);
            return "redirect:/search";
        }
        else {
            PersonDetails personDetails= (PersonDetails)authentication.getPrincipal();
            model.addAttribute("person",personDetails.getPerson());
        }
        model.addAttribute("lesson", lessonService.findById(id));
        return "lessons/index";
    }
    @GetMapping("/new")
    public String newLesson(Model model, @ModelAttribute("lesson") Lesson lesson) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if( authentication.getPrincipal().equals("anonymousUser")) {
            model.addAttribute("person", null);
            return "redirect:/search";
        }
        else {
            PersonDetails personDetails= (PersonDetails)authentication.getPrincipal();
            model.addAttribute("person",personDetails.getPerson());
        }

        return "lessons/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("lesson") @Valid Lesson lesson,
                         BindingResult bindingResult) {
        lessonValidator.validate(lesson,bindingResult);

        if (bindingResult.hasErrors())
            return "lessons/new";
        lessonService.save(lesson);
        return "redirect:/lessons";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if( authentication.getPrincipal().equals("anonymousUser")) {
            model.addAttribute("person", null);
            return "redirect:/search";
        }
        else {
            PersonDetails personDetails= (PersonDetails)authentication.getPrincipal();
            model.addAttribute("person",personDetails.getPerson());
        }

        model.addAttribute("lesson", lessonService.findById(id));
        return "lessons/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("lesson") @Valid Lesson lesson, BindingResult bindingResult,
                         @PathVariable("id") Long id) {
        lessonValidator.validate(lesson,bindingResult);
        if (bindingResult.hasErrors())
            return "lessons/edit";

        lessonService.update(id,lesson);
        return "redirect:/lessons";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
       Lesson lesson= lessonService.findById(id);
        for(Group group: lesson.getGroups()){
            group.getLessons().remove(lesson);
            groupService.save(group);
        }
        for(Teacher teacher: lesson.getTeachers()){

            teacher.getTeacherLessons().remove(lesson);
            teacherService.save(teacher);
        }

        lesson.getScheduleList().forEach(schedule -> {
            schedule.setLesson(null);
            schedule.setAudience(null);
            schedule.setTeacher(null);
        });



        lessonService.delete(id);
        return "redirect:/lessons";
    }
    @GetMapping("/group/{id}")
    public String listGroupLessons(Model model,@PathVariable("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if( authentication.getPrincipal().equals("anonymousUser")) {
            model.addAttribute("person", null);
        }
        else {
            PersonDetails personDetails= (PersonDetails)authentication.getPrincipal();
            model.addAttribute("person",personDetails.getPerson());
        }
        Group group = groupService.findById(id);
        model.addAttribute("group", group);
        model.addAttribute("lessons",lessonService.findByGroups(group));
        return "lessons/groupLessons";
    }

    @GetMapping("/teacher/{id}")
    public String listTeacherLessons(Model model,@PathVariable("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if( authentication.getPrincipal().equals("anonymousUser")) {
            model.addAttribute("person", null);
        }
        else {
            PersonDetails personDetails= (PersonDetails)authentication.getPrincipal();
            model.addAttribute("person",personDetails.getPerson());
        }
        Teacher teacher = teacherService.findById(id);
        model.addAttribute("teacher", teacher);
        model.addAttribute("lessons",lessonService.findByTeachers(teacher));
        return "lessons/teacherLessons";
    }

    @GetMapping("/group/{id}/add")
    public String addLessonToGroup(Model model,@PathVariable("id") Long id, @ModelAttribute("groupLesson") GroupLesson groupLesson) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if( authentication.getPrincipal().equals("anonymousUser")) {
            model.addAttribute("person", null);
            return "redirect:/search";
        }
        else {
            PersonDetails personDetails= (PersonDetails)authentication.getPrincipal();
            model.addAttribute("person",personDetails.getPerson());
        }

        Group group = groupService.findById(id);
        model.addAttribute("group", group);
        List<Lesson> lessons = lessonService.findAll();
        List<Lesson> lessonsNoAdd = new ArrayList<>();
        for (Lesson lesson : lessons) {
            long lessonId = lesson.getId();
            if (groupLessonService.findByGroupAndLesson(group, lessonService.findById(lessonId))==null) {

                lessonsNoAdd.add(lessonService.findById(lessonId));
            }
        }
        model.addAttribute("lessonsNoAdd", lessonsNoAdd);
        return "lessons/addLessonToGroup";
    }

    @GetMapping("/teacher/{id}/add")
    public String addLessonToTeacher(Model model,@PathVariable("id") Long id, @ModelAttribute("teacherLesson") TeacherLesson teacherLesson) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if( authentication.getPrincipal().equals("anonymousUser")) {
            model.addAttribute("person", null);
            return "redirect:/search";
        }
        else {
            PersonDetails personDetails= (PersonDetails)authentication.getPrincipal();
            model.addAttribute("person",personDetails.getPerson());
        }

        Teacher teacher = teacherService.findById(id);
        model.addAttribute("teacher", teacher);
        List<Lesson> lessons = lessonService.findAll();
        List<Lesson> lessonsNoAdd = new ArrayList<>();
        for (Lesson lesson : lessons) {
            long lessonId = lesson.getId();
            if (teacherLessonService.findByTeacherAndAndLesson(teacher, lessonService.findById(lessonId))==null) {
                lessonsNoAdd.add(lessonService.findById(lessonId));
            }
        }
        model.addAttribute("lessonsNoAdd", lessonsNoAdd);
        return "lessons/addLessonToTeacher";
    }


}


