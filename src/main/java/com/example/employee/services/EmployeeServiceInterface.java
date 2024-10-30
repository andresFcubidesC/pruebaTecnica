package com.example.employee.services;



import com.example.employee.models.Employee;

import java.util.List;

public interface EmployeeServiceInterface {
    public List<Employee> getEmployees(Integer id_employee ) throws Exception;
}
