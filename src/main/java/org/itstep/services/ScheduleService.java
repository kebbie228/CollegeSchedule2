package org.itstep.services;

import org.itstep.model.*;
import org.itstep.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }


    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }


    public Schedule findById(Long id) {
        Optional<Schedule> foundSchedule = scheduleRepository.findById(id);
        return foundSchedule.orElse(null);
    }

    public void save(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    public void update(Long id, Schedule updatedSchedule) {
        updatedSchedule.setId(id);
        scheduleRepository.save(updatedSchedule);
    }

    public void delete(Long id) {
        scheduleRepository.deleteById(id);
    }


    public List<Schedule> findByGroup(Group group) {
        return scheduleRepository.findByGroupOrderByDayAscParaAsc(group);
    }
    public List<Schedule> findByGroupAndDayIdOrderByDayAscParaAsc(Group group,Long dayId) {
        return scheduleRepository.findByGroupAndDayIdOrderByDayAscParaAsc(group,dayId);
    }
    public List<Schedule> findByTeacher(Teacher teacher) {
        return scheduleRepository.findByTeacher(teacher);
    }
    public boolean hasSchedule(Group group, Day day, Para para) {
        return scheduleRepository.existsByGroupAndDayAndPara(group, day,para);
    }
}
