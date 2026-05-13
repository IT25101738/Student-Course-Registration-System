package com.university.StudentRegistration.controller;

import com.university.StudentRegistration.model.Student;
import com.university.StudentRegistration.model.UndergraduateStudent;
import com.university.StudentRegistration.model.GraduateStudent;
import com.university.StudentRegistration.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController // tells the spring this class handles web requests
@RequestMapping("/api/students") // base URL (so all endpoints starts with /api/students)
public class StudentController {

    @Autowired //spring automatically injects the repository here
    private StudentRepository studentRepository;

    // CREATE: Register a new student (UPDATED FOR POLYMORPHISM)
    @PostMapping("/register") // post use to assign values to db
    public Student createStudent(@RequestBody java.util.Map<String, String> requestData) {

        // 1. Extract the data sent from our new HTML form
        String type = requestData.get("type");
        String name = requestData.get("name");
        String email = requestData.get("email");
        String major = requestData.get("major");
        String password = requestData.get("password");
        String extraInfo = requestData.get("extraInfo"); // This will be either High School or Thesis

        Student newStudent;

        // 2. POLYMORPHISM: Decide which exact object to create based on the dropdown!
        if ("GRAD".equals(type)) {
            // Use the GraduateStudent constructor
            newStudent = new GraduateStudent(name, email, major, password, extraInfo);
        } else {
            // Use the UndergraduateStudent constructor
            newStudent = new UndergraduateStudent(name, email, major, password, extraInfo);
        }

        // 3. Save it. Spring Boot and Hibernate are smart enough to save the child class data!
        return studentRepository.save(newStudent);
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
    @DeleteMapping("/delete")
    public void deleteStudentAccount(@RequestBody java.util.Map<String, String> requestData) {

        // 1. Get the email AND password from the frontend request
        String email = requestData.get("email");
        String password = requestData.get("password");

        // 2. Search for the student
        Student studentToDelete = studentRepository.findByEmail(email);

        // 3. SECURITY CHECK: Do they exist, and does the password match?
        if (studentToDelete != null && studentToDelete.getPassword().equals(password)) {
            // 4. If everything matches perfectly, delete them
            studentRepository.delete(studentToDelete);
        } else {
            // If the password is wrong or email doesn't exist, block it!
            throw new RuntimeException("Security Error: Invalid email or password.");
        }
    }

}