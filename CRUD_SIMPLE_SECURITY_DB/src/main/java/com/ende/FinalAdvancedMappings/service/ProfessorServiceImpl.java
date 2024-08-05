package com.ende.FinalAdvancedMappings.service;

import com.ende.FinalAdvancedMappings.dao.ProfessorDAO;
import com.ende.FinalAdvancedMappings.entity.*;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfessorServiceImpl implements ProfessorService{

    private final ProfessorDAO profDAO;

    @Autowired
    public ProfessorServiceImpl(ProfessorDAO profDAO){
        this.profDAO = profDAO;
    }

    @Override
    @Transactional
    public void saveProfessor(Professor theProfessor) {
        profDAO.saveProfessor(theProfessor);
    }

    @Override
    @Transactional
    public Professor findProfessorById(int theId) {
        return profDAO.findProfessorById(theId);
    }

    @Override
    public Professor findProfessorByEmail(String email){
        return profDAO.findProfessorByEmail(email);
    }

    @Override
    @Transactional
    public void updateProfessor(Professor theProfessor) {
        profDAO.updateProfessor(theProfessor);
    }

    @Override
    @Transactional
    public void deleteProfessorById(int theId) {
        profDAO.deleteProfessorById(theId);
    }

    @Override
    @Transactional
    public void deleteAllProfessor(){
        profDAO.deleteAllProfessor();
    }

    @Override
    public List<Professor> findAllProfessor() {
        return profDAO.findAllProfessor();
    }

    @Override
    @Transactional
    public void deleteProfessorDetailsById(int id){
        profDAO.deleteProfessorDetailsById(id);
    }

    @Override
    @Transactional
    public void saveProfessorDetails(int id, ProfessorDetails professorDetails){
        profDAO.saveProfessorDetails(id, professorDetails);
    }

    @Override
    @Transactional
    public void saveCourse(Course course, int professorId) {
        profDAO.saveCourse(course, professorId);
    }

    @Override
    public List<Course> findAllCourses() {
        return profDAO.findAllCourses();
    }

    @Override
    public List<Course> findCoursesByProfessorId(int id) {
        return null;
    }

    @Override
    public Course findCourseById(int id) {
        return profDAO.findCourseById(id);
    }

//    @Override
//    @Transactional
//    public void updateCourse(Course course) {
//        profDAO.updateCourse(course);
//    }

    @Override
    @Transactional
    public void deleteCourseById(int id) {
        profDAO.deleteCourseById(id);
    }

    @Override
    @Transactional
    public void deleteAllCourse(){
        profDAO.deleteAllCourse();
    }

    @Override
    public boolean isEnrolled(int studentId, int courseId){
        return profDAO.isEnrolled(studentId, courseId);
    }

    


    @Override
    @Transactional
    public void addReview(Review review, int courseId){
        profDAO.addReview(review, courseId);
    }

    @Override
    @Transactional
    public void deleteReviewById(int id) {
        profDAO.deleteReviewById(id);
    }

    @Override
    @Transactional
    public void updateReview(Review review) {
        profDAO.updateReview(review);
    }

    @Override
    public Review findReviewById(int id) {
        return profDAO.findReviewById(id);
    }

    @Override
    public List<Review> findAllReview() {
        return profDAO.findAllReview();
    }

    @Override
    @Transactional
    public void deleteAllReview() {
        profDAO.deleteAllReview();
    }

    @Override
    @Transactional
    public void saveStudent(Student theStudent) {
        profDAO.saveStudent(theStudent);
    }

    @Override
    public Student findStudentById(int theId) {
        return profDAO.findStudentById(theId);
    }

    @Override
    @Transactional
    public void updateStudent(Student theStudent) {
        profDAO.updateStudent(theStudent);
    }

    @Override
    @Transactional
    public void deleteStudentById(int theId) {
        profDAO.deleteStudentById(theId);
    }

    @Override
    @Transactional
    public void deleteAllStudent() {
        profDAO.deleteAllStudent();
    }

    @Override
    public List<Student> findAllStudent() {
        return profDAO.findAllStudent();
    }

    @Override
    @Transactional
    public void saveStudentDetails(int id, StudentDetails studentDetails) {
        profDAO.saveStudentDetails(id, studentDetails);
    }

    @Override
    @Transactional
    public void setStudentsForCourse(List<Student> students, int courseId) {
        profDAO.setStudentsForCourse(students, courseId);
    }

    @Override
    @Transactional
    public void setCoursesForStudent(List<Course> courses, int studentId) {
        profDAO.setCoursesForStudent(courses, studentId);
    }

    @Override
    public boolean isStudent(int studentId){
        return profDAO.isStudent(studentId);
    }

    @Override
    public boolean isProfessor(int professorId){
        return profDAO.isProfessor(professorId);
    }
}




































