package org.itstep.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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
    @Size(min = 2, max = 20, message = "Название предмета не должно превышать 20 символов")
    private String lessonName;

    @ManyToMany(mappedBy = "groupLessons",cascade = CascadeType.REFRESH)
    private List<Group> groups;

    @ManyToMany(mappedBy = "teacherLessons",cascade = CascadeType.REFRESH)
    private List<Teacher> teachers;

    @OneToMany(mappedBy = "lesson")
    private List<Schedule> scheduleList;
}