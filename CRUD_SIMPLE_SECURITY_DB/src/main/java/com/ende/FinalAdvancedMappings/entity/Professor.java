package com.ende.FinalAdvancedMappings.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "professors")
public class Professor {

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "professor", cascade = {CascadeType.DETACH,CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    private List<Course> courses;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @OneToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH, CascadeType.PERSIST})
    @JoinColumn(name = "professor_details_id")
    private ProfessorDetails professorDetails;

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Professor() {
    }

    public Professor(Course tempCourse) {
        tempCourse.setProfessor(this);
    }

    public Professor(String firstName, String lastName, String email, ProfessorDetails professorDetails) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.professorDetails = professorDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ProfessorDetails getProfessorDetails() {
        return professorDetails;
    }

    public void setProfessorDetails(ProfessorDetails professorDetails) {
        this.professorDetails = professorDetails;
    }


    @Override
    public String toString() {
        return "Professor{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", professorDetails=" + professorDetails +
                '}';
    }
}
