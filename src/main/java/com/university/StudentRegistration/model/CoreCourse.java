package com.university.StudentRegistration.model;

public class CoreCourse extends Course{
    CoreCourse(String code, String title, int credits){
        super(code, title, credits);
    }

    @Override
    public String getCategory(){
        return "Core";
    }
}
