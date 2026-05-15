package com.university.StudentRegistration.repository;

import com.university.StudentRegistration.model.GradeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<GradeRecord, Long> {

    /** Fetch all grades for one student (used on the transcript page) */
    List<GradeRecord> findByStudentEmail(String studentEmail);

    /** Fetch all grades for one course (used by faculty grade entry) */
    List<GradeRecord> findByCourseCode(String courseCode);

    /** Check if a grade record already exists for a given student + course */
    Optional<GradeRecord> findByStudentEmailAndCourseCode(String studentEmail, String courseCode);

    /** Fetch all grades submitted by semester (useful for admin views) */
    List<GradeRecord> findBySemester(String semester);
}
