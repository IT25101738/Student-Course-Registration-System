package com.university.StudentRegistration.controller;

import com.university.StudentRegistration.model.Course;
import com.university.StudentRegistration.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
