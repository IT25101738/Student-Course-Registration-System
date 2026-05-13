package com.university.StudentRegistration.model;

public abstract class Grade {

    protected String gradeValue;

    public Grade(String gradeValue) {
        this.gradeValue = gradeValue;
    }

    public String getGradeValue() {
        return gradeValue;
    }

  
    public abstract double calculateGPA();


    public abstract String getGradeType();
}
