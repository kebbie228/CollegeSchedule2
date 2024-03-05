package org.itstep.controllers;

import org.itstep.model.Lesson;
import org.itstep.services.LessonService;
import org.itstep.util.LessonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/lessons")
public class LessonController {

    private final LessonService lessonService;

    private  final LessonValidator lessonValidator;

    @Autowired
    public LessonController(LessonService lessonService, LessonValidator lessonValidator) {
        this.lessonService = lessonService;
        this.lessonValidator = lessonValidator;
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


}


