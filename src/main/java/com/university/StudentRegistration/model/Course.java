package com.university.StudentRegistration.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "course_type")
public class Course {

    @Id
    private String courseCode;
    private String title;
    private int credits;
    private String description;

    //course enrollment part................
    private int maxCapacity = 50; // Default capacity

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }



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

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
