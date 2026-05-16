package com.university.StudentRegistration.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

// Inheritance: AdjunctProfessor extends Instructor
@Entity
@DiscriminatorValue("ADJUNCT") // stored in the faculty_type column
public class AdjunctProfessor extends Instructor {

    private String contractEndDate; // e.g. "2026-12-31"

    // Spring Boot needs an empty constructor behind the scenes
    public AdjunctProfessor() {
    }

    public AdjunctProfessor(String name, String department, String employeeId,
                            String email, String password, String contractEndDate) {
        super(name, department, employeeId, email, password);
        this.contractEndDate = contractEndDate;
    }

    // Polymorphism: Adjunct professors can only teach up to 2 courses
    @Override
    public int getMaxTeachingLoad() {
        return 2;
    }

    // Polymorphism: overrides the display format from the parent class
    @Override
    public String getProfileDisplayFormat() {
        return super.getProfileDisplayFormat() + " [Type: Adjunct Professor | Max Load: 2 courses]";
    }

    public String getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(String contractEndDate) {
        this.contractEndDate = contractEndDate;
    }
}
