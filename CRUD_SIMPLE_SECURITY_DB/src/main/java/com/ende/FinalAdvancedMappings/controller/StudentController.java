package com.ende.FinalAdvancedMappings.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ende.FinalAdvancedMappings.config.CustomUserDetails;
import com.ende.FinalAdvancedMappings.entity.Course;
import com.ende.FinalAdvancedMappings.entity.Student;
import com.ende.FinalAdvancedMappings.entity.StudentDetails;
import com.ende.FinalAdvancedMappings.service.ProfessorService;


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

    @PostMapping("/enroll")
    public String enrollInACourse(@RequestParam int courseId) {
        
        System.out.println("Received courseId: " + courseId);
    
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        int studentId = userDetails.getId();
        Student student = profService.findStudentById(studentId);
    
        Course course = profService.findCourseById(courseId);
    
        // Add the course to the student's list
        List<Course> courses = student.getCourses();
        if (!courses.contains(course)) {
            courses.add(course);
            student.setCourses(courses);
        }
        profService.updateStudent(student);
    
        // Add the student to the course's list
        List<Student> students = course.getStudents();
        if (!students.contains(student)) {
            students.add(student);
            course.setStudents(students);
        }
        profService.saveCourse(course, courseId);
    
        return "redirect:/viewAllCourses";
    }
    @GetMapping("/myCourses/{studentId}")
    public String myCourses(Model model, @PathVariable int studentId) {
    
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        // int studentId = userDetails.getId();

        Student student = profService.findStudentById(studentId);
        List<Course> courses = student.getCourses();
        model.addAttribute("courses", courses);

        boolean isStudent = userDetails.isStudent();
        model.addAttribute("isStudent", isStudent);
        model.addAttribute("isProfessor", !isStudent);

            return "course/student-courses";
        }
    
    
    

    @PostMapping("/unroll")
    public String unrollCourse(@RequestParam int courseId){
        
        Course course = profService.findCourseById(courseId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        int studentId = userDetails.getId();
        Student student = profService.findStudentById(studentId);
        List<Course> courses = student.getCourses();
        courses.remove(course);
        student.setCourses(courses);
        profService.updateStudent(student);
       
        List<Student> students = course.getStudents();
        students.remove(student);
        course.setStudents(students);
        profService.saveCourse(course, courseId);

        return "redirect:/myCourses/"+studentId;
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
