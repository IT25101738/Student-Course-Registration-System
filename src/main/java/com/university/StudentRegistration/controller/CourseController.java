package com.university.StudentRegistration.controller;

import com.university.StudentRegistration.model.CoreCourse;
import com.university.StudentRegistration.model.Course;
import com.university.StudentRegistration.model.ElectiveCourses;
import com.university.StudentRegistration.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/all")
    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    @PostMapping("/add")
    public Course addCourse(@RequestBody Course course){
        return courseRepository.save(course);
    }

    @PostMapping("/api/courses/add")
    public ResponseEntity<String> addCourse(@RequestBody Map<String, Object> payload) {
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
    }

    @DeleteMapping("/api/courses/delete/{courseCode}")
    public ResponseEntity<String> deleteCourse(@PathVariable String courseCode) {
        try {
            courseRepository.deleteById(courseCode);
            return ResponseEntity.ok("Course successfully removed from catalog.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting course. It might be tied to existing student registrations.");
        }
    }
}
