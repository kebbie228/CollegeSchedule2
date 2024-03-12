package org.itstep.controllers;

import org.itstep.model.Group;
import org.itstep.model.GroupLesson;
import org.itstep.model.Lesson;
import org.itstep.services.GroupLessonService;
import org.itstep.services.GroupService;
import org.itstep.services.LessonService;
import org.itstep.util.LessonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/lessons")
public class LessonController {

    private final LessonService lessonService;

    private  final LessonValidator lessonValidator;
    private final GroupService groupService;
    private final GroupLessonService groupLessonService;

    @Autowired
    public LessonController(LessonService lessonService, LessonValidator lessonValidator, GroupService groupService, GroupLessonService groupLessonService) {
        this.lessonService = lessonService;
        this.lessonValidator = lessonValidator;
        this.groupService = groupService;
        this.groupLessonService = groupLessonService;
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
    public String listLessons(Model model,@PathVariable("id") Long id) {
        Group group = groupService.findById(id);
        model.addAttribute("group", group);
        model.addAttribute("lessons",lessonService.findByGroups(group));
        return "lessons/groupLessons";
    }
    // Создаем Map для хранения информации о каждой песне (songId -> songAdded)
//        Map<Long, Boolean> lessonNoAddedInGroupMap = new HashMap<>();
//        List<Lesson> lessons = lessonService.findByGroups(group);
//        for (Lesson lesson : lessons) {
//            long lessonId = lesson.getId();
//
//            boolean lessonNoAdded =!groupLessonService.hasLesson(group.getId(), lessonId);
//            lessonNoAddedInGroupMap.put(lessonId, lessonNoAdded);
//        }
//        model.addAttribute("lessonNoAddedInGroupMap", lessonNoAddedInGroupMap); // Передаем информацию в модель

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
}


