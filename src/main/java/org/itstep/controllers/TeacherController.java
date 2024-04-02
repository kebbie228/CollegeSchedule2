package org.itstep.controllers;

import org.itstep.model.Schedule;
import org.itstep.model.ScheduleTeacher;
import org.itstep.model.Teacher;
import org.itstep.services.*;
import org.itstep.util.TeacherValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;
    private final TeacherValidator teacherValidator;
    private final DayService dayService;

    private final ParaService paraService;


    private final ScheduleTeacherService scheduleTeacherService;

    @Autowired
    public TeacherController(TeacherService teacherService, TeacherValidator teacherValidator, DayService dayService, ParaService paraService, ScheduleTeacherService scheduleTeacherService) {
        this.teacherService = teacherService;
        this.teacherValidator = teacherValidator;
        this.dayService = dayService;
        this.paraService = paraService;
        this.scheduleTeacherService = scheduleTeacherService;
    }






    @GetMapping()
    public String show(Model model){
        model.addAttribute("teachers", teacherService.findAll());
        return "teachers/show";
    }

    @GetMapping("/{id}")
    public String index(@PathVariable("id") Long id, Model model, @ModelAttribute("teacher") Teacher teacher) {
        model.addAttribute("teacher", teacherService.findById(id));
        return "teachers/index";
    }
    @GetMapping("/new")
    public String newTeacher( @ModelAttribute("teacher") Teacher teacher) {
        return "teachers/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("teacher") @Valid Teacher teacher,
                         BindingResult bindingResult) {
        teacherValidator.validate(teacher,bindingResult);


        if (bindingResult.hasErrors())
            return "teachers/new";
        teacherService.save(teacher);
        for(int i=1;i<=6;i++){

            for(int j=1;j<6;j++){
                ScheduleTeacher scheduleTeacher=new ScheduleTeacher();
                scheduleTeacher.setDay(dayService.findById((long) i));
                scheduleTeacher.setPara(paraService.findById((long) j));
                scheduleTeacher.setTeacher(teacher);
                scheduleTeacherService.save(scheduleTeacher);
            }
        }
        return "redirect:/teachers";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("teacher", teacherService.findById(id));
        return "teachers/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("teacher") @Valid Teacher teacher, BindingResult bindingResult,
                         @PathVariable("id") Long id) {
        teacherValidator.validate(teacher,bindingResult);

        if (bindingResult.hasErrors())
            return "teachers/edit";

        teacherService.update(id,teacher);
        return "redirect:/teachers";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {

        Teacher teacher=teacherService.findById(id);
        teacher.getScheduleList().forEach(schedule -> {
            schedule.setLesson(null);
            schedule.setAudience(null);
            schedule.setTeacher(null);
        });

//        teacher.getScheduleTeacherList().forEach(scheduleTeacher -> {
//            scheduleTeacher.setLesson(null);
//            scheduleTeacher.setAudience(null);
//            scheduleTeacher.setTeacher(null);
//            scheduleTeacher.setGroup(null);
//        });

        teacherService.delete(id);
        return "redirect:/teachers";
    }

}


