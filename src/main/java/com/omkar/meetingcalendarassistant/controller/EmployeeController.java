package com.omkar.meetingcalendarassistant.controller;

import com.omkar.meetingcalendarassistant.model.Company;
import com.omkar.meetingcalendarassistant.model.Employee;
import com.omkar.meetingcalendarassistant.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/createEmployee")
    public ResponseEntity<String> createEmployee(@RequestParam Long id, @RequestParam String name) {

        if(!employeeService.createEmployee(id, name))
            return ResponseEntity.badRequest().body("Employee id already present!");

        return ResponseEntity.ok("Employee created");
    }

    @GetMapping("/getEmployee")
    public ResponseEntity<String> getEmployee(@RequestParam Long id) {
        Employee employee = employeeService.getEmployee(id);
        System.out.println(employee.toString());
        if (employee == null)
            return ResponseEntity.badRequest().body("Employee not found");
        return ResponseEntity.ok().body(employee.toString());
    }
}
