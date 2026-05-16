package com.university.StudentRegistration.model;

public class AdminOverrideEnrollment extends EnrollmentPolicy {

    @Override
    public boolean checkCapacity(Course course, long currentStudents) {
        // Admins can force an enrollment even if it is past max capacity!
        return true;
    }
}