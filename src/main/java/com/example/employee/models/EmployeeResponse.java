package com.example.employee.models;


import lombok.Data;


import java.util.List;

@Data
public class EmployeeResponse<T> {
    private String status;
    private T data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

    public EmployeeResponse(String status, String message,T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
