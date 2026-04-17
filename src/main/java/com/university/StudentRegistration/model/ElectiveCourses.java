package com.university.StudentRegistration.model;

public class ElectiveCourses extends Course{
    ElectiveCourses(String code,String title,int credits){
        super(code, title, credits);
    }

    @Override
    public String getCategory(){
        return "Elective";
    }
}
