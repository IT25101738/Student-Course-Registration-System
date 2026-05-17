package com.university.StudentRegistration.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "grade_type")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = LetterGrade.class, name = "letter"),
        @JsonSubTypes.Type(value = PassFailGrade.class, name = "passfail")
})
public abstract class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public abstract double calculateGPA(double numericMark);

    public abstract String getGradeString(double numericMark);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}