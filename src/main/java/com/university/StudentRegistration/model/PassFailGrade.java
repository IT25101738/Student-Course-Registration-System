package com.university.StudentRegistration.model;

/**
 * PassFailGrade — extends Grade (OOP: Inheritance).
 * Handles Pass/Fail grading: "P" or "F".
 * Pass/Fail courses typically do NOT affect GPA — returns 0 contribution.
 * Overrides calculateGPA() and getGradeType() — Polymorphism.
 */
public class PassFailGrade extends Grade {

    public PassFailGrade(String gradeValue) {
        super(gradeValue);
    }

    /**
     * Pass/Fail courses do not factor into GPA calculation.
     * Returns -1.0 as a sentinel to signal "exclude from GPA".
     */
    @Override
    public double calculateGPA() {
        // -1 signals to the GPA calculator: exclude this course
        return -1.0;
    }

    /**
     * Returns true if the student passed the course.
     */
    public boolean isPassing() {
        return "P".equalsIgnoreCase(gradeValue.trim());
    }

    @Override
    public String getGradeType() {
        return "Pass/Fail";
    }
}
