package com.omkar.meetingcalendarassistant.controller;

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
        if(employeeService.getEmployee(meetingRequest.getOwnerId()) == null)
            return ResponseEntity.badRequest().body("Owner id for the meeting is invalid!");

        boolean status = meetingService.bookMeeting(meetingRequest);
        if (!status)
            return ResponseEntity.badRequest().body("Booking failed!");

        return ResponseEntity.ok("Meeting booked");
    }

    @PostMapping("/conflictingParticipants")
    public ResponseEntity<List<Long>> findConflictingParticipants(@RequestBody MeetingRequest meetingRequest) {
        return ResponseEntity.ok().body(meetingService.findConflictingParticipants(meetingRequest));
    }

    @GetMapping("/freeSlots")
    public ResponseEntity<List<TimeSlot>> getFreeSlots(@RequestParam Long empId1, @RequestParam Long empId2, @RequestParam int duration) {
        return ResponseEntity.ok(meetingService.getFreeSlots(empId1, empId2, duration));
    }

    @GetMapping("/printTree")
    public ResponseEntity<String> printTree(@RequestParam Long id) {
        Employee employee = employeeService.getEmployee(id);
        if(employee == null)
            return ResponseEntity.badRequest().body("Employee id invalid!");
        String treeData = employee.getCalendar().getAllMeetings().printTree();
        return ResponseEntity.ok(treeData);
    }
}
