package com.ende.FinalAdvancedMappings.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.REFRESH,CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "professor_id")
    @JsonIgnore
    private Professor professor;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "course")
//    @JoinColumn(name = "course_id")
    private List<Review> reviews;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> students;

    @Transient
    private boolean isEnrolled;

    @Transient
    private int numberOfEnrolledStudents = 0;

    public Course() {
    }

    public Course(String title, Professor professor) {
        this.title = title;
        this.professor = professor;
        professor.getCourses().add(this);
    }




    public Course(Review review) {
        review.setCourse(this);
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public boolean isEnrolled() {
        return isEnrolled;
    }

    public void setEnrolled(boolean isEnrolled) {
        this.isEnrolled = isEnrolled;
    }

    public void setNumberOfEnrolledStudents(int numberOfEnrolledStudents){
        this.numberOfEnrolledStudents = numberOfEnrolledStudents;
    }

    public int getNumberOfEnrolledStudents(){
        return (students != null)?students.size():0;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", professor=" + professor +
                '}';
    }
}
