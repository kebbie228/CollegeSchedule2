package org.itstep.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

    @Id
    @Column(name = "teacher_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "teacher_name")
    @NotEmpty(message = "ФИО преподавателя не должно быть пустым")
    @Size(min = 5, max = 33 , message = "ФИО преподавателя не должно превышать 33 символов")
    private String teacherName;

    @Column(name = "photo_file_path")
    private String photoFilePath;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="teacher_lesson",
            joinColumns = @JoinColumn(name="teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_id")
    )
    private List<Lesson> teacherLessons;


    @OneToMany(mappedBy = "teacher")
    private List<Schedule> scheduleList;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<ScheduleTeacher> scheduleTeacherList;
}