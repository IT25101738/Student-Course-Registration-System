package com.university.StudentRegistration.model;

public abstract class EnrollmentPolicy {

    // ABSTRACTION: An abstract method with no body.
    // The child classes MUST define how this works.
    public abstract boolean checkCapacity(Course course, long currentStudents);

}