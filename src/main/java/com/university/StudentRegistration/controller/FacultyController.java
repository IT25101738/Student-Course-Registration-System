package com.university.StudentRegistration.controller;

import com.university.StudentRegistration.model.Faculty;
import com.university.StudentRegistration.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculty")
@CrossOrigin

public class FacultyController {

    @Autowired
    private FacultyRepository facultyRepository;

    @GetMapping("/all")
    public List<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    @PostMapping("/add")
    public Faculty addFaculty(@RequestBody Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteFaculty(@PathVariable int id) {
        facultyRepository.deleteById(id);
        return "Faculty deleted successfully";
    }

    @PutMapping("/update/{id}")
    public Faculty updateFaculty(@PathVariable int id, @RequestBody Faculty updatedFaculty) {

        Faculty faculty = facultyRepository.findById(id).orElse(null);

        if (faculty != null) {
            faculty.setFullName(updatedFaculty.getFullName());
            faculty.setEmail(updatedFaculty.getEmail());
            faculty.setDepartment(updatedFaculty.getDepartment());
            faculty.setSpecialization(updatedFaculty.getSpecialization());
            faculty.setPhoneNumber(updatedFaculty.getPhoneNumber());

            return facultyRepository.save(faculty);
        }

        return null;
    }
}

