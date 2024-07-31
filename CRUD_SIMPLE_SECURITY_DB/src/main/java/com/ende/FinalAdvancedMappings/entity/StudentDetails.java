package com.ende.FinalAdvancedMappings.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "student_details")
public class StudentDetails {

    @OneToOne(mappedBy = "studentDetails", cascade = CascadeType.ALL)
    private Student student;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "role")
    private String role;

    @Column(name = "password")
    private String password;

    public StudentDetails() {
    }

    public StudentDetails(String role, String password) {
        this.role = role;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "StudentDetails{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
