package com.university.StudentRegistration.repository;

import com.university.StudentRegistration.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    // Spring Data JPA automatically implements these based on method names

    // Find one faculty member by their unique employee ID (used for login/lookup/delete)
    Instructor findByEmployeeId(String employeeId);

    // Find all faculty in a specific department (used for directory filtering)
    List<Instructor> findByDepartment(String department);

    // Find all faculty assigned to a specific course (used in course assignment view)
    List<Instructor> findByAssignedCourseCode(String assignedCourseCode);
}
