package com.university.StudentRegistration.repository;

import com.university.StudentRegistration.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty, Integer> {

}
