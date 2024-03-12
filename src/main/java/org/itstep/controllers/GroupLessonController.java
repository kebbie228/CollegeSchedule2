package org.itstep.controllers;

import org.itstep.model.Group;
import org.itstep.model.GroupLesson;
import org.itstep.model.Lesson;
import org.itstep.services.GroupLessonService;
import org.itstep.services.GroupService;
import org.itstep.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/groupLesson")
public class GroupLessonController {
private final GroupLessonService groupLessonService;
private final GroupService groupService;
private final LessonService lessonService;
@Autowired
    public GroupLessonController(GroupLessonService groupLessonService, GroupService groupService, LessonService lessonService) {
        this.groupLessonService = groupLessonService;
    this.groupService = groupService;
    this.lessonService = lessonService;

}
    //delete lesson in group
    @DeleteMapping("/deleteLessonFromGroup/{id1}/{id2}")
    public String delete(@PathVariable("id1") long id1, @PathVariable("id2") long id2){
       Group group=  groupService.findById(id1);
        Lesson lesson= lessonService.findById(id2);
        groupLessonService.delete(groupLessonService.findByGroupAndLesson(group,lesson));
        String redirectUrl = "redirect:/lessons/group/" + id1;
        return redirectUrl;
    }



    @PostMapping("/addLessonToGroup")
    public String addLessonToGroup(@ModelAttribute("listenerSong") GroupLesson groupLesson,
                                   @RequestParam("lesson.id") Long lessonId,
                                    @RequestParam("groupId") Long groupId,
                                   Model model
    ) {
        Group group=groupService.findById(groupId);
        Lesson lesson= lessonService.findById(lessonId);
        groupLesson.setGroup(group);
        groupLesson.setLesson(lesson);
        groupLessonService.save(groupLesson);
        String redirectUrl = "redirect:/lessons/group/" + groupId;
        return redirectUrl;


    }
}
