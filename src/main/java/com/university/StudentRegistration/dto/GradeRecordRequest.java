package com.university.StudentRegistration.dto;

public class GradeRecordRequest {
    private String studentEmail;
    private String courseCode;
    private String examTopic;
    private double numericMark;
    private String gradeType;

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
}

