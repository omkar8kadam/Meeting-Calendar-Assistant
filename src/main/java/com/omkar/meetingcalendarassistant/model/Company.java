package com.omkar.meetingcalendarassistant.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Company {

    private Map<Long, Integer> employeeMap;
    private List<Employee> employeeList;

    private static Company company;

    private Company() {
        this.employeeMap = new HashMap<>();
        this.employeeList = new ArrayList<>();
    }

    synchronized public static Company getInstance() {
        if(company == null)
            company = new Company();
        return company;
    }
}
