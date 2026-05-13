package com.university.StudentRegistration.model;

public class LetterGrade extends Grade {

    public LetterGrade(String gradeValue) {
        super(gradeValue);
    }

    @Override
    public double calculateGPA() {
        return switch (gradeValue.toUpperCase().trim()) {
            case "A+"  -> 4.0;
            case "A"   -> 4.0;
            case "A-"  -> 3.7;
            case "B+"  -> 3.3;
            case "B"   -> 3.0;
            case "B-"  -> 2.7;
            case "C+"  -> 2.3;
            case "C"   -> 2.0;
            case "C-"  -> 1.7;
            case "D+"  -> 1.3;
            case "D"   -> 1.0;
            case "F"   -> 0.0;
            default    -> 0.0;
        };
    }

    @Override
    public String getGradeType() {
        return "Letter Grade";
    }
}
