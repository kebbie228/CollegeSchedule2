package org.itstep.controllers;

import org.itstep.services.GroupService;
import org.itstep.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
private final GroupService groupService;
private final TeacherService teacherService;

@Autowired
    public MainController(GroupService groupService, TeacherService teacherService) {
    this.groupService = groupService;
    this.teacherService = teacherService;
}
    @GetMapping("/search")
    public String searchPage(Model model) {
        return "main/search";
    }
    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String firstLetters) {
        model.addAttribute("groups", groupService.findByGroupNameContainingIgnoreCase(firstLetters));
        model.addAttribute("teachers", teacherService.findByTeacherNameContainingIgnoreCase(firstLetters));

        return "main/search";
    }
}
