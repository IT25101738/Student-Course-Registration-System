package com.university.StudentRegistration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.university.StudentRegistration.model.GradeRecord;
import java.util.List;

public interface GradeRecordRepository extends JpaRepository<GradeRecord, Long> {
    List<GradeRecord> findByStudentEmail(String studentEmail);
}
