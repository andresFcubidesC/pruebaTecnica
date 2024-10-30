package com.example.employee.services;


import com.example.employee.models.Employee;
import com.example.employee.models.EmployeeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class EmployeeService implements EmployeeServiceInterface {
    private final RestTemplate restTemplate;

    @Value("${API_URL_ALL_EMPOLYEES}")
    private String api_url_all_employees ;
    @Value("${API_URL_EMPOLYEES_BY_ID}")
    private String api_url_employees_by_id;


    @Autowired
    Buisness buisness;


    @Autowired
    public EmployeeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Employee> getEmployees(Integer id_employee ) throws Exception {
        try{
            //Funcion para manejar el llamado a las APIs de acuerdo a si existe o no el id_Employee
            ResponseEntity<?> response = getResponseApi(id_employee);

            //Funcion para calcular el salario anual de los trabajadores.
            return getEmployeesWithAnualSalary(id_employee, response);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    private ArrayList<Employee> getEmployeesWithAnualSalary(Integer id_employee, ResponseEntity<?> response) {
        ArrayList<Employee> employees = new ArrayList<>();
        if (id_employee == null) {
            EmployeeResponse<List<Employee>> employeeResponseApi;
            employeeResponseApi = (EmployeeResponse<List<Employee>>) response.getBody();
            employees = new ArrayList<>(employeeResponseApi.getData());
        } else {
            // Si se obtuvo un solo empleado
            EmployeeResponse<Employee> singleEmployeeResponse = (EmployeeResponse<Employee>) response.getBody();
            employees.add(singleEmployeeResponse.getData()); // Agrega el empleado a la lista
        }

        employees.forEach(employeeT -> {
            double annualSalary = buisness.getAnualSalary(Double.parseDouble(employeeT.getEmployee_salary()));
            employeeT.setEmployee_anual_salary(String.valueOf(annualSalary));
        });
        return employees;
    }

    private ResponseEntity<?> getResponseApi(Integer id_employee) throws Exception {
        System.out.println("Fetching employee data...");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", "humans_21909=1");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<?> response;

        if (id_employee == null) {
            // Llama a la API para obtener todos los empleados
            response = restTemplate.exchange(api_url_all_employees, HttpMethod.GET, entity,
                    new ParameterizedTypeReference<EmployeeResponse<List<Employee>>>() {});
        } else {
            // Llama a la API para obtener un solo empleado por ID
            String apiUrlEmployeeById = api_url_employees_by_id + id_employee.toString();
            response = restTemplate.exchange(apiUrlEmployeeById, HttpMethod.GET, entity,
                    new ParameterizedTypeReference<EmployeeResponse<Employee>>() {});
        }

        // Verifica si la respuesta es exitosa
        if (response.getStatusCode().isError()) {
            throw new Exception("Error fetching employees: " + response.getStatusCode().toString());
        }
        return response;
    }


}