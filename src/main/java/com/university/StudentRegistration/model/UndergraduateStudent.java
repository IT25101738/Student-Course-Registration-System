package com.university.StudentRegistration.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class UndergraduateStudent extends Student {

    private String highSchoolName;

    public UndergraduateStudent(String name, String email, String major, String password, String highSchoolName) {
        super(name, email, major, password);
        this.highSchoolName = highSchoolName;
    }

    // Polymorphism: Overriding the parent method
    @Override
    public String getProfileDisplayFormat() {
        return super.getProfileDisplayFormat() + " [Status: Undergraduate]";
    }
}
