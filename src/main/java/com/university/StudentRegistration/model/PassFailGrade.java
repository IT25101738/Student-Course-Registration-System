package com.university.StudentRegistration.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PASS_FAIL")
public class PassFailGrade extends Grade {
    private double passBoundary = 50.0;

    public PassFailGrade() {}

    public PassFailGrade(double passBoundary) {
        this.passBoundary = passBoundary;
    }

    public double getPassBoundary() {
        return passBoundary;
    }

    public void setPassBoundary(double passBoundary) {
        this.passBoundary = passBoundary;
    }

    @Override
    public double calculateGPA(double numericMark) {
        return -1.0; // Indicate it's not part of GPA average
    }

    @Override
    public String getGradeString(double numericMark) {
        return numericMark >= passBoundary ? "Pass" : "Fail";
    }
}