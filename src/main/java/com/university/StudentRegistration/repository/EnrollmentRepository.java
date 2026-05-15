package com.university.StudentRegistration.repository;

import com.university.StudentRegistration.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    // Add this exact line below:
    List<Enrollment> findByStudentId(Long studentId);
}