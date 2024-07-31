package com.ende.FinalAdvancedMappings.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ende.FinalAdvancedMappings.entity.Course;
import com.ende.FinalAdvancedMappings.entity.Professor;
import com.ende.FinalAdvancedMappings.service.ProfessorService;

@Controller
public class CourseController {

    private ProfessorService profService;
    public CourseController(ProfessorService professorService){
        this.profService = professorService;
    }

    @GetMapping("/course-manage")
    public String courseManage(Model model){
        List<Course> courses = profService.findAllCourses();
        model.addAttribute("courses", courses);
        return "course/professor-course-manage";
    }

    @GetMapping("/course-details/{id}")
    public String courseDetails(@PathVariable int id, Model model){
        Course course = profService.findCourseById(id);
        model.addAttribute("course", course);
        return "course/course-detail";
    }

    @GetMapping("/showFormForAddCourse")
    public String showFormForAddCourse(Model theModel){
        Course course = new Course();
        theModel.addAttribute("course",course);
        return "course/course-add-update";
    }

    @PostMapping("/saveCourse")
    public String saveCourse(@ModelAttribute("course") Course course, Model theModel){
        Professor professor = course.getProfessor();
        profService.saveCourse(course,professor.getId());
        return "redirect:/course-manage";
    }


    @GetMapping("/delete-course")
    public String deleteCourse(@RequestParam("courseId") int courseId){
        profService.deleteCourseById(courseId);
        System.out.println("Course deleted with id: "+courseId);
        return "redirect:/course-manage";
    }

    @GetMapping("/showFormForUpdateCourse")
    public String showFormForUpdateCourse(@RequestParam("courseId") int theId, Model theModel){
        Course course = profService.findCourseById(theId);
        theModel.addAttribute("course", course);
        return "course/course-add-update";
    }

    @GetMapping("/viewAllCourses")
    public String viewAllCourses(Model model){
        List<Course> courses = profService.findAllCourses();
        model.addAttribute("courses", courses);
        return "course/all-courses";
    }
}
