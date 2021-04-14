package com.omkar.meetingcalendarassistant;

import com.omkar.meetingcalendarassistant.model.Company;
import com.omkar.meetingcalendarassistant.service.EmployeeService;
import com.omkar.meetingcalendarassistant.service.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MeetingCalendarAssistantApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetingCalendarAssistantApplication.class, args);

		EmployeeService employeeService = new EmployeeServiceImpl();
		employeeService.createEmployee(1L, "Omkar");
		employeeService.createEmployee(2L, "Omkar");
		employeeService.createEmployee(3L, "Omkar");
		employeeService.createEmployee(4L, "Omkar");
		employeeService.createEmployee(5L, "Omkar");
	}

}
