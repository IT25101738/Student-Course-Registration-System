package com.university.StudentRegistration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.university.StudentRegistration.model.Grade;

public interface GradeRepository extends JpaRepository<Grade, Long> {
}
