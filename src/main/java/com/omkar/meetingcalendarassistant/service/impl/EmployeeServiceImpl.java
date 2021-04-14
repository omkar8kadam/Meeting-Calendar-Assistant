package com.omkar.meetingcalendarassistant.service.impl;

import com.omkar.meetingcalendarassistant.model.Company;
import com.omkar.meetingcalendarassistant.model.Employee;
import com.omkar.meetingcalendarassistant.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public boolean createEmployee(Long id, String name) {

        Employee employee = new Employee(id, name);

        Company company = Company.getInstance();
        Map<Long, Integer> employeeMap = company.getEmployeeMap();
        if (employeeMap.containsKey(id))
            return false;
        List<Employee> employeeList = company.getEmployeeList();
        employeeList.add(employee);
        employeeMap.put(id, employeeList.size()-1);
        return true;
    }

    @Override
    public Employee getEmployee(Long id) {
        Company company = Company.getInstance();
        Map<Long, Integer> employeeMap = company.getEmployeeMap();
        List<Employee> employeeList = company.getEmployeeList();
        if (employeeMap.containsKey(id))
            return employeeList.get(employeeMap.get(id));
        return null;
    }
}
