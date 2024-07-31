package com.ende.FinalAdvancedMappings.controller;

import com.ende.FinalAdvancedMappings.entity.Professor;
import com.ende.FinalAdvancedMappings.entity.ProfessorDetails;
import com.ende.FinalAdvancedMappings.entity.Student;
import com.ende.FinalAdvancedMappings.entity.StudentDetails;
import com.ende.FinalAdvancedMappings.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StudentController {

    ProfessorService profService;
    @Autowired
    StudentController(ProfessorService profService){
        this.profService = profService;
    }


    @GetMapping("/student-manage")
    public String findAllStudents(Model theModel){
        List<Student> students = profService.findAllStudent();
        theModel.addAttribute("students", students);
        return "professor/professor-student-manage";
    }


    @GetMapping("/student-details/{id}")
    public String studentDetails(Model theModel, @PathVariable int id ){
        Student student = profService.findStudentById(id);
        theModel.addAttribute("student", student);
        return "professor/student-detail";
    }

    @GetMapping("/delete-student")
    public String deleteStudent(@RequestParam("studentId") int studentId){
        profService.deleteStudentById(studentId);
        return "redirect:/student-manage";
    }

    @GetMapping("/showFormForAddStudent")
    public String showFormForAddStudent(Model theModel){
        StudentController.StudentForm studentForm = new StudentController.StudentForm();
        theModel.addAttribute("studentForm", studentForm);
        return "professor/student-add-update";

    }

    @PostMapping("/saveStudent")
    public String saveStudent(@ModelAttribute("studentForm") StudentController.StudentForm studentForm){
        Student student = studentForm.getStudent();
        StudentDetails studentDetails = studentForm.getStudentDetails();
        student.setStudentDetails(studentDetails);
        profService.saveStudent(student);
        int id = student.getId();
        profService.saveStudentDetails(id, studentDetails);
        return "redirect:/student-manage";
    }

    @GetMapping("/showFormForUpdateStudent")
    public String showFormForUpdateStudent(@RequestParam("studentId") int id, Model theModel){
        Student student = profService.findStudentById(id);
        StudentDetails studentDetails = student.getStudentDetails();
        StudentForm studentForm = new StudentForm();
        studentForm.setStudentDetails(studentDetails);
        studentForm.setStudent(student);
        theModel.addAttribute("studentForm", studentForm);
        return "professor/student-add-update";
    }



    public class StudentForm {
        private Student student;
        private StudentDetails studentDetails;

        // Getters and setters
        public Student getStudent() {
            return student;
        }

        public void setStudent(Student student) {
            this.student = student;
        }

        public StudentDetails getStudentDetails() {
            return studentDetails;
        }

        public void setStudentDetails(StudentDetails studentDetails) {
            this.studentDetails = studentDetails;
        }
    }
}
