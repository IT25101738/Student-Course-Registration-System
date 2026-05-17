package com.university.StudentRegistration.model;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;

@Entity // tells spring the class is a database table
@DiscriminatorValue("UNDERGRAD")
public class UndergraduateStudent extends Student {
    private String highSchoolName;

    // Spring Boot needs an empty constructor behind the scenes
    public UndergraduateStudent() {
    }

    public UndergraduateStudent(String name, String email, String major, String password, String highSchoolName) {
        super(name, email, major, password);
        this.highSchoolName = highSchoolName;
    }

    public String getHighSchoolName() {
        return highSchoolName;
    }

    public void setHighSchoolName(String highSchoolName) {
        this.highSchoolName = highSchoolName;
    }

    @Override
    public String getProfileDisplayFormat() {
        return super.getProfileDisplayFormat() + " [Status: Undergraduate]";
    }

    @Override
    public String getStudentLevel() {
        return "Undergraduate Student";
    }
}