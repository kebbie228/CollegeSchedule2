package org.itstep.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "teacher_lesson", uniqueConstraints = {@UniqueConstraint(columnNames = {"teacher_id", "lesson_id"})} )
public class TeacherLesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
}
