package com.omkar.meetingcalendarassistant.service.impl;

import com.omkar.meetingcalendarassistant.dao.EmployeeDAO;
import com.omkar.meetingcalendarassistant.model.Employee;
import com.omkar.meetingcalendarassistant.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeDAO employeeDAO;

    @Override
    public Long createEmployee(Long id, String name) {
       return employeeDAO.createEmployee(id, name);
    }

    @Override
    public Employee getEmployee(Long id) {
        return employeeDAO.getEmployee(id);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }
}
