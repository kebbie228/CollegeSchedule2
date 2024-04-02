package org.itstep.services;

import org.itstep.model.*;
import org.itstep.repositories.ScheduleTeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleTeacherService {
    private final ScheduleTeacherRepository scheduleTeacherRepository;



    @Autowired
    public ScheduleTeacherService(ScheduleTeacherRepository scheduleTeacherRepository) {
        this.scheduleTeacherRepository = scheduleTeacherRepository;
    }


    public List<ScheduleTeacher> findAll() {
        return scheduleTeacherRepository.findAll();
    }


    public ScheduleTeacher findById(Long id) {
        Optional<ScheduleTeacher> foundScheduleTeacher = scheduleTeacherRepository.findById(id);
        return foundScheduleTeacher.orElse(null);
    }

    public void save(ScheduleTeacher scheduleTeacher) {
        scheduleTeacherRepository.save(scheduleTeacher);
    }

    public void update(Long id, ScheduleTeacher updatedScheduleTeacher) {
        updatedScheduleTeacher.setId(id);
        scheduleTeacherRepository.save(updatedScheduleTeacher);
    }

    public void delete(Long id) {
        scheduleTeacherRepository.deleteById(id);
    }


    public List<ScheduleTeacher> findByGroup(Group group) {
        return scheduleTeacherRepository.findByGroupOrderByDayAscParaAsc(group);
    }
    public List<ScheduleTeacher> findByGroupAndDayIdOrderByDayAscParaAsc(Group group,Long dayId) {
        return scheduleTeacherRepository.findByGroupAndDayIdOrderByDayAscParaAsc(group,dayId);
    }
    public List<ScheduleTeacher> findByTeacher(Teacher teacher) {
        return scheduleTeacherRepository.findByTeacher(teacher);
    }
    public boolean hasSchedule(Group group, Day day, Para para) {
        return scheduleTeacherRepository.existsByGroupAndDayAndPara(group, day,para);
    }
    public ScheduleTeacher findByTeacherAndDayAndPara(Teacher teacher, Day day, Para para) {
        Optional<ScheduleTeacher> foundSchedule = Optional.ofNullable(scheduleTeacherRepository.findByTeacherAndDayAndPara(teacher, day, para));
        return foundSchedule.orElse(null);
    }
}
