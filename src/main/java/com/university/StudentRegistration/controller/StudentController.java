package com.university.StudentRegistration.controller;

import com.university.StudentRegistration.model.Student;
import com.university.StudentRegistration.model.UndergraduateStudent;
import com.university.StudentRegistration.model.GraduateStudent;
import com.university.StudentRegistration.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController // tells spring this class handles web requests
@RequestMapping("/api/students") // base URL (so all endpoints starts with /api/students)
public class StudentController {

    @Autowired // spring automatically injects the repository database connection here
    private StudentRepository studentRepository;

    // CREATE: Register a new student
    @PostMapping("/register") // post use to assign values to db
    public Student createStudent(@RequestBody java.util.Map<String, String> requestData) {

        // extract the raw text data sent from our HTML form
        String type = requestData.get("type");
        String name = requestData.get("name");
        String email = requestData.get("email");
        String major = requestData.get("major");
        String password = requestData.get("password");
        String extraInfo = requestData.get("extraInfo");

        //new info
        String birthday = requestData.get("birthday");
        String contactNumber = requestData.get("contactNumber");
        String address = requestData.get("address");

        //declare a parent variable
        Student newStudent;

        if ("GRAD".equals(type)) {
            newStudent = new GraduateStudent(name, email, major, password, extraInfo);
        } else {
            newStudent = new UndergraduateStudent(name, email, major, password, extraInfo);
        }
        // adding new fields using
        newStudent.setBirthday(birthday);
        newStudent.setContactNumber(contactNumber);
        newStudent.setAddress(address);

        //save it to the database.
        return studentRepository.save(newStudent);
    }

    // READ: The Login Method
    @PostMapping("/login")
    public Student loginStudent(@RequestBody Student loginRequest) {

        // first ask the repository to search the database for this specific email
        Student existingStudent = studentRepository.findByEmail(loginRequest.getEmail());

        // then check if the student actually exists AND if the passwords match exactly
        if (existingStudent != null && existingStudent.getPassword().equals(loginRequest.getPassword())) {
            return existingStudent;
        } else {
            // EXCEPTION HANDLING
            throw new RuntimeException("Invalid email or password");
        }
    }

    // UPDATE: Change a student's profile details
    @PutMapping("/update")
    public Student updateStudentProfile(@RequestBody Student updatedData) {

        //Search the database for the student using the email they typed
        Student existingStudent = studentRepository.findByEmail(updatedData.getEmail());

        if (existingStudent != null) {
            existingStudent.setName(updatedData.getName());
            existingStudent.setMajor(updatedData.getMajor());

            // --- NEW: Save Contact and Address if they are provided ---
            if (updatedData.getContactNumber() != null && !updatedData.getContactNumber().isEmpty()) {
                existingStudent.setContactNumber(updatedData.getContactNumber());
            }
            if (updatedData.getAddress() != null && !updatedData.getAddress().isEmpty()) {
                existingStudent.setAddress(updatedData.getAddress());
            }

            //Save the modified object back into MySQL
            return studentRepository.save(existingStudent);
        } else {
            throw new RuntimeException("Student not found!");
        }
    }

    // DELETE: Remove a student account
    @DeleteMapping("/delete")
    public void deleteStudentAccount(@RequestBody java.util.Map<String, String> requestData) {

        // get the email AND password from the frontend Danger Zone
        String email = requestData.get("email");
        String password = requestData.get("password");

        //Search for the student in the database
        Student studentToDelete = studentRepository.findByEmail(email);

        if (studentToDelete != null && studentToDelete.getPassword().equals(password)) {
            studentRepository.delete(studentToDelete);
        } else {
            // Exception Handling
            throw new RuntimeException("Security Error: Invalid email or password.");
        }
    }

    // Fetch the complete student profile for the Profile Card
    @GetMapping("/profile/{email}")
    public ResponseEntity<Student> getStudentProfile(@PathVariable String email) {

        // Ask the database to find the student by their email
        Student student = studentRepository.findByEmail(email);

        if (student != null) {
            return ResponseEntity.ok(student); // Sends the data with a 200 OK status
        } else {
            return ResponseEntity.notFound().build(); // Sends a 404 Not Found status
        }
    }
}