package com.omkar.meetingcalendarassistant.controller;

import com.omkar.meetingcalendarassistant.exception.EmployeeNotFoundExcpetion;
import com.omkar.meetingcalendarassistant.exchanges.MeetingRequest;
import com.omkar.meetingcalendarassistant.model.Employee;
import com.omkar.meetingcalendarassistant.model.TimeSlot;
import com.omkar.meetingcalendarassistant.service.EmployeeService;
import com.omkar.meetingcalendarassistant.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MeetingController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    MeetingService meetingService;

    @PostMapping("/bookMeeting")
    public ResponseEntity<String> bookMeeting(@RequestBody MeetingRequest meetingRequest) {

        meetingService.bookMeeting(meetingRequest);
        return ResponseEntity.ok("Meeting booked");
    }

    @PostMapping("/conflictingParticipants")
    public ResponseEntity<List<Long>> findConflictingParticipants(@RequestBody MeetingRequest meetingRequest) {
        return ResponseEntity.ok().body(meetingService.findConflictingParticipants(meetingRequest));
    }

    @GetMapping("/freeSlots")
    public ResponseEntity<List<TimeSlot>> getFreeSlots(@RequestParam Long empId1, @RequestParam Long empId2, @RequestParam int durationInMinutes) {
        return ResponseEntity.ok(meetingService.getFreeSlots(empId1, empId2, durationInMinutes));
    }

    @GetMapping("/allMeetingSlotsOfEmployee")
    public ResponseEntity<List<TimeSlot>> getAllMeetingSlotsOfEmployee(@RequestParam Long id) {
        Employee employee = employeeService.getEmployee(id);
        if (employee == null)
            throw new EmployeeNotFoundExcpetion("Employee id: " + id + " not found");
        List<TimeSlot> timeSlotList = employee.getCalendar().getAllMeetings().getTimeSlotList();
        return ResponseEntity.ok(timeSlotList);
    }
}
