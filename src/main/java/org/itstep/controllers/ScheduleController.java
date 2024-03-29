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
import java.util.List;

@Controller
@RequestMapping("/schedules")
public class ScheduleController {
private final ScheduleService scheduleService;
    private final GroupService groupService;
    private final DayService dayService;
    private final TeacherService teacherService;
    private final AudienceService audienceService;
    private final ParaService paraService;
    private final LessonService lessonService;
    private final ScheduleValidator scheduleValidator;

    @Autowired
    public ScheduleController(ScheduleService scheduleService, GroupService groupService, DayService dayService, TeacherService teacherService, AudienceService audienceService, ParaService paraService,
                              LessonService lessonService, ScheduleValidator scheduleValidator) {
        this.scheduleService = scheduleService;
        this.groupService = groupService;
        this.dayService = dayService;
        this.teacherService = teacherService;
        this.audienceService = audienceService;
        this.paraService = paraService;
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
        model.addAttribute("scheduleTeacher",
                scheduleService.findByTeacher(teacherService.findById((long) 5)));
        model.addAttribute("days", dayService.findAll());
        model.addAttribute("group", group);
        model.addAttribute("lessons", lessonService.findByGroups(group));
        model.addAttribute("audiences", audienceService.findAll());


        System.out.println(scheduleService.findByGroup(group));
        return "schedules/group2";
    }



    @GetMapping("/{id1}/editGroup/{id2}")
    public String editSchedule(Model model, @PathVariable("id1") Long id1,@PathVariable("id2") Long id2
                              ) {
        Schedule schedule= scheduleService.findById(id1);
        Group group= groupService.findById(id2);
        model.addAttribute("day", schedule.getDay());
        model.addAttribute("para", schedule.getPara());
        model.addAttribute("group", group);
        model.addAttribute("schedule", schedule);
        model.addAttribute("teachers", teacherService.findAll());

        model.addAttribute("lessons", lessonService.findByGroups(group));
        model.addAttribute("audiences", audienceService.findAll());
        return "schedules/edit";
    }

    @PatchMapping("/edit/{id}")
    public String updateSchedule(@ModelAttribute("schedule") Schedule schedule,
                                 @RequestParam("lesson.id") Long lessonId,
                                 @RequestParam("groupId") Long groupId,
                                 @RequestParam("day.id") Long dayId,
                                 @RequestParam("para.id") Long paraId,
                                 @RequestParam("teacher.id") Long teacherId,
                                 @RequestParam("audience.id") Long audienceId
    ) {
        schedule.setPara(paraService.findById(paraId));
        schedule.setDay(dayService.findById(dayId));
        schedule.setLesson(lessonService.findById(lessonId));
        schedule.setTeacher(teacherService.findById(teacherId));
        schedule.setAudience(audienceService.findById(audienceId));
        schedule.setGroup(groupService.findById(groupId));
        scheduleService.update(schedule.getId(),schedule);
        return "redirect:/schedules/group/"+groupId;
    }



    @PatchMapping("/editGroup")
    public String editDelete(
                                 @RequestParam("groupId") Long groupId,
                                 @RequestParam("scheduleId") Long scheduleId,
                                 @RequestParam("day.id") Long dayId,
                                 @RequestParam("para.id") Long paraId

    ) {
        Schedule  schedule= scheduleService.findById(scheduleId);
        schedule.setGroup(groupService.findById(groupId));
        schedule.setPara(paraService.findById(paraId));
        schedule.setDay(dayService.findById(dayId));
        schedule.setLesson(null);
        schedule.setAudience(null);
        schedule.setTeacher(null);
        scheduleService.update(schedule.getId(),schedule);
        return "redirect:/schedules/group/"+groupId;
    }



//    @GetMapping("/group/{id}/add")
//    public String addScheduleToGroup(Model model,@PathVariable("id") Long id) {
//        Group group= groupService.findById(id);
//        model.addAttribute("audiences", audienceService.findAll());
//        model.addAttribute("paras", paraService.findAll());
//        model.addAttribute("statuses", statusPariService.findAll());
//        model.addAttribute("days", dayService.findAll());
//        model.addAttribute("lessons", lessonService.findByGroups(group));
//        model.addAttribute("group", group);
//        model.addAttribute("teachers", teacherService.findAll());
//        model.addAttribute("newSchedule", new Schedule());
//
//        // model.addAttribute("teachers", teacherService.findByLesson());
//        return "schedules/add";
//    }
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