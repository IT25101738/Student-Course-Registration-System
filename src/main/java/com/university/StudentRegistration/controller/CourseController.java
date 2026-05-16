package com.university.StudentRegistration.controller;

import com.university.StudentRegistration.model.CoreCourse;
import com.university.StudentRegistration.model.Course;
import com.university.StudentRegistration.model.ElectiveCourses;
import com.university.StudentRegistration.repository.CourseRepository;
import com.university.StudentRegistration.repository.CourseRegistrationRepository;
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

    // FIXED: Swapped the deleted Enrollment repo for our official Registration repo
    @Autowired
    private CourseRegistrationRepository registrationRepository;

    // THIS IS WHAT POWERS YOUR FRIEND'S CATALOG - It is completely safe!
    @GetMapping("/all")
    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    // FIXED: Removed the duplicate /add method. This one perfectly demonstrates Polymorphism!
    @PostMapping("/add")
    public ResponseEntity<String> addCourse(@RequestBody Map<String, Object> payload) {
        String type = (String) payload.get("courseType");
        String code = (String) payload.get("courseCode");
        String title = (String) payload.get("title");
        int credits = Integer.parseInt(payload.get("credits").toString());

        Course newCourse;

        // Polymorphism (Lecture 05): Decide which child object to create
        if ("CORE".equalsIgnoreCase(type)) {
            newCourse = new CoreCourse(code, title, credits);
        } else {
            newCourse = new ElectiveCourses(code, title, credits);
        }

        courseRepository.save(newCourse);
        return ResponseEntity.ok("Course saved successfully!");
    }

    @DeleteMapping("/delete/{courseCode}")
    @Transactional
    public ResponseEntity<String> deleteCourse(@PathVariable String courseCode) {
        try {
            // FIXED: We only have one registration table now, so we only need to clear that one!
            registrationRepository.deleteByCourseCode(courseCode);

            // Now that the students are safely un-enrolled, we can delete the course
            courseRepository.deleteById(courseCode);

            return ResponseEntity.ok("Course fully removed.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{courseCode}")
    public ResponseEntity<String> updateCourse(@PathVariable String courseCode, @RequestBody Map<String, Object> payload) {
        System.out.println("DEBUG: Attempting to update course: " + courseCode);

        return courseRepository.findById(courseCode).map(course -> {

            // Encapsulation: Safely using setters
            course.setTitle((String) payload.get("title"));
            course.setCredits(Integer.parseInt(payload.get("credits").toString()));

            courseRepository.save(course);
            return ResponseEntity.ok("Success");

        }).orElseGet(() -> {
            return ResponseEntity.notFound().build();
        });
    }
}