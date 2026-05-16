package com.university.StudentRegistration.controller;

import com.university.StudentRegistration.model.CoreCourse;
import com.university.StudentRegistration.model.Course;
import com.university.StudentRegistration.model.ElectiveCourses;
import com.university.StudentRegistration.repository.CourseRepository;
import com.university.StudentRegistration.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;


    @GetMapping("/all")
    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    @PostMapping("/add") // This combines with the class mapping to make exactly "/api/courses/add"
    public ResponseEntity<String> addCourse(@RequestBody Map<String, Object> payload) {
        try {
            String type = (String) payload.get("courseType");
            String code = (String) payload.get("courseCode");
            String title = (String) payload.get("title");
            int credits = Integer.parseInt(payload.get("credits").toString());

            Course newCourse;

            // Apply Inheritance logic to decide which object to create
            if ("CORE".equalsIgnoreCase(type)) {
                newCourse = new CoreCourse(code, title, credits);
            } else {
                newCourse = new ElectiveCourses(code, title, credits);
            }

            courseRepository.save(newCourse);
            return ResponseEntity.ok("Course saved successfully!");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{courseCode}")
    @Transactional
    public ResponseEntity<String> deleteCourse(@PathVariable String courseCode) {
        try {
            // Step A: Clear table 1
            enrollmentRepository.deleteFromEnrollments(courseCode);

            // Step B: Clear table 2 (the hidden one!)
            enrollmentRepository.deleteFromRegistrations(courseCode);

            // Step C: Finally delete the course
            courseRepository.deleteById(courseCode);

            return ResponseEntity.ok("Course fully removed.");
        } catch (Exception e) {
            // This will now show the REAL error in your IntelliJ console
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{courseCode}")
    public ResponseEntity<String> updateCourse(@PathVariable String courseCode, @RequestBody Map<String, Object> payload) {
        System.out.println("DEBUG: Attempting to update course: " + courseCode); // Track this in IntelliJ console

        return courseRepository.findById(courseCode).map(course -> {
            System.out.println("DEBUG: Course found! Old title: " + course.getTitle());

            course.setTitle((String) payload.get("title"));
            course.setCredits(Integer.parseInt(payload.get("credits").toString()));

            courseRepository.save(course);
            System.out.println("DEBUG: Save command sent to MySQL.");

            return ResponseEntity.ok("Success");
        }).orElseGet(() -> {
            System.out.println("DEBUG: ERROR - Course code " + courseCode + " was not found.");
            return ResponseEntity.notFound().build();
        });
    }
}
