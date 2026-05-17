package com.university.StudentRegistration.controller;

import com.university.StudentRegistration.model.AdjunctProfessor;
import com.university.StudentRegistration.model.FullTimeProfessor;
import com.university.StudentRegistration.model.Instructor;
import com.university.StudentRegistration.repository.CourseRepository;
import com.university.StudentRegistration.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController // tells Spring this class handles web requests and returns JSON
@RequestMapping("/api/faculty") // base URL for all faculty endpoints
@CrossOrigin // allows requests from our HTML frontend
public class FacultyController {

    @Autowired // Spring automatically injects the repository (database connection)
    private InstructorRepository instructorRepository;

    @Autowired
    private CourseRepository courseRepository;

    // -----------------------------------------------------------------------
    // CREATE: Register a new faculty member
    // POST /api/faculty/register
    // -----------------------------------------------------------------------
    @PostMapping("/register")
    public ResponseEntity<String> registerFaculty(@RequestBody Map<String, String> requestData) {
        try {
            // Extract the raw text data sent from the HTML form
            String type       = requestData.get("type");       // "FULLTIME" or "ADJUNCT"
            String name       = requestData.get("name");
            String department = requestData.get("department");
            String employeeId = requestData.get("employeeId");
            String email      = requestData.get("email");
            String password   = requestData.get("password");
            String extraInfo  = requestData.get("extraInfo"); // officeHours OR contractEndDate

            // Check if employee ID already exists
            if (instructorRepository.findByEmployeeId(employeeId) != null) {
                return ResponseEntity.status(409).body("Error: Employee ID already exists.");
            }

            // Inheritance + Polymorphism: decide which subclass to instantiate
            Instructor newInstructor;
            if ("FULLTIME".equalsIgnoreCase(type)) {
                newInstructor = new FullTimeProfessor(name, department, employeeId, email, password, extraInfo);
            } else if ("ADJUNCT".equalsIgnoreCase(type)) {
                newInstructor = new AdjunctProfessor(name, department, employeeId, email, password, extraInfo);
            } else {
                return ResponseEntity.badRequest().body("Error: Unknown faculty type. Use FULLTIME or ADJUNCT.");
            }

            instructorRepository.save(newInstructor);
            return ResponseEntity.ok("Faculty member registered successfully!");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    // -----------------------------------------------------------------------
    // READ: Get all faculty members
    // GET /api/faculty/all
    // -----------------------------------------------------------------------
    @GetMapping("/all")
    public List<Instructor> getAllFaculty() {
        return instructorRepository.findAll();
    }

    // -----------------------------------------------------------------------
    // READ: Search faculty by employee ID
    // GET /api/faculty/search?employeeId=FAC-001
    // -----------------------------------------------------------------------
    @GetMapping("/search")
    public ResponseEntity<?> searchByEmployeeId(@RequestParam String employeeId) {
        Instructor found = instructorRepository.findByEmployeeId(employeeId);
        if (found != null) {
            return ResponseEntity.ok(found);
        } else {
            return ResponseEntity.status(404).body("Faculty member not found.");
        }
    }

    // -----------------------------------------------------------------------
    // READ: Filter faculty by department
    // GET /api/faculty/department?dept=Computing
    // -----------------------------------------------------------------------
    @GetMapping("/department")
    public List<Instructor> getByDepartment(@RequestParam String dept) {
        return instructorRepository.findByDepartment(dept);
    }

    // -----------------------------------------------------------------------
    // UPDATE: Assign a course to a faculty member
    // PUT /api/faculty/assign-course
    // Body: { "employeeId": "FAC-001", "courseCode": "CS101" }
    //
    // Demonstrates POLYMORPHISM: getMaxTeachingLoad() is called on the object
    // and returns different values depending on whether it's a FullTimeProfessor
    // (returns 4) or an AdjunctProfessor (returns 2).
    // -----------------------------------------------------------------------
    @PutMapping("/assign-course")
    public ResponseEntity<String> assignCourse(@RequestBody Map<String, String> requestData) {
        String employeeId  = requestData.get("employeeId");
        String courseCode  = requestData.get("courseCode");

        // Find the instructor
        Instructor instructor = instructorRepository.findByEmployeeId(employeeId);
        if (instructor == null) {
            return ResponseEntity.status(404).body("Error: Faculty member not found.");
        }

        // Verify the course exists
        if (!courseRepository.existsById(courseCode)) {
            return ResponseEntity.status(404).body("Error: Course not found.");
        }

        // Count how many courses this instructor is already assigned to
        long currentLoad = instructorRepository.findByAssignedCourseCode(courseCode).size();

        // POLYMORPHISM IN ACTION: calling getMaxTeachingLoad() on the parent reference
        // returns 4 for FullTimeProfessor and 2 for AdjunctProfessor at runtime.
        int maxLoad = instructor.getMaxTeachingLoad();

        // Count courses already assigned to THIS specific instructor
        long myLoad = instructorRepository.findAll().stream()
                .filter(i -> i.getEmployeeId().equals(employeeId) && i.getAssignedCourseCode() != null)
                .count();

        if (myLoad >= maxLoad) {
            return ResponseEntity.status(400).body(
                    "Error: " + instructor.getName() + " has reached their maximum teaching load of "
                            + maxLoad + " course(s). Cannot assign another course.");
        }

        instructor.setAssignedCourseCode(courseCode);
        instructorRepository.save(instructor);
        return ResponseEntity.ok("Course " + courseCode + " assigned to " + instructor.getName() + " successfully!");
    }

    // -----------------------------------------------------------------------
    // UPDATE: Update office hours (FullTimeProfessor only)
    // PUT /api/faculty/update-hours
    // Body: { "employeeId": "FAC-001", "officeHours": "Tue/Thu 9–11" }
    // -----------------------------------------------------------------------
    @PutMapping("/update-hours")
    public ResponseEntity<String> updateOfficeHours(@RequestBody Map<String, String> requestData) {
        String employeeId  = requestData.get("employeeId");
        String officeHours = requestData.get("officeHours");

        Instructor instructor = instructorRepository.findByEmployeeId(employeeId);
        if (instructor == null) {
            return ResponseEntity.status(404).body("Error: Faculty member not found.");
        }

        if (!(instructor instanceof FullTimeProfessor)) {
            return ResponseEntity.status(400).body("Error: Office hours can only be set for Full-Time Professors.");
        }

        ((FullTimeProfessor) instructor).setOfficeHours(officeHours);
        instructorRepository.save(instructor);
        return ResponseEntity.ok("Office hours updated successfully!");
    }

    // -----------------------------------------------------------------------
    // UPDATE: Edit core faculty profile fields
    // PUT /api/faculty/update
    // Body: { "employeeId": "FAC-001", "name": "...", "department": "...",
    //         "email": "...", "extraInfo": "..." }
    // extraInfo maps to officeHours (FullTimeProfessor) or
    // contractEndDate (AdjunctProfessor) via polymorphic instanceof check.
    // -----------------------------------------------------------------------
    @PutMapping("/update")
    public ResponseEntity<String> updateFaculty(@RequestBody Map<String, String> requestData) {
        String employeeId = requestData.get("employeeId");

        Instructor instructor = instructorRepository.findByEmployeeId(employeeId);
        if (instructor == null) {
            return ResponseEntity.status(404).body("Error: Faculty member not found.");
        }

        // Only overwrite fields that were actually provided
        String name       = requestData.get("name");
        String department = requestData.get("department");
        String email      = requestData.get("email");
        String extraInfo  = requestData.get("extraInfo");

        if (name       != null && !name.isBlank())       instructor.setName(name);
        if (department != null && !department.isBlank()) instructor.setDepartment(department);
        if (email      != null && !email.isBlank())      instructor.setEmail(email);

        // Polymorphic dispatch: update the subclass-specific extra field
        if (extraInfo != null && !extraInfo.isBlank()) {
            if (instructor instanceof FullTimeProfessor) {
                ((FullTimeProfessor) instructor).setOfficeHours(extraInfo);
            } else if (instructor instanceof AdjunctProfessor) {
                ((AdjunctProfessor) instructor).setContractEndDate(extraInfo);
            }
        }

        instructorRepository.save(instructor);
        return ResponseEntity.ok("Faculty member updated successfully.");
    }

    // -----------------------------------------------------------------------
    // DELETE: Remove a faculty member (admin-only, no password required)
    // DELETE /api/faculty/delete
    // Body: { "employeeId": "FAC-001" }
    // Admin is already authenticated via session — faculty password is not
    // known to the admin, so we authenticate by employeeId lookup only.
    // -----------------------------------------------------------------------
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFaculty(@RequestBody Map<String, String> requestData) {
        String employeeId = requestData.get("employeeId");

        Instructor instructor = instructorRepository.findByEmployeeId(employeeId);

        if (instructor == null) {
            return ResponseEntity.status(404).body("Error: Faculty member not found.");
        }

        instructorRepository.delete(instructor);
        return ResponseEntity.ok("Faculty member removed successfully.");
    }
}
