package com.omkar.meetingcalendarassistant.dao;

import com.omkar.meetingcalendarassistant.exception.CustomExcpetion;
import com.omkar.meetingcalendarassistant.exception.EmployeeNotFoundExcpetion;
import com.omkar.meetingcalendarassistant.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EmployeeDAO {

    private static final Map<Long, Integer> employeeMap = new HashMap<>();
    private static final List<Employee> employeeList = new ArrayList<>();

    static {
        employeeList.add(new Employee(1L, "Omkar"));
        employeeMap.put(1L, 0);
        employeeList.add(new Employee(2L, "Virat"));
        employeeMap.put(2L, 1);
        employeeList.add(new Employee(3L, "Dhoni"));
        employeeMap.put(3L, 2);
        employeeList.add(new Employee(4L, "Sachin"));
        employeeMap.put(4L, 3);
        employeeList.add(new Employee(5L, "Sehwag"));
        employeeMap.put(5L, 4);
    }

    public Long createEmployee(Long id, String name) {
        if (employeeMap.containsKey(id))
            throw new CustomExcpetion("Invalid Input", "Employee id: " + id + " already present");
        employeeList.add(new Employee(id, name));
        employeeMap.put(id, employeeList.size()-1);
        return id;
    }

    public List<Employee> getAllEmployees() {
        return employeeList;
    }

    public Employee getEmployee(Long id) {
        if (!employeeMap.containsKey(id))
            throw new EmployeeNotFoundExcpetion("Employee id: " + id + " not found");
        return employeeList.get(employeeMap.get(id));
    }
}
