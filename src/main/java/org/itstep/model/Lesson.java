package org.itstep.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.jsf.FacesContextUtils;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    @Id
    @Column(name = "lesson_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lesson_name")
    @NotEmpty(message = "Название предмета не должно быть пустым")
    @Size(min = 2, max = 20, message = " Название предмета должно содержать от 2 до 20 символов")
    private String lessonName;


    @ManyToMany(mappedBy = "teacherLessons")
    private List<Teacher> teachers;

    @ManyToMany(mappedBy = "lessons",cascade = CascadeType.REFRESH)
    private List<Group> groups;


    @OneToMany(mappedBy = "lesson",cascade = CascadeType.PERSIST)
    private List<Schedule> scheduleList;
//del if doesnt work
    @OneToMany(mappedBy = "lesson",cascade = CascadeType.PERSIST)
    private List<ScheduleTeacher> scheduleTeacherList;
}