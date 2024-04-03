package org.itstep.controllers;

import org.itstep.model.*;
import org.itstep.security.PersonDetails;
import org.itstep.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/schedulesTeacher")
public class ScheduleTeacherController {
private final ScheduleTeacherService scheduleTeacherService;
    private final GroupService groupService;
    private final DayService dayService;
    private final TeacherService teacherService;
    private final AudienceService audienceService;
    private final ParaService paraService;
    private final LessonService lessonService;
   private final ScheduleService scheduleService;

    @Autowired
    public ScheduleTeacherController(ScheduleTeacherService scheduleTeacherService, GroupService groupService, DayService dayService, TeacherService teacherService,
                                     AudienceService audienceService, ParaService paraService, LessonService lessonService, ScheduleService scheduleService) {
        this.scheduleTeacherService = scheduleTeacherService;
        this.groupService = groupService;
        this.dayService = dayService;
        this.teacherService = teacherService;
        this.audienceService = audienceService;
        this.paraService = paraService;
        this.lessonService = lessonService;
        this.scheduleService = scheduleService;
    }





    @GetMapping()
    public String show(Model model){
        model.addAttribute("schedulesTeacher", scheduleTeacherService.findAll());
        return "schedulesTeacher/show";
    }



    @GetMapping("/teacher/{id}")
    public String groupSchedule2(Model model, @PathVariable("id") Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if( authentication.getPrincipal().equals("anonymousUser")) {
            model.addAttribute("person", null);

        }
        else {
            PersonDetails personDetails= (PersonDetails)authentication.getPrincipal();
            model.addAttribute("person",personDetails.getPerson());
        }

        Teacher teacher= teacherService.findById(id);

        model.addAttribute("scheduleTeacher", scheduleTeacherService.findByTeacher(teacher));

        model.addAttribute("days", dayService.findAll());
        model.addAttribute("teacher", teacher);
        model.addAttribute("lessons", lessonService.findByTeachers(teacher));
        model.addAttribute("audiences", audienceService.findAll());


        System.out.println(scheduleTeacherService.findByTeacher(teacher));
        return "schedulesTeacher/teacher";
    }




    @GetMapping("/{id1}/editAddScheduleTeacher/{id2}")
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
        ScheduleTeacher scheduleTeacher= scheduleTeacherService.findById(id1);
        Teacher teacher= teacherService.findById(id2);
        model.addAttribute("day", scheduleTeacher.getDay());
        model.addAttribute("para", scheduleTeacher.getPara());
        model.addAttribute("teacher", teacher);
        model.addAttribute("scheduleTeacher", scheduleTeacher);
        model.addAttribute("groups", groupService.findAll()); // group findBylesson
        model.addAttribute("lessons", lessonService.findByTeachers(teacher));
        model.addAttribute("audiences", audienceService.findAll());
        return "schedulesTeacher/add";
    }
    @PatchMapping("/add/{id}")
    public String addSchedule(@ModelAttribute("scheduleTeacher") ScheduleTeacher scheduleTeacher,
                                 @RequestParam("lesson.id") Long lessonId,
                                 @RequestParam("teacherId") Long teacherId,
                                 @RequestParam("day.id") Long dayId,
                                 @RequestParam("para.id") Long paraId,
                                 @RequestParam("group.id") Long groupId,
                                 @RequestParam("audience.id") Long audienceId
    ) {

        Group group= groupService.findById(groupId);
        Para para=paraService.findById(paraId);
        Day day=dayService.findById(dayId);
        Schedule schedule=scheduleService.findByGroupAndDayAndPara(group,day,para);
        //нужна разная проверка разные маппинги
        if(schedule.getLesson()!=null && schedule.getAudience()!=null && schedule.getTeacher()!=null){
             System.out.println("не йоу");
             return "redirect:/schedulesTeacher/teacher/"+teacherId;
        }
else if(!group.getLessons().contains(lessonService.findById(lessonId)))   {
    return "redirect:/schedulesTeacher/teacher/"+teacherId;
}
        else {
         System.out.println("йоу");
         schedule.setPara(para);
         schedule.setDay(day);
         schedule.setLesson(lessonService.findById(lessonId));
         schedule.setTeacher(teacherService.findById(teacherId));
         schedule.setAudience(audienceService.findById(audienceId));
         scheduleService.update(schedule.getId(), schedule);

         scheduleTeacher.setPara(para);
         scheduleTeacher.setDay(day);
         scheduleTeacher.setLesson(lessonService.findById(lessonId));
         scheduleTeacher.setTeacher(teacherService.findById(teacherId));
         scheduleTeacher.setAudience(audienceService.findById(audienceId));
         scheduleTeacher.setGroup(group);
         scheduleTeacherService.update(scheduleTeacher.getId(), scheduleTeacher);
     }
        return "redirect:/schedulesTeacher/teacher/"+teacherId;
    }

    @PatchMapping("/editTeacher")
    public String editDelete(
            @RequestParam("teacherId") Long teacherId,
            @RequestParam("scheduleId") Long scheduleId,
            @RequestParam("day.id") Long dayId,
            @RequestParam("group.id") Long groupId,
            @RequestParam("para.id") Long paraId

    ) {

        Group group= groupService.findById(groupId);
        Para para=paraService.findById(paraId);
        Day day=dayService.findById(dayId);
        Schedule schedule=scheduleService.findByGroupAndDayAndPara(group,day,para);
        schedule.setLesson(null);
        schedule.setAudience(null);
        schedule.setTeacher(null);
        scheduleService.update(schedule.getId(),schedule);



        ScheduleTeacher  scheduleTeacher= scheduleTeacherService.findById(scheduleId);
        scheduleTeacher.setTeacher(teacherService.findById(teacherId));
        scheduleTeacher.setPara(paraService.findById(paraId));
        scheduleTeacher.setDay(dayService.findById(dayId));
        scheduleTeacher.setLesson(null);
        scheduleTeacher.setAudience(null);
        scheduleTeacher.setGroup(null);

        scheduleTeacherService.update(scheduleTeacher.getId(),scheduleTeacher);
        return "redirect:/schedulesTeacher/teacher/"+teacherId;
    }

 }