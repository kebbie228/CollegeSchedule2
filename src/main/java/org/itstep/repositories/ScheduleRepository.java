package org.itstep.repositories;

import org.itstep.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

    List<Schedule> findByGroupOrderByDayAscParaAsc (Group group);
    List<Schedule> findByTeacher (Teacher teacher);


    boolean existsByGroupAndDayAndPara(Group group, Day day, Para para);

}
