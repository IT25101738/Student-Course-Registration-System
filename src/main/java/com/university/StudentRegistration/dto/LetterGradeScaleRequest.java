package com.university.StudentRegistration.dto;

public class LetterGradeScaleRequest {
    private double aBoundary;
    private double bBoundary;
    private double cBoundary;
    private double dBoundary;

    public double getABoundary() { return aBoundary; }
    public void setABoundary(double aBoundary) { this.aBoundary = aBoundary; }

    public double getBBoundary() { return bBoundary; }
    public void setBBoundary(double bBoundary) { this.bBoundary = bBoundary; }

    public double getCBoundary() { return cBoundary; }
    public void setCBoundary(double cBoundary) { this.cBoundary = cBoundary; }

    public double getDBoundary() { return dBoundary; }
    public void setDBoundary(double dBoundary) { this.dBoundary = dBoundary; }
}
