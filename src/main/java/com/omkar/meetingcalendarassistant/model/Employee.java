package com.omkar.meetingcalendarassistant.model;

import lombok.Data;

@Data
public class Employee {

    private Long id;
    private String name;
    private Calendar calendar;

    public Employee(Long id, String name) {
        this.id = id;
        this.name = name;
        this.calendar = new Calendar();
    }
}
