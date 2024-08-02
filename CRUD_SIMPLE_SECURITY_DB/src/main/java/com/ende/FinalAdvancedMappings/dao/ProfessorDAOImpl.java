package com.ende.FinalAdvancedMappings.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ende.FinalAdvancedMappings.entity.Course;
import com.ende.FinalAdvancedMappings.entity.Professor;
import com.ende.FinalAdvancedMappings.entity.ProfessorDetails;
import com.ende.FinalAdvancedMappings.entity.Review;
import com.ende.FinalAdvancedMappings.entity.Student;
import com.ende.FinalAdvancedMappings.entity.StudentDetails;

import jakarta.persistence.EntityManager;

@Repository
public class ProfessorDAOImpl implements ProfessorDAO{

    private final EntityManager entityManager;

    @Autowired
    public ProfessorDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void saveProfessor(Professor theProfessor) {
       entityManager.merge(theProfessor);
    }

    @Override
    public Professor findProfessorById(int theId) {
        return entityManager.find(Professor.class, theId);
    }

    @Override
    public Professor findProfessorByEmail(String email){
        List<Professor> professors = findAllProfessor();
        for(Professor professor: professors){
            if(professor.getEmail().equals(email)){
                return professor;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public void updateProfessor(Professor theProfessor) {
        entityManager.merge(theProfessor);

    }

    @Override
    @Transactional
    public void deleteProfessorById(int theId) {
       Professor tempProfessor = entityManager.find(Professor.class, theId);
       List<Course> courses = tempProfessor.getCourses();
       for(Course course: courses){
           course.setProfessor(null);
       }
       entityManager.remove(tempProfessor);
    }

    @Override
    @Transactional
    public void deleteAllProfessor() {
        List<Professor> professors = entityManager.createQuery("SELECT p FROM Professor p", Professor.class)
                .getResultList();

        for (Professor professor : professors) {
            List<Course> courses = professor.getCourses();
            for(Course course: courses){
                course.setProfessor(null);
            }

            entityManager.remove(professor);
        }

    }

    @Override
    public List<Professor> findAllProfessor() {
        List<Professor> professors = entityManager.createQuery("SELECT p FROM Professor p", Professor.class).getResultList();
        return professors;
    }

    @Override
    @Transactional
    public void deleteProfessorDetailsById(int id) {
        ProfessorDetails professorDetails = entityManager.find(ProfessorDetails.class, id);
        entityManager.remove(professorDetails);
    }

    @Override
    @Transactional
    public void saveProfessorDetails(int id, ProfessorDetails professorDetails){
        Professor prof = entityManager.find(Professor.class, id);
        if(prof!=null){
            prof.setProfessorDetails(professorDetails);
            entityManager.merge(prof);
        }

    }

    @Override
    @Transactional
    public void saveCourse(Course course, int professorId) {
        Course existingCourse = entityManager.find(Course.class, course.getId());

        if (existingCourse != null) {
            existingCourse.setTitle(course.getTitle());

            if (course.getProfessor() != null) {
                Professor professor = entityManager.find(Professor.class, course.getProfessor().getId());
                existingCourse.setProfessor(professor);
            } else {
                existingCourse.setProfessor(null);
            }
            existingCourse.setReviews(course.getReviews());
            existingCourse.setStudents(course.getStudents());
        } else {
            Professor professor = findProfessorById(professorId);
            course.setProfessor(professor);
            entityManager.persist(course);
        }

    }

    @Override
    public List<Course> findAllCourses() {
        List<Course> courses = entityManager.createQuery("SELECT c FROM Course c", Course.class).getResultList();
        return courses;
    }

    @Override
    public Course findCourseById(int id) {
        return entityManager.find(Course.class, id);
    }

    //
//          /saveCourse used for both saving and updating
//
//
//    @Override
//    @Transactional
//    public void updateCourse(Course course) {
//        Course existingCourse = entityManager.find(Course.class, course.getId());
//
//        if (existingCourse != null) {
//            existingCourse.setTitle(course.getTitle());
//
//            if (course.getProfessor() != null) {
//                Professor professor = entityManager.find(Professor.class, course.getProfessor().getId());
//                existingCourse.setProfessor(professor);
//            } else {
//                existingCourse.setProfessor(null);
//            }
//            existingCourse.setReviews(course.getReviews());
//            existingCourse.setStudents(course.getStudents());
//        } else {
//            throw new EntityNotFoundException("Course not found with ID: " + course.getId());
//        }
//    }

    @Override
    @Transactional
    public void deleteCourseById(int id) {
        Course tempCourse = entityManager.find(Course.class, id);
        if (tempCourse != null) {
            if (tempCourse.getProfessor() != null) {
                tempCourse.getProfessor().getCourses().remove(tempCourse);
            }
            tempCourse.getReviews().clear();
            entityManager.remove(tempCourse);

        }
    }

    @Override
    public List<Course> findCoursesByProfessorId(int id) {
        return null;
    }

    @Override
    public Professor findProfessorByCourseId(int id) {
        return null;
    }

    @Override
    @Transactional
    public void deleteAllCourse() {
        List<Course> courses = entityManager.createQuery("SELECT c FROM Course c", Course.class)
                .getResultList();

        for (Course course : courses) {
            entityManager.remove(course);
        }

    }

    @Override
    @Transactional
    public void addReview(Review review, int courseId){
        Course course = entityManager.find(Course.class, courseId);
        review.setCourse(course);
        entityManager.persist(review);
    }

    @Override
    @Transactional
    public void deleteReviewById(int id) {
        Review review = entityManager.find(Review.class, id);
        entityManager.remove(review);
    }

    @Override
    @Transactional
    public void updateReview(Review review) {
        entityManager.merge(review);
    }

    @Override
    public Review findReviewById(int id) {
        return entityManager.find(Review.class, id);
    }

    @Override
    public List<Review> findAllReview() {
        List<Review> reviews = entityManager.createQuery("SELECT r FROM Review r", Review.class).getResultList();
        return reviews;
    }

    @Override
    @Transactional
    public void deleteAllReview() {
        List<Review> reviews = entityManager.createQuery("SELECT r FROM Review r", Review.class).getResultList();
        for(Review review: reviews){
            entityManager.remove(review);
        }
    }

    @Override
    @Transactional
    public void saveStudent(Student theStudent) {
        entityManager.merge(theStudent);
    }

    @Override
    public Student findStudentById(int theId) {
        return entityManager.find(Student.class, theId);
    }

    @Override
    @Transactional
    public void updateStudent(Student theStudent) {
        entityManager.merge(theStudent);
    }

    @Override
    @Transactional
    public void deleteStudentById(int theId) {
        Student tempStudent = entityManager.find(Student.class, theId);
        entityManager.remove(tempStudent);
    }

    @Override
    @Transactional
    public void deleteAllStudent() {
        List<Student> students = entityManager.createQuery("SELECT s FROM Student s", Student.class)
                .getResultList();

        for (Student student : students) {
            entityManager.remove(student);
        }
    }

    @Override
    public List<Student> findAllStudent() {
        List<Student> students = entityManager.createQuery("SELECT s FROM Student s", Student.class).getResultList();
        return students;
    }

    @Override
    @Transactional
    public void deleteStudentDetailsById(int id) {
        StudentDetails studentDetails = entityManager.find(StudentDetails.class, id);
        entityManager.remove(studentDetails);
    }

    @Override
    @Transactional
    public void saveStudentDetails(int id, StudentDetails studentDetails) {
        Student student = entityManager.find(Student.class, id);
        if(student!=null){
            student.setStudentDetails(studentDetails);
            entityManager.merge(student);
        }
    }

    @Override
    @Transactional
    public void setStudentsForCourse(List<Student> students, int courseId) {
        Course course = entityManager.find(Course.class, courseId);
        List<Student> studentsList = course.getStudents();
        studentsList.addAll(students);
        course.setStudents(studentsList);
        entityManager.persist(course);
    }

    @Override
    @Transactional
    public void setCoursesForStudent(List<Course> courses, int studentId) {
        Student student = entityManager.find(Student.class, studentId);
        List<Course> courseList = student.getCourses();
        courseList.addAll(courses);
        student.setCourses(courseList);
        entityManager.persist(student);
    }


}






























