package com.university.StudentRegistration.model;

import jakarta.persistence.Entity;

@Entity // tells spring the class is a database table
public class GraduateStudent extends Student {
    private String researchThesisTitle;

    // Spring Boot needs an empty constructor behind the scenes
    public GraduateStudent() {
    }

    public GraduateStudent(String name, String email, String major, String password, String thesisTitle) {
        super(name, email, major, password);
        this.researchThesisTitle = thesisTitle;
    }

    public String getResearchThesisTitle() {
        return researchThesisTitle;
    }

    public void setResearchThesisTitle(String researchThesisTitle) {
        this.researchThesisTitle = researchThesisTitle;
    }

    @Override
    public String getProfileDisplayFormat() {
        return super.getProfileDisplayFormat() + " [Status: Graduate Researcher]";
    }
}
