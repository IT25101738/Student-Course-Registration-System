package com.university.StudentRegistration.controller;

import com.university.StudentRegistration.model.Student;
import com.university.StudentRegistration.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController // tells the spring this class handles web requests
@RequestMapping("/api/students") // base URL (so all endpoints starts with /api/students)
public class StudentController {

    @Autowired //spring automatically injects the repository here
    private StudentRepository studentRepository;

    // CREATE: Register a new student
    @PostMapping("/register") // post use to assign values to db
    public Student createStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    // READ: The Login Method
    @PostMapping("/login")
    public Student loginStudent(@RequestBody Student loginRequest) {

        // first ask the repository to search the database for this email
        Student existingStudent = studentRepository.findByEmail(loginRequest.getEmail());

        // then check if the student exists and if the password matches
        if (existingStudent != null && existingStudent.getPassword().equals(loginRequest.getPassword())) {
            return existingStudent; // Success! Send the student data back to the UI.
        } else {
            throw new RuntimeException("Invalid email or password"); // Fail!
        }
    }

    // UPDATE: Change a student's profile details
    @PutMapping("/update")
    public Student updateStudentProfile(@RequestBody Student updatedData) {

        // 1. Search the database for the student's email
        Student existingStudent = studentRepository.findByEmail(updatedData.getEmail());

        if (existingStudent != null) {
            // 2. If found, update their Name and Major
            existingStudent.setName(updatedData.getName());
            existingStudent.setMajor(updatedData.getMajor());

            // 3. Save the changes back to MySQL
            return studentRepository.save(existingStudent);
        } else {
            throw new RuntimeException("Student not found!");
        }
    }

    // DELETE: Remove a student account
    @DeleteMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
        return "Student with ID " + id + " has been successfully deleted.";
    }
}
