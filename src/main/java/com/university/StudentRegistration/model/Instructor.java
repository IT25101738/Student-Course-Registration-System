package com.university.StudentRegistration.model;

import jakarta.persistence.*;

@Entity // tells Spring this class maps to a database table
@Table(name = "instructors") // sets the table name
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // handles OOP inheritance in one DB table
@DiscriminatorColumn(name = "faculty_type") // column that identifies the subclass type
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-generates id using DB auto-increment
    private Long id;

    private String name;
    private String department;
    private String employeeId;   // unique identifier e.g. "FAC-001"
    private String email;
    private String password;     // used for admin delete verification (mirrors Student pattern)
    private String assignedCourseCode; // nullable — stores the course code this instructor teaches

    // Spring Boot needs an empty constructor behind the scenes
    public Instructor() {
    }

    public Instructor(String name, String department, String employeeId, String email, String password) {
        this.name = name;
        this.department = department;
        this.employeeId = employeeId;
        this.email = email;
        this.password = password;
    }

    // --- Polymorphic method (overridden in subclasses) ---
    // Encapsulation: subclasses control their own teaching load rule
    public int getMaxTeachingLoad() {
        return 0; // base implementation — subclasses override this
    }

    // Mirrors the getProfileDisplayFormat() pattern from Student.java
    public String getProfileDisplayFormat() {
        return "Instructor: " + this.name + " | Department: " + this.department;
    }

    // --- Getters and Setters (Encapsulation) ---

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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAssignedCourseCode() {
        return assignedCourseCode;
    }

    public void setAssignedCourseCode(String assignedCourseCode) {
        this.assignedCourseCode = assignedCourseCode;
    }
}
