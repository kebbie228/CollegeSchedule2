package org.itstep.controllers;

import jdk.swing.interop.SwingInterOpUtils;
import org.itstep.model.*;
import org.itstep.services.*;
import org.itstep.util.ScheduleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/schedules")
public class ScheduleController {
private final ScheduleService scheduleService;
    private final GroupService groupService;
    private final DayService dayService;
    private final TeacherService teacherService;
    private final AudienceService audienceService;
    private final ParaService paraService;
    private final StatusPariService statusPariService;
    private final LessonService lessonService;
    private final ScheduleValidator scheduleValidator;

    @Autowired
    public ScheduleController(ScheduleService scheduleService, GroupService groupService, DayService dayService, TeacherService teacherService, AudienceService audienceService, ParaService paraService, StatusPariService statusPariService, LessonService lessonService, ScheduleValidator scheduleValidator) {
        this.scheduleService = scheduleService;
        this.groupService = groupService;
        this.dayService = dayService;
        this.teacherService = teacherService;
        this.audienceService = audienceService;
        this.paraService = paraService;
        this.statusPariService = statusPariService;
        this.lessonService = lessonService;
        this.scheduleValidator = scheduleValidator;
    }


    @GetMapping()
    public String show(Model model){
        model.addAttribute("schedules", scheduleService.findAll());
        return "schedules/show";
    }
//
//    @GetMapping("/group/{id}")
//    public String groupSchedule(Model model, @PathVariable("id") Long id) {
//        Group group= groupService.findById(id);
//        model.addAttribute("scheduleGroup", scheduleService.findByGroup(group));
//        model.addAttribute("days", dayService.findAll());
//        model.addAttribute("group", group);
//        System.out.println(scheduleService.findByGroup(group));
//        return "schedules/group";
//    }

    @GetMapping("/group/{id}")
    public String groupSchedule2(Model model, @PathVariable("id") Long id) {
        Group group= groupService.findById(id);
        model.addAttribute("scheduleGroup", scheduleService.findByGroup(group));
        model.addAttribute("days", dayService.findAll());
        model.addAttribute("group", group);
        System.out.println(scheduleService.findByGroup(group));
        return "schedules/group2";
    }



    @GetMapping("/group/{id}/add")
    public String addScheduleToGroup(Model model,@PathVariable("id") Long id) {
        Group group= groupService.findById(id);
        model.addAttribute("audiences", audienceService.findAll());
        model.addAttribute("paras", paraService.findAll());
        model.addAttribute("statuses", statusPariService.findAll());
        model.addAttribute("days", dayService.findAll());
        model.addAttribute("lessons", lessonService.findByGroups(group));
        model.addAttribute("group", group);
        model.addAttribute("teachers", teacherService.findAll());
        model.addAttribute("newSchedule", new Schedule());

        // model.addAttribute("teachers", teacherService.findByLesson());
        return "schedules/add";
    }
    @PostMapping("/addToGroup")
    public String addLessonToGroup(@ModelAttribute("newSchedule") @Valid Schedule schedule,
                                   @RequestParam("groupId") Long groupId,
                                   @RequestParam("day.id") Long dayId,
                                   @RequestParam("para.id") Long paraId,
                                   @RequestParam("lesson.id") Long lessonId,
                                   @RequestParam("teacher.id") Long teacherId,
                                   @RequestParam("audience.id") Long audienceId,
                                   BindingResult bindingResult,
                                   Model model
    ) {
        Group group = groupService.findById(groupId);
        Day day = dayService.findById(dayId);
        Para para = paraService.findById(paraId);
        Lesson lesson = lessonService.findById(lessonId);
        Teacher teacher = teacherService.findById(teacherId);
        Audience audience = audienceService.findById(audienceId);

        schedule.setGroup(group);
        schedule.setDay(day);
        schedule.setPara(para);
        schedule.setLesson(lesson);
        schedule.setTeacher(teacher);
        schedule.setAudience(audience);

        scheduleValidator.validate(schedule,bindingResult);
        if (bindingResult.hasErrors()) {
            System.out.println("да сюда есть ошибка");
            model.addAttribute("audiences", audienceService.findAll());
            model.addAttribute("paras", paraService.findAll());
            model.addAttribute("statuses", statusPariService.findAll());
            model.addAttribute("days", dayService.findAll());
            model.addAttribute("lessons", lessonService.findByGroups(group));
            model.addAttribute("group", group);
            model.addAttribute("teachers", teacherService.findAll());
            model.addAttribute("newSchedule", new Schedule());
            return "schedules/add";
        }

        scheduleService.save(schedule);
        String redirectUrl = "redirect:/schedules/group/" + groupId;
        return redirectUrl;
    }


    @DeleteMapping("/group/delete")
    public String deleteScheduleFromGroup(@RequestParam("scheduleId") Long scheduleId,
    @RequestParam("groupId") Long groupId)
    {
 scheduleService.delete(scheduleId);

    String redirectUrl = "redirect:/schedules/group/" + groupId;
        return redirectUrl;
}
 }