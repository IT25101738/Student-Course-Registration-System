package com.university.StudentRegistration.controller;

import com.university.StudentRegistration.model.Enrollment;
import com.university.StudentRegistration.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @PostMapping("/enroll")
    public Enrollment enroll(@RequestBody Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }
}
