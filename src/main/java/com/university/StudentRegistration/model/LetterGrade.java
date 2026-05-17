package com.university.StudentRegistration.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("LETTER")
public class LetterGrade extends Grade {
    private double aBoundary = 90.0;
    private double bBoundary = 80.0;
    private double cBoundary = 70.0;
    private double dBoundary = 60.0;

    public LetterGrade() {}

    public LetterGrade(double aBoundary, double bBoundary, double cBoundary, double dBoundary) {
        this.aBoundary = aBoundary;
        this.bBoundary = bBoundary;
        this.cBoundary = cBoundary;
        this.dBoundary = dBoundary;
    }

    public double getABoundary() { return aBoundary; }
    public void setABoundary(double aBoundary) { this.aBoundary = aBoundary; }

    public double getBBoundary() { return bBoundary; }
    public void setBBoundary(double bBoundary) { this.bBoundary = bBoundary; }

    public double getCBoundary() { return cBoundary; }
    public void setCBoundary(double cBoundary) { this.cBoundary = cBoundary; }

    public double getDBoundary() { return dBoundary; }
    public void setDBoundary(double dBoundary) { this.dBoundary = dBoundary; }

    @Override
    public double calculateGPA(double numericMark) {
        if (numericMark >= aBoundary) return 4.0;
        if (numericMark >= bBoundary) return 3.0;
        if (numericMark >= cBoundary) return 2.0;
        if (numericMark >= dBoundary) return 1.0;
        return 0.0;
    }

    @Override
    public String getGradeString(double numericMark) {
        if (numericMark >= aBoundary) return "A";
        if (numericMark >= bBoundary) return "B";
        if (numericMark >= cBoundary) return "C";
        if (numericMark >= dBoundary) return "D";
        return "F";
    }
}

