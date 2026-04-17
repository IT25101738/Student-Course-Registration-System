package com.university.StudentRegistration.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CORE")
public class CoreCourse extends Course{

    public CoreCourse(){

    }

    CoreCourse(String code, String title, int credits){
        super(code, title, credits);
    }

    @Override
    public String getCategory(){
        return "Core";
    }
}
