package com.omkar.meetingcalendarassistant.service;

import com.omkar.meetingcalendarassistant.model.Employee;

import java.util.List;

public interface EmployeeService {

    Long createEmployee(Long id, String name);
    Employee getEmployee(Long id);
    List<Employee> getAllEmployees();
}
