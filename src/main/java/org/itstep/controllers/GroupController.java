package org.itstep.controllers;

import org.itstep.model.Group;

import org.itstep.model.Schedule;
import org.itstep.services.*;
import org.itstep.util.GroupValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;
    private  final GroupValidator groupValidator;
    private final DayService dayService;

    private final ParaService paraService;


    private final ScheduleService scheduleService;


    @Autowired
    public GroupController(GroupService groupService, GroupValidator groupValidator, DayService dayService,  ParaService paraService, ScheduleService scheduleService) {
        this.groupService = groupService;
        this.groupValidator = groupValidator;
        this.dayService = dayService;
        this.paraService = paraService;
        this.scheduleService = scheduleService;
    }


    @GetMapping()
    public String show(Model model){
        model.addAttribute("groups", groupService.findAll());
        return "groups/show";
    }

    @GetMapping("/{id}")
    public String index(@PathVariable("id") Long id, Model model, @ModelAttribute("group") Group group) {
        model.addAttribute("group", groupService.findById(id));
        return "groups/index";
    }
    @GetMapping("/new")
    public String newGroup( @ModelAttribute("group") Group group) {
        return "groups/new";

    }

//    @PostMapping()
//    public String create(@ModelAttribute("group") @Valid Group group,
//                         BindingResult bindingResult) {
//        groupValidator.validate(group,bindingResult);
//        if (bindingResult.hasErrors()) return "groups/new";
//        groupService.save(group);
//        return "redirect:/groups";
//    }

    @PostMapping()
    public String create(@ModelAttribute("group") @Valid Group group,
                         BindingResult bindingResult) {
        groupValidator.validate(group,bindingResult);
        if (bindingResult.hasErrors()) return "groups/new";
        groupService.save(group);
        for(int i=1;i<=6;i++){

            for(int j=1;j<6;j++){
                Schedule schedule=new Schedule();
                schedule.setDay(dayService.findById((long) i));
                schedule.setPara(paraService.findById((long) j));
                schedule.setGroup(group);
                scheduleService.save(schedule);
            }
        }
        return "redirect:/groups";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("group", groupService.findById(id));
        return "groups/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("group") @Valid Group group, BindingResult bindingResult,
                         @PathVariable("id") Long id) {
        groupValidator.validate(group,bindingResult);
        if (bindingResult.hasErrors())
            return "groups/edit";

        groupService.update(id,group);
        return "redirect:/groups";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        groupService.delete(id);
        return "redirect:/groups";
    }

//    @GetMapping("/listLessons/{id}")
//    public String listLessons(Model model,@PathVariable("id") Long id) {
//
//
//
//        return "groups/new";
//
//    }

}


