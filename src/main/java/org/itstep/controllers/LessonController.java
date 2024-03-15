package org.itstep.controllers;

import org.itstep.model.*;
import org.itstep.services.*;
import org.itstep.util.LessonValidator;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public LessonController(LessonService lessonService, LessonValidator lessonValidator, GroupService groupService, GroupLessonService groupLessonService, TeacherService teacherService, TeacherLessonService teacherLessonService) {
        this.lessonService = lessonService;
        this.lessonValidator = lessonValidator;
        this.groupService = groupService;
        this.groupLessonService = groupLessonService;
        this.teacherService = teacherService;
        this.teacherLessonService = teacherLessonService;
    }


    @GetMapping()
    public String show(Model model){
        model.addAttribute("lessons", lessonService.findAll());
        return "lessons/show";
    }

    @GetMapping("/{id}")
    public String index(@PathVariable("id") Long id, Model model, @ModelAttribute("lesson") Lesson lesson) {
        model.addAttribute("lesson", lessonService.findById(id));
        return "lessons/index";
    }
    @GetMapping("/new")
    public String newLesson( @ModelAttribute("lesson") Lesson lesson) {
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
        lessonService.delete(id);
        return "redirect:/lessons";
    }
    @GetMapping("/group/{id}")
    public String listGroupLessons(Model model,@PathVariable("id") Long id) {
        Group group = groupService.findById(id);
        model.addAttribute("group", group);
        model.addAttribute("lessons",lessonService.findByGroups(group));
        return "lessons/groupLessons";
    }

    @GetMapping("/teacher/{id}")
    public String listTeacherLessons(Model model,@PathVariable("id") Long id) {
        Teacher teacher = teacherService.findById(id);
        model.addAttribute("teacher", teacher);
        model.addAttribute("lessons",lessonService.findByTeachers(teacher));
        return "lessons/teacherLessons";
    }

    @GetMapping("/group/{id}/add")
    public String addLessonToGroup(Model model,@PathVariable("id") Long id, @ModelAttribute("groupLesson") GroupLesson groupLesson) {
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


