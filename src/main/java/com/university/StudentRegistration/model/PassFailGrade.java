package com.university.StudentRegistration.model;

public class PassFailGrade extends Grade {

    public PassFailGrade(String gradeValue) {
        super(gradeValue);
    }

    @Override
    public double calculateGPA() {
        // -1 signals to the GPA calculator: exclude this course
        return -1.0;
    }

    public boolean isPassing() {
        return "P".equalsIgnoreCase(gradeValue.trim());
    }

    @Override
    public String getGradeType() {
        return "Pass/Fail";
    }
}
