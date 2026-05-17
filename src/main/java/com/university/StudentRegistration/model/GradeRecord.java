package com.university.StudentRegistration.model;

import jakarta.persistence.*;

@Entity
@Table(name = "grade_records")
public class GradeRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentEmail;
    private String courseCode;
    private String examTopic;
    private double numericMark;

    @ManyToOne
    @JoinColumn(name = "grade_id")
    private Grade grade;

    public GradeRecord() {}

    public GradeRecord(String studentEmail, String courseCode, String examTopic, double numericMark, Grade grade) {
        this.studentEmail = studentEmail;
        this.courseCode = courseCode;
        this.examTopic = examTopic;
        this.numericMark = numericMark;
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getExamTopic() {
        return examTopic;
    }

    public void setExamTopic(String examTopic) {
        this.examTopic = examTopic;
    }

    public double getNumericMark() {
        return numericMark;
    }

    public void setNumericMark(double numericMark) {
        this.numericMark = numericMark;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}