package org.itstep.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    @Id
    @Column(name = "schedule_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="day_id",referencedColumnName ="day_id" )
    private Day day;

    @ManyToOne
    @JoinColumn(name="para_id",referencedColumnName ="para_id" )
    private Para para;

    @ManyToOne
    @JoinColumn(name="group_id",referencedColumnName ="group_id" )
    private Group group;

    @ManyToOne
    @JoinColumn(name="lesson_id",referencedColumnName ="lesson_id" )
    private Lesson lesson;

    @ManyToOne
    @JoinColumn(name="teacher_id",referencedColumnName ="teacher_id" )
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name="audience_id",referencedColumnName ="audience_id" )
    private Audience audience;

}
