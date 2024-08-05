package com.ende.FinalAdvancedMappings.service;

import com.ende.FinalAdvancedMappings.entity.*;

import java.util.List;

public interface ProfessorService {

    void saveProfessor(Professor theProfessor);
    Professor findProfessorById(int theId);
    void updateProfessor(Professor theProfessor);
    void deleteProfessorById(int theId);
    void deleteAllProfessor();
    List<Professor> findAllProfessor();
    Professor findProfessorByEmail(String email);

    void deleteProfessorDetailsById(int id);
    void saveProfessorDetails(int id, ProfessorDetails professorDetails);


    void saveCourse(Course course, int professorId);
    List<Course>findAllCourses();
    List<Course> findCoursesByProfessorId(int id);
    Course findCourseById(int id);
//    void updateCourse(Course course);
    void deleteCourseById(int id);
    void deleteAllCourse();
    boolean isEnrolled(int studentId, int courseId);
  


    void addReview(Review review, int courseId);
    void deleteReviewById(int id);
    void updateReview(Review review);
    Review findReviewById(int id);
    List<Review> findAllReview();
    void deleteAllReview();


    void saveStudent(Student theStudent);
    Student findStudentById(int theId);
    void updateStudent(Student theStudent);
    void deleteStudentById(int theId);
    void deleteAllStudent();
    List<Student> findAllStudent();
    void saveStudentDetails(int id, StudentDetails studentDetails);

    void setStudentsForCourse(List<Student> students, int courseId);
    void setCoursesForStudent(List<Course> courses, int studentId);

    boolean isStudent(int studentId);
    boolean isProfessor(int professorId);

}
