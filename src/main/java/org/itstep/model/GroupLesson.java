package org.itstep.model;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
@Table(name = "group_lesson", uniqueConstraints = {@UniqueConstraint(columnNames = {"group_id", "lesson_id"})} )
public class GroupLesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
}