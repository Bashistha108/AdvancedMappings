package com.ende.FinalAdvancedMappings.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "professor_details")
public class ProfessorDetails {

    @OneToOne(mappedBy = "professorDetails", cascade = {CascadeType.DETACH,CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})
    private Professor professor;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name="role")
    private String role;

    @Column(name = "password")
    private String password;

    public ProfessorDetails() {
    }

    public ProfessorDetails(String role, String password) {
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
        return "{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
