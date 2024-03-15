package org.itstep.services;

import org.itstep.model.Group;
import org.itstep.model.GroupLesson;
import org.itstep.model.Lesson;
import org.itstep.repositories.GroupLessonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GroupLessonService {
    private final GroupLessonRepository groupLessonRepository;

    public GroupLessonService(GroupLessonRepository groupLessonRepository) {
        this.groupLessonRepository = groupLessonRepository;
    }
    public GroupLesson findByGroupAndLesson(Group group, Lesson lesson){
        return groupLessonRepository.findByGroupAndLesson(group,lesson);
    }
    public void delete(GroupLesson groupLesson){
        groupLessonRepository.delete(groupLesson);
    }
    public void save(GroupLesson groupLesson){
        groupLessonRepository.save(groupLesson);
    }



}
