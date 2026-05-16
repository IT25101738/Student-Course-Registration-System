package com.university.StudentRegistration.controller;

import com.university.StudentRegistration.model.Course;
import com.university.StudentRegistration.model.CourseRegistration;
import com.university.StudentRegistration.repository.CourseRegistrationRepository;
import com.university.StudentRegistration.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.university.StudentRegistration.model.EnrollmentPolicy;
import com.university.StudentRegistration.model.AdminOverrideEnrollment;
import com.university.StudentRegistration.model.StandardEnrollment;

import java.util.List;

@RestController
@RequestMapping("/api/registrations")
@CrossOrigin
public class CourseRegistrationController {

    @Autowired
    private CourseRegistrationRepository registrationRepository;

    @Autowired
    private CourseRepository courseRepository;

    @PostMapping("/register-course")
    public ResponseEntity<String> registerForCourse(@RequestBody CourseRegistration request) {

        // 1. Get the course they are trying to join
        Course courseToJoin = courseRepository.findById(request.getCourseCode()).orElse(null);
        if (courseToJoin == null) {
            return ResponseEntity.badRequest().body("Error: Course not found.");
        }

        // --- 2. CAPACITY VALIDATION WITH POLYMORPHISM ---
        long currentStudents = registrationRepository.countByCourseCode(request.getCourseCode());

        EnrollmentPolicy policy;

        // Let's pretend any email with "admin" in it gets the special override powers
        if (request.getStudentEmail().contains("admin")) {
            policy = new AdminOverrideEnrollment(); // Uses the Admin rule
        } else {
            policy = new StandardEnrollment(); // Uses the Normal rule
        }

        // ABSTRACTION IN ACTION: We just call checkCapacity(), and Java figures out which version to use!
        if (!policy.checkCapacity(courseToJoin, currentStudents)) {
            return ResponseEntity.badRequest().body("Error: Course is currently full!");
        }

        // 3. GET CURRENT CLASSES FOR CREDIT & DUPLICATE CHECKS
        List<CourseRegistration> currentClasses = registrationRepository.findByStudentEmail(request.getStudentEmail());

        // Start counting credits with the course they are trying to add right now
        int totalCredits = courseToJoin.getCredits();

        for (int i = 0; i < currentClasses.size(); i++) {
            CourseRegistration currentReg = currentClasses.get(i);

            // 4. DUPLICATE VALIDATION (Bullet Point 4)
            if (currentReg.getCourseCode().equals(request.getCourseCode())) {
                return ResponseEntity.badRequest().body("Error: You are already registered for this course.");
            }

            // Add up the credits from their existing classes
            Course existingCourse = courseRepository.findById(currentReg.getCourseCode()).orElse(null);
            if (existingCourse != null) {
                totalCredits += existingCourse.getCredits();
            }
        }

        // 5. CREDIT VALIDATION (Bullet Point 3)
        if (totalCredits > 18) {
            return ResponseEntity.badRequest().body("Error: Cannot register. This exceeds the 18 credit limit!");
        }

        // 6. SUCCESS: If it passes all the checks, save it! (Bullet Point 1)
        registrationRepository.save(request);
        return ResponseEntity.ok("Successfully registered for " + request.getCourseCode());
    }
    // --- NEW: FOR THE SCHEDULE PAGE ---
    @GetMapping("/my-schedule/{email}")
    public List<CourseRegistration> getMySchedule(@PathVariable String email) {
        // Automatically finds all courses linked to this student's email
        return registrationRepository.findByStudentEmail(email);
    }

    // --- NEW: FOR THE DROP COURSE BUTTON ---
    @DeleteMapping("/drop/{id}")
    public ResponseEntity<String> dropCourse(@PathVariable Long id) {
        // Deletes the specific registration ID
        registrationRepository.deleteById(id);
        return ResponseEntity.ok("Course successfully dropped.");
    }
}