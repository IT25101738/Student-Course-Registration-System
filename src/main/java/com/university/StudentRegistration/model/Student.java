package com.university.StudentRegistration.model;

import jakarta.persistence.*;
import lombok.Data;

@Data // automatically creates all getters, setters, and constructors....
@Entity // tells spring the class is a database table
@Table(name = "students") // sets the table name student into students

public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-generates id using db auto-increment
    private Long id;

    private String name;
    private String email;
    private String major;
    private String password;
}
