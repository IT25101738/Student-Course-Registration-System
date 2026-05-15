package com.university.StudentRegistration.controller;

import com.university.StudentRegistration.model.GradeRecord;
import com.university.StudentRegistration.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/grades")
@CrossOrigin(origins = "*") // allow the HTML frontend to reach the API
public class GradeController {

    @Autowired
    private GradeRepository gradeRepository;


    @PostMapping("/submit")
    public ResponseEntity<?> submitGrade(@RequestBody GradeRecord gradeRecord) {

        Optional<GradeRecord> existing = gradeRepository
                .findByStudentEmailAndCourseCode(
                        gradeRecord.getStudentEmail(),
                        gradeRecord.getCourseCode());

        if (existing.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body("Grade already exists for this student in this course. Use the update endpoint instead.");
        }

        if (gradeRecord.getGradeType() == null || gradeRecord.getGradeType().isBlank()) {
            gradeRecord.setGradeType("LETTER");
        }

        GradeRecord saved = gradeRepository.save(gradeRecord);
        return ResponseEntity.ok(saved);
    }


    @GetMapping("/student/{email}")
    public ResponseEntity<List<GradeRecord>> getTranscript(@PathVariable String email) {
        List<GradeRecord> records = gradeRepository.findByStudentEmail(email);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/gpa/{email}")
    public ResponseEntity<Map<String, Object>> getGpa(@PathVariable String email) {

        List<GradeRecord> records = gradeRepository.findByStudentEmail(email);

        double totalWeightedPoints = 0.0;
        int    totalCredits        = 0;
        int    totalCourses        = records.size();
        int    passFailCourses     = 0;

        for (GradeRecord record : records) {

            double gpaPoints = record.getGpaPoints();

            if (gpaPoints < 0) {

                passFailCourses++;
            } else {
                totalWeightedPoints += gpaPoints * record.getCredits();
                totalCredits        += record.getCredits();
            }
        }

        double gpa = (totalCredits > 0)
                ? Math.round((totalWeightedPoints / totalCredits) * 100.0) / 100.0
                : 0.0;

        Map<String, Object> result = new HashMap<>();
        result.put("email",          email);
        result.put("gpa",            gpa);
        result.put("totalCredits",   totalCredits);
        result.put("totalCourses",   totalCourses);
        result.put("passFailCourses", passFailCourses);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/course/{courseCode}")
    public ResponseEntity<List<GradeRecord>> getGradesByCourse(@PathVariable String courseCode) {
        List<GradeRecord> records = gradeRepository.findByCourseCode(courseCode);
        return ResponseEntity.ok(records);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateGrade(@PathVariable Long id,
                                         @RequestBody GradeRecord updatedData) {

        Optional<GradeRecord> optional = gradeRepository.findById(id);

        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        GradeRecord existing = optional.get();


        existing.setGradeValue(updatedData.getGradeValue());

        if (updatedData.getGradeType() != null && !updatedData.getGradeType().isBlank()) {
            existing.setGradeType(updatedData.getGradeType());
        }

        if (updatedData.getSemester() != null && !updatedData.getSemester().isBlank()) {
            existing.setSemester(updatedData.getSemester());
        }

        GradeRecord saved = gradeRepository.save(existing);
        return ResponseEntity.ok(saved);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteGrade(@PathVariable Long id) {
        if (!gradeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        gradeRepository.deleteById(id);
        return ResponseEntity.ok("Grade record with ID " + id + " has been successfully removed.");
    }
}