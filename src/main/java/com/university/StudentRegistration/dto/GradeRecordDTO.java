package com.university.StudentRegistration.dto;

import com.university.StudentRegistration.model.GradeRecord;

public class GradeRecordDTO {
    private Long id;
    private String studentEmail;
    private String courseCode;
    private String examTopic;
    private double numericMark;
    private String gradeType;
    private double gpaPoints;
    private String gradeString;

    public GradeRecordDTO() {}

    public GradeRecordDTO(GradeRecord record) {
        this.id = record.getId();
        this.studentEmail = record.getStudentEmail();
        this.courseCode = record.getCourseCode();
        this.examTopic = record.getExamTopic();
        this.numericMark = record.getNumericMark();

        if (record.getGrade() != null) {
            this.gpaPoints = record.getGrade().calculateGPA(this.numericMark);
            this.gradeString = record.getGrade().getGradeString(this.numericMark);
            this.gradeType = record.getGrade().getClass().getSimpleName().toLowerCase().contains("letter") ? "letter" : "passfail";
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStudentEmail() { return studentEmail; }
    public void setStudentEmail(String studentEmail) { this.studentEmail = studentEmail; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getExamTopic() { return examTopic; }
    public void setExamTopic(String examTopic) { this.examTopic = examTopic; }

    public double getNumericMark() { return numericMark; }
    public void setNumericMark(double numericMark) { this.numericMark = numericMark; }

    public String getGradeType() { return gradeType; }
    public void setGradeType(String gradeType) { this.gradeType = gradeType; }

    public double getGpaPoints() { return gpaPoints; }
    public void setGpaPoints(double gpaPoints) { this.gpaPoints = gpaPoints; }

    public String getGradeString() { return gradeString; }
    public void setGradeString(String gradeString) { this.gradeString = gradeString; }
}
