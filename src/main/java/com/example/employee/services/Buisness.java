package com.example.employee.services;


import org.springframework.stereotype.Service;

@Service
public class Buisness {
    public double getAnualSalary(double monthly_salary){
        return monthly_salary*12;
    }

}
