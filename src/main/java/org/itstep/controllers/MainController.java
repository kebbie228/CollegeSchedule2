package org.itstep.controllers;

import org.itstep.security.PersonDetails;
import org.itstep.services.GroupService;
import org.itstep.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

     if( authentication.getPrincipal().equals("anonymousUser")) model.addAttribute("person",null);
      else {
          PersonDetails personDetails= (PersonDetails)authentication.getPrincipal();
         model.addAttribute("person",personDetails.getPerson());

     }
        System.out.println(authentication.getPrincipal());
        return "main/search";


    }



    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String firstLetters) {
        model.addAttribute("groups", groupService.findByGroupNameContainingIgnoreCase(firstLetters));
        model.addAttribute("teachers", teacherService.findByTeacherNameContainingIgnoreCase(firstLetters));

        return "main/search";
    }
}
