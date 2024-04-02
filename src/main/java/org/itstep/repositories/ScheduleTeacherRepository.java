package org.itstep.repositories;

import org.itstep.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleTeacherRepository extends JpaRepository<ScheduleTeacher,Long> {

    List<ScheduleTeacher> findByGroupOrderByDayAscParaAsc (Group group);
    List<ScheduleTeacher> findByTeacher (Teacher teacher);
    List<ScheduleTeacher> findByGroupAndDayIdOrderByDayAscParaAsc (Group group,Long dayId);

    ScheduleTeacher findByTeacherAndDayAndPara(Teacher teacher, Day day, Para para);

    boolean existsByGroupAndDayAndPara(Group group, Day day, Para para);

}
