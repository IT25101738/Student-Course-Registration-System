package com.university.StudentRegistration.model;

public class Course {

    private String courseCode;
    private String title;
    private int credits;

    Course(String courseCode, String title, int credits){
        this.courseCode=courseCode;
        this.title=title;
        this.credits=credits;
    }

    public String getCategory(){
        return "General Course";
    }

    public String getCourseCode(){
        return courseCode;
    }

    public String getTitle(){
        return title;
    }
}
