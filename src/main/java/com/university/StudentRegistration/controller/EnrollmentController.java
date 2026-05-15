package com.university.StudentRegistration.controller;

import com.university.StudentRegistration.model.Enrollment;
import com.university.StudentRegistration.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @PostMapping("/enroll")
    public Enrollment enroll(@RequestBody Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    // 2. READ: Get a student's schedule
    @GetMapping("/student/{id}")
    public List<Enrollment> getSchedule(@PathVariable Long id) {
        return enrollmentRepository.findByStudentId(id);
    }
}

    // 3. UPDATE: Change enrollment status (e.g., to 'DROPPED')
    @PutMapping("/update/{id}")
    public Enrollment updateStatus(@PathVariable Long id, @RequestParam String status) {
        Enrollment e = enrollmentRepository.findById(id).orElseThrow();
        e.setStatus(status);
        return enrollmentRepository.save(e);
    }

    // 4. DELETE: Drop a course completely
    @DeleteMapping("/delete/{id}")
    public String deleteEnrollment(@PathVariable Long id) {
        enrollmentRepository.deleteById(id);
        return "Enrollment successfully removed!";
    }
}