package com.university.StudentRegistration.repository;

import com.university.StudentRegistration.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // this automatically knows how to do CRUD.

    Student findByEmail(String email);
}
