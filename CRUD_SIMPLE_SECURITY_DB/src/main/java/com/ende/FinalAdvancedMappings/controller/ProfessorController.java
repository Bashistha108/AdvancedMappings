package com.ende.FinalAdvancedMappings.controller;

import com.ende.FinalAdvancedMappings.entity.*;
import com.ende.FinalAdvancedMappings.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProfessorController {

    ProfessorService profService;

    @Autowired
    ProfessorController(ProfessorService profService){
        this.profService = profService;
    }


    @GetMapping("/professor-manage")
    public String findAllProfessor(Model theModel){
        List<Professor> professors = profService.findAllProfessor();
        theModel.addAttribute("professors", professors);
        return "professor/professor-professor-manage";
    }


    @GetMapping("/professor-details/{id}")
    public String professorDetails(Model theModel, @PathVariable int id ){
        Professor professor = profService.findProfessorById(id);
        theModel.addAttribute("professor", professor);
        return "professor/professor-detail";
    }

    @GetMapping("/delete-professor")
    public String deleteProfessor(@RequestParam("professorId") int professorId){
        profService.deleteProfessorById(professorId);
        return "redirect:/professor-manage";
    }

    @GetMapping("/showFormForAddProfessor")
    public String showFormForAddProfessor(Model model){
        ProfessorForm professorForm = new ProfessorForm();
        model.addAttribute("professorForm", professorForm);
        return "professor/professor-add-update";
    }

    @PostMapping("/saveProfessor")
    public String saveProfessor(@ModelAttribute("professorForm") ProfessorForm professorForm){
        Professor professor = professorForm.getProfessor();
        ProfessorDetails professorDetails = professorForm.getProfessorDetails();
        professor.setProfessorDetails(professorDetails);
        profService.saveProfessor(professor);
        int id = professor.getId();
        profService.saveProfessorDetails(id, professorDetails);

        return "redirect:/professor-manage";
    }

    @GetMapping("/showFormForUpdateProfessor")
    public String showFormForUpdateProfessor(@RequestParam("professorId") int id, Model theModel){
        Professor professor = profService.findProfessorById(id);
        ProfessorDetails professorDetails = professor.getProfessorDetails();
        ProfessorForm professorForm = new ProfessorForm();
        professorForm.setProfessorDetails(professorDetails);
        professorForm.setProfessor(professor);
        theModel.addAttribute("professorForm", professorForm);
        return "professor/professor-add-update";
    }

    @GetMapping("/showCoursesOfAProfessor")
    public String showCoursesOfAProfessor(Model theModel, @RequestParam("professorId") int id){
        Professor professor = profService.findProfessorById(id);
        List<Course> courses = professor.getCourses();
        theModel.addAttribute("courses", courses);
        return "professor/professor-courses";
    }
//
//    @GetMapping("/delete-course-professor")
//    public String deleteCourse(@RequestParam("courseId") int courseId){
//        profService.deleteCourseById(courseId);
//        return "redirect:/showCoursesOfAProfessor";
//    }


    public class ProfessorForm {
        private Professor professor;
        private ProfessorDetails professorDetails;

        // Getters and setters
        public Professor getProfessor() {
            return professor;
        }

        public void setProfessor(Professor professor) {
            this.professor = professor;
        }

        public ProfessorDetails getProfessorDetails() {
            return professorDetails;
        }

        public void setProfessorDetails(ProfessorDetails professorDetails) {
            this.professorDetails = professorDetails;
        }
    }


}
