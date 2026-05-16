package com.university.StudentRegistration.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

// Inheritance: FullTimeProfessor extends Instructor
@Entity
@DiscriminatorValue("FULLTIME") // stored in the faculty_type column
public class FullTimeProfessor extends Instructor {

    private String officeHours; // e.g. "Mon/Wed 10:00–12:00"

    // Spring Boot needs an empty constructor behind the scenes
    public FullTimeProfessor() {
    }

    public FullTimeProfessor(String name, String department, String employeeId,
                             String email, String password, String officeHours) {
        super(name, department, employeeId, email, password);
        this.officeHours = officeHours;
    }

    // Polymorphism: Full-time professors can teach up to 4 courses
    @Override
    public int getMaxTeachingLoad() {
        return 4;
    }

    // Polymorphism: overrides the display format from the parent class
    @Override
    public String getProfileDisplayFormat() {
        return super.getProfileDisplayFormat() + " [Type: Full-Time Professor | Max Load: 4 courses]";
    }

    public String getOfficeHours() {
        return officeHours;
    }

    public void setOfficeHours(String officeHours) {
        this.officeHours = officeHours;
    }
}
