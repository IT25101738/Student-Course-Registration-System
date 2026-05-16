package com.university.StudentRegistration.repository;

import com.university.StudentRegistration.model.CourseRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Long> {

    // Finds all registrations for a specific student
    List<CourseRegistration> findByStudentEmail(String studentEmail);

    // Counts how many students are in a specific course
    long countByCourseCode(String courseCode);

    @Transactional
    void deleteByCourseCode(String courseCode);
}