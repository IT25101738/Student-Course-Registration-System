package com.university.StudentRegistration.model;

import jakarta.persistence.*;

@Entity // tells spring the class is a database table
@Table(name = "students") // sets the table name student into students
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // handles inheritance in the database
@DiscriminatorColumn(name = "student_type") // Helps the database separate them cleanly
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-generates id using db auto-increment
    private Long id;

    private String name;
    private String email;
    private String major;
    private String password;

    //new fields
    private String birthday;
    private String contactNumber;
    private String address;

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

    public String getBirthday() {
        return birthday;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfileDisplayFormat() {
        return "Student: " + this.name + " | Major: " + this.major;
    }

    public String getStudentLevel() {
        return "General Student";
    }
}