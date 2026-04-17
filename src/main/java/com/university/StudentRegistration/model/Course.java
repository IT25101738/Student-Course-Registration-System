package com.university.StudentRegistration.model;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "course_type")
public class Course {

    @Id
    private String courseCode;
    private String title;
    private int credits;

    public Course(){

    }

    Course(String courseCode, String title, int credits){
        this.courseCode=courseCode;
        this.title=title;
        this.credits=credits;
    }

    public String getCategory(){
        return "General Course";
    }

    public String getCourseCode(){
        return courseCode;
    }

    public String getTitle(){
        return title;
    }
}
