package com.university.StudentRegistration.model;

import jakarta.persistence.*;

@Entity // tells spring the class is a database table
@Table(name = "students") // sets the table name student into students
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // handles inheritance in the database
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-generates id using db auto-increment
    private Long id;

    private String name;
    private String email;
    private String major;
    private String password;

    // Spring Boot needs an empty constructor behind the scenes
    public Student() {
    }

    public Student(String name, String email, String major, String password) {
        this.name = name;
        this.email = email;
        this.major = major;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileDisplayFormat() {
        return "Student: " + this.name + " | Major: " + this.major;
    }
}