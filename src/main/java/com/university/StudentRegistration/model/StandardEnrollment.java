package com.university.StudentRegistration.model;

public class StandardEnrollment extends EnrollmentPolicy {

    @Override
    public boolean checkCapacity(Course course, long currentStudents) {
        // Normal students get blocked if the course is full
        return currentStudents < course.getMaxCapacity();
    }
}
