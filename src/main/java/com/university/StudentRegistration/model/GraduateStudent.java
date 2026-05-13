package com.university.StudentRegistration.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class GraduateStudent extends Student {

    private String researchThesisTitle;

    public GraduateStudent(String name, String email, String major, String password, String thesisTitle) {
        super(name, email, major, password);
        this.researchThesisTitle = thesisTitle;
    }

    // Polymorphism: Overriding the parent method
    @Override
    public String getProfileDisplayFormat() {
        return super.getProfileDisplayFormat() + " [Status: Graduate Researcher]";
    }
}
