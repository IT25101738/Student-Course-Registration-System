package com.university.StudentRegistration.model;

import jakarta.persistence.*;


    @Entity
    @Table(name = "grade_records")
    public class GradeRecord {

        // ─── Encapsulated Fields ────────────────────────────────────────────────

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String studentEmail;
        private String courseCode;
        private String courseTitle;
        private int credits;
        private String gradeValue;

        private String gradeType;


        private String semester;

        public GradeRecord() {}

        public GradeRecord(String studentEmail, String courseCode, String courseTitle,
                           int credits, String gradeValue, String gradeType, String semester) {
            this.studentEmail = studentEmail;
            this.courseCode   = courseCode;
            this.courseTitle  = courseTitle;
            this.credits      = credits;
            this.gradeValue   = gradeValue;
            this.gradeType    = gradeType;
            this.semester     = semester;
        }

        public Grade buildGradeObject() {
            if ("PASSFAIL".equalsIgnoreCase(gradeType)) {
                return new PassFailGrade(gradeValue);
            }
            return new LetterGrade(gradeValue);
        }


        public double getGpaPoints() {
            return buildGradeObject().calculateGPA();
        }



        public Long getId() { return id; }

        public String getStudentEmail() { return studentEmail; }
        public void setStudentEmail(String studentEmail) { this.studentEmail = studentEmail; }

        public String getCourseCode() { return courseCode; }
        public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

        public String getCourseTitle() { return courseTitle; }
        public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }

        public int getCredits() { return credits; }
        public void setCredits(int credits) { this.credits = credits; }

        public String getGradeValue() { return gradeValue; }
        public void setGradeValue(String gradeValue) { this.gradeValue = gradeValue; }

        public String getGradeType() { return gradeType; }
        public void setGradeType(String gradeType) { this.gradeType = gradeType; }

        public String getSemester() { return semester; }
        public void setSemester(String semester) { this.semester = semester; }
    }

