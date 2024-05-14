package org.itstep.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
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





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Para getPara() {
        return para;
    }

    public void setPara(Para para) {
        this.para = para;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Audience getAudience() {
        return audience;
    }

    public void setAudience(Audience audience) {
        this.audience = audience;
    }




}
