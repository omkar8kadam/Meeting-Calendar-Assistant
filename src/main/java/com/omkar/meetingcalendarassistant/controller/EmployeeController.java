package com.omkar.meetingcalendarassistant.controller;

import com.omkar.meetingcalendarassistant.model.Employee;
import com.omkar.meetingcalendarassistant.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/createEmployee")
    public ResponseEntity<String> createEmployee(@RequestParam Long id, @RequestParam String name) {
        Long empId = employeeService.createEmployee(id, name);
        return ResponseEntity.ok("Employee created with id: " + empId);
    }

    @GetMapping("/getEmployee")
    public ResponseEntity<Employee> getEmployee(@RequestParam Long id) {
        Employee employee = employeeService.getEmployee(id);
        return ResponseEntity.ok().body(employee);
    }

    @GetMapping("/getAllEmployees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }
}
