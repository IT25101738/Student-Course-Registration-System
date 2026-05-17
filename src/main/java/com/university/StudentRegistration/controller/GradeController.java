package com.university.StudentRegistration.controller;

import com.university.StudentRegistration.dto.GradeRecordDTO;
import com.university.StudentRegistration.dto.GradeRecordRequest;
import com.university.StudentRegistration.dto.LetterGradeScaleRequest;
import com.university.StudentRegistration.model.*;
import com.university.StudentRegistration.repository.GradeRecordRepository;
import com.university.StudentRegistration.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    @Autowired
    private GradeRecordRepository gradeRecordRepository;

    @Autowired
    private GradeRepository gradeRepository;

    private LetterGrade getGlobalLetterGrade() {
        return gradeRepository.findAll().stream()
                .filter(g -> g instanceof LetterGrade)
                .map(g -> (LetterGrade) g)
                .findFirst()
                .orElseGet(() -> gradeRepository.save(new LetterGrade(90, 80, 70, 60)));
    }

    private PassFailGrade getGlobalPassFailGrade() {
        return gradeRepository.findAll().stream()
                .filter(g -> g instanceof PassFailGrade)
                .map(g -> (PassFailGrade) g)
                .findFirst()
                .orElseGet(() -> gradeRepository.save(new PassFailGrade(50)));
    }

    @PostMapping("/release")
    public ResponseEntity<String> releaseGrade(@RequestBody GradeRecordRequest request) {
        Grade grade;
        if ("passfail".equalsIgnoreCase(request.getGradeType())) {
            grade = getGlobalPassFailGrade();
        } else {
            grade = getGlobalLetterGrade();
        }

        GradeRecord record = new GradeRecord(request.getStudentEmail(), request.getCourseCode(), request.getExamTopic(), request.getNumericMark(), grade);
        gradeRecordRepository.save(record);
        return ResponseEntity.ok("Grade released successfully.");
    }

    @GetMapping("/transcript/{email}")
    public ResponseEntity<List<GradeRecordDTO>> getTranscript(@PathVariable String email) {
        List<GradeRecord> records = gradeRecordRepository.findByStudentEmail(email);
        List<GradeRecordDTO> dtos = records.stream().map(GradeRecordDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<String> modifyGrade(@PathVariable Long id, @RequestBody GradeRecordRequest request) {
        return gradeRecordRepository.findById(id).map(record -> {
            record.setNumericMark(request.getNumericMark());
            if (request.getGradeType() != null) {
                if ("passfail".equalsIgnoreCase(request.getGradeType())) {
                    record.setGrade(getGlobalPassFailGrade());
                } else {
                    record.setGrade(getGlobalLetterGrade());
                }
            }
            gradeRecordRepository.save(record);
            return ResponseEntity.ok("Grade updated successfully.");
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGrade(@PathVariable Long id) {
        gradeRecordRepository.deleteById(id);
        return ResponseEntity.ok("Grade deleted successfully.");
    }

    @PutMapping("/scale/letter")
    public ResponseEntity<String> updateLetterGradeScale(@RequestBody LetterGradeScaleRequest request) {
        LetterGrade lg = getGlobalLetterGrade();
        lg.setABoundary(request.getABoundary());
        lg.setBBoundary(request.getBBoundary());
        lg.setCBoundary(request.getCBoundary());
        lg.setDBoundary(request.getDBoundary());
        gradeRepository.save(lg);
        return ResponseEntity.ok("Letter grade scale updated successfully.");
    }

    @GetMapping("/scale/letter")
    public ResponseEntity<LetterGrade> getLetterGradeScale() {
        return ResponseEntity.ok(getGlobalLetterGrade());
    }
}
