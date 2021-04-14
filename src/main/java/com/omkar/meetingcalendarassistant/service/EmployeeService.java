package com.omkar.meetingcalendarassistant.service;

import com.omkar.meetingcalendarassistant.model.Employee;

public interface EmployeeService {

    public boolean createEmployee(Long id, String name);

    public Employee getEmployee(Long id);
}
