package org.itstep.controllers;

import org.itstep.model.*;
import org.itstep.security.PersonDetails;
import org.itstep.services.*;
import org.itstep.util.ScheduleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    private final ScheduleTeacherService scheduleTeacherService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService, GroupService groupService, DayService dayService, TeacherService teacherService, AudienceService audienceService, ParaService paraService,
                              LessonService lessonService, ScheduleValidator scheduleValidator, ScheduleTeacherService scheduleTeacherService) {
        this.scheduleService = scheduleService;
        this.groupService = groupService;
        this.dayService = dayService;
        this.teacherService = teacherService;
        this.audienceService = audienceService;
        this.paraService = paraService;
        this.lessonService = lessonService;
        this.scheduleValidator = scheduleValidator;
        this.scheduleTeacherService = scheduleTeacherService;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if( authentication.getPrincipal().equals("anonymousUser")) {
            model.addAttribute("person", null);
        }
        else {
            PersonDetails personDetails= (PersonDetails)authentication.getPrincipal();
            model.addAttribute("person",personDetails.getPerson());
        }
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
    @GetMapping("/{id1}/editAddScheduleGroup/{id2}")
    public String addSchedule(Model model, @PathVariable("id1") Long id1,@PathVariable("id2") Long id2
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if( authentication.getPrincipal().equals("anonymousUser")) {
            model.addAttribute("person", null);
            return "redirect:/search";
        }
        else {
            PersonDetails personDetails= (PersonDetails)authentication.getPrincipal();
            model.addAttribute("person",personDetails.getPerson());
        }
        Schedule schedule= scheduleService.findById(id1);
        Group group= groupService.findById(id2);
        model.addAttribute("day", schedule.getDay());
        model.addAttribute("para", schedule.getPara());
        model.addAttribute("group", group);
        model.addAttribute("schedule", schedule);
        model.addAttribute("lessons", lessonService.findByGroups(group));
        model.addAttribute("teachers", teacherService.findAll());
        model.addAttribute("audiences", audienceService.findAll());
        return "schedules/add";
    }

    @PatchMapping("/add/{id}")
    public String addSchedule(@ModelAttribute("schedule") Schedule schedule,
                              @RequestParam("lesson.id") Long lessonId,
                              @RequestParam("groupId") Long groupId,
                              @RequestParam("day.id") Long dayId,
                              @RequestParam("para.id") Long paraId,
                              @RequestParam("teacher.id")Long teacherId,
                              @RequestParam("audience.id") Long audienceId
    ) {

        Teacher teacher= teacherService.findById(teacherId);
        Para para=paraService.findById(paraId);
        Day day=dayService.findById(dayId);
        ScheduleTeacher scheduleTeacher=scheduleTeacherService.findByTeacherAndDayAndPara(teacher,day,para);
        //нужна разная проверка разные маппинги
        if(scheduleTeacher.getLesson()!=null && scheduleTeacher.getAudience()!=null && scheduleTeacher.getGroup()!=null){
            System.out.println("не йоу");
            return "redirect:/schedules/group/"+groupId;
        }
        else if(!teacher.getTeacherLessons().contains(lessonService.findById(lessonId)))   {
            return "redirect:/schedules/group/"+groupId;
        }
        else {
            System.out.println("йоу");
            scheduleTeacher.setPara(para);
            scheduleTeacher.setDay(day);
            scheduleTeacher.setLesson(lessonService.findById(lessonId));
            scheduleTeacher.setGroup(groupService.findById(groupId));
            scheduleTeacher.setAudience(audienceService.findById(audienceId));
            scheduleTeacherService.update(scheduleTeacher.getId(), scheduleTeacher);

            schedule.setPara(para);
            schedule.setDay(day);
            schedule.setLesson(lessonService.findById(lessonId));
            schedule.setTeacher(teacherService.findById(teacherId));
            schedule.setAudience(audienceService.findById(audienceId));
            schedule.setGroup(groupService.findById(groupId));
            scheduleService.update(schedule.getId(), schedule);
        }
        return "redirect:/schedules/group/"+groupId;
    }


    @PatchMapping("/editGroup")
    public String editDelete(
            @RequestParam("groupId") Long groupId,
            @RequestParam("scheduleId") Long scheduleId,
            @RequestParam("day.id") Long dayId,
            @RequestParam("teacher.id") Long teacherId,
            @RequestParam("para.id") Long paraId

    ) {

        Teacher teacher= teacherService.findById(teacherId);
        Para para=paraService.findById(paraId);
        Day day=dayService.findById(dayId);
        ScheduleTeacher scheduleTeacher=scheduleTeacherService.findByTeacherAndDayAndPara(teacher,day,para);
        scheduleTeacher.setLesson(null);
        scheduleTeacher.setAudience(null);
        scheduleTeacher.setGroup(null);
        scheduleTeacherService.update(scheduleTeacher.getId(),scheduleTeacher);



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

//
//    @GetMapping("/{id1}/editGroup/{id2}")
//    public String editSchedule(Model model, @PathVariable("id1") Long id1,@PathVariable("id2") Long id2
//                              ) {
//        Schedule schedule= scheduleService.findById(id1);
//        Group group= groupService.findById(id2);
//        model.addAttribute("day", schedule.getDay());
//        model.addAttribute("para", schedule.getPara());
//        model.addAttribute("group", group);
//        model.addAttribute("schedule", schedule);
//        model.addAttribute("teachers", teacherService.findAll());
//
//        model.addAttribute("lessons", lessonService.findByGroups(group));
//        model.addAttribute("audiences", audienceService.findAll());
//        return "schedules/edit";
//    }
//
//    @PatchMapping("/edit/{id}")
//    public String updateSchedule(@ModelAttribute("schedule") Schedule schedule,
//                                 @RequestParam("lesson.id") Long lessonId,
//                                 @RequestParam("groupId") Long groupId,
//                                 @RequestParam("day.id") Long dayId,
//                                 @RequestParam("para.id") Long paraId,
//                                 @RequestParam("teacher.id") Long teacherId,
//                                 @RequestParam("audience.id") Long audienceId
//    ) {
//        schedule.setPara(paraService.findById(paraId));
//        schedule.setDay(dayService.findById(dayId));
//        schedule.setLesson(lessonService.findById(lessonId));
//        schedule.setTeacher(teacherService.findById(teacherId));
//        schedule.setAudience(audienceService.findById(audienceId));
//        schedule.setGroup(groupService.findById(groupId));
//        scheduleService.update(schedule.getId(),schedule);
//        return "redirect:/schedules/group/"+groupId;
//    }




//    @PatchMapping("/editGroup")
//    public String editDelete(
//                                 @RequestParam("groupId") Long groupId,
//                                 @RequestParam("scheduleId") Long scheduleId,
//                                 @RequestParam("day.id") Long dayId,
//                                 @RequestParam("para.id") Long paraId
//
//    ) {
//        Schedule  schedule= scheduleService.findById(scheduleId);
//        schedule.setGroup(groupService.findById(groupId));
//        schedule.setPara(paraService.findById(paraId));
//        schedule.setDay(dayService.findById(dayId));
//        schedule.setLesson(null);
//        schedule.setAudience(null);
//        schedule.setTeacher(null);
//        scheduleService.update(schedule.getId(),schedule);
//        return "redirect:/schedules/group/"+groupId;
//    }



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


    @DeleteMapping("/group/delete")
    public String deleteScheduleFromGroup(@RequestParam("scheduleId") Long scheduleId,
    @RequestParam("groupId") Long groupId)
    {
 scheduleService.delete(scheduleId);

    String redirectUrl = "redirect:/schedules/group/" + groupId;
        return redirectUrl;
}
 }