package com.university.StudentRegistration.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // automatically creates all getters, setters, and constructors....
@NoArgsConstructor // Spring Boot needs an empty constructor behind the scenes
@Entity // tells spring the class is a database table
@Table(name = "students") // sets the table name student into students
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-generates id using db auto-increment
    private Long id;

    private String name;
    private String email;
    private String major;
    private String password;

    public Student(String name, String email, String major, String password) {
        this.name = name;
        this.email = email;
        this.major = major;
        this.password = password;
    }

    // Polymorphism: Base Method
    public String getProfileDisplayFormat() {
        return "Student: " + this.name + " | Major: " + this.major;
    }
}
