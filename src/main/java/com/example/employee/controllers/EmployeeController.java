package com.example.employee.controllers;

import com.example.employee.exception.ApiConnectionException;
import com.example.employee.models.EmployeeResponse;
import com.example.employee.services.EmployeeServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class EmployeeController {


    @Autowired
    EmployeeServiceInterface employeeService;

    @GetMapping("/employees")
    public ResponseEntity<EmployeeResponse> getEmployee(@RequestParam(required = false) Integer id_employee) {
        try{
            //TODO: crear una clase de respuesta diferente a la de request
            //TODO:github
            //TODO:readme
            EmployeeResponse employeeResponse=new EmployeeResponse("Accepted","lista de empleados completa",employeeService.getEmployees(id_employee));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(employeeResponse);
        } catch (ApiConnectionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new EmployeeResponse("denied","Error de conexión al API: " + e.getMessage(),new ArrayList<>()));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new EmployeeResponse("denied","Error de conexión al API: " + e.getMessage(),new ArrayList<>()));
        }

    }
}



