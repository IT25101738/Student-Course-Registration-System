package com.university.StudentRegistration.controller;

import com.university.StudentRegistration.model.CourseRegistration;
import com.university.StudentRegistration.repository.CourseRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseRegistrationController {

    @Autowired
    private CourseRegistrationRepository courseRegistrationRepository;

    @PostMapping("/register-course")
    public CourseRegistration registerForCourse(@RequestBody CourseRegistration reg){
        return courseRegistrationRepository.save(reg);
    }
}
