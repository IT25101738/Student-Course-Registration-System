package com.university.StudentRegistration.model;

import jakarta.persistence.*;

@Entity
@Table(name = "course_registrations")
public class CourseRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentEmail;
    private String courseCode;

    public CourseRegistration(){}

    public CourseRegistration(String studentEmail, String courseCode){
        this.studentEmail=studentEmail;
        this.courseCode=courseCode;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public String getCourseCode() {
        return courseCode;
    }
}
