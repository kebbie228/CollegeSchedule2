package org.itstep.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "college_group")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_name")
    @NotEmpty(message = "Название группы не доллжно быть пустым")
    @Size(min = 2, max = 6, message = "Название группы не должно превышать 6 символов")
    private String groupName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="group_lesson",
            joinColumns = @JoinColumn(name="group_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_id")
    )
    private List<Lesson> lessons;



    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Schedule> scheduleList;

    @OneToMany(mappedBy = "group")
    private List<ScheduleTeacher> scheduleTeacherList;

}