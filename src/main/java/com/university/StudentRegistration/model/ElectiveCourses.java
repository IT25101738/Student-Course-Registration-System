package com.university.StudentRegistration.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ELECTIVE")
public class ElectiveCourses extends Course{

    public ElectiveCourses(){

    }

    ElectiveCourses(String code,String title,int credits){
        super(code, title, credits);
    }

    @Override
    public String getCategory(){
        return "Elective";
    }
}
