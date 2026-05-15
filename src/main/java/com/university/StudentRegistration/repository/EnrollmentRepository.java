package com.university.StudentRegistration.repository;

import com.university.StudentRegistration.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM enrollments WHERE course_id = (SELECT id FROM course WHERE course_code = :courseCode)", nativeQuery = true)
    void deleteFromEnrollments(@Param("courseCode") String courseCode);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM course_registrations WHERE course_code = :courseCode", nativeQuery = true)
    void deleteFromRegistrations(@Param("courseCode") String courseCode);

    // Add this exact line below:
    List<Enrollment> findByStudentId(Long studentId);
}