package com.omkar.meetingcalendarassistant;

import com.omkar.meetingcalendarassistant.exchanges.MeetingRequest;
import com.omkar.meetingcalendarassistant.model.Employee;
import com.omkar.meetingcalendarassistant.model.TimeSlot;
import com.omkar.meetingcalendarassistant.service.EmployeeService;
import com.omkar.meetingcalendarassistant.service.MeetingService;
import com.omkar.meetingcalendarassistant.service.impl.MeetingServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MeetingServiceTest {

    @Mock
    EmployeeService employeeService;

    @InjectMocks
    static MeetingService meetingService = new MeetingServiceImpl();

    Employee emp1, emp2, emp3, emp4, emp5;
    MeetingRequest mr1, mr2, mr3, mr4;

    @BeforeEach
    public void setup() {
        emp1 = new Employee(1L, "Omkar");
        emp2 = new Employee(2L, "Virat");
        emp3 = new Employee(3L, "Sachin");
        emp4 = new Employee(4L, "Sehwag");
        emp5 = new Employee(5L, "Dhoni");

        createMeetings();

        Mockito.when(employeeService.getEmployee(1L)).thenReturn(emp1);
        Mockito.when(employeeService.getEmployee(2L)).thenReturn(emp2);
        Mockito.when(employeeService.getEmployee(3L)).thenReturn(emp3);
        Mockito.when(employeeService.getEmployee(4L)).thenReturn(emp4);
        Mockito.when(employeeService.getEmployee(5L)).thenReturn(emp5);
    }

    @Test
    public void bookMeetingTest() {
        boolean val = meetingService.bookMeeting(mr1);
        Assertions.assertTrue(val);
    }

    @Test
    public void getFreeSlotsTest() {
        meetingService.bookMeeting(mr1);
        meetingService.bookMeeting(mr2);
        meetingService.bookMeeting(mr3);
        meetingService.bookMeeting(mr4);

        List<TimeSlot> slots = meetingService.getFreeSlots(1L, 2L, 30);
        Assertions.assertEquals(4, slots.size());

        TimeSlot ts1 = new TimeSlot(LocalTime.of(0, 0), LocalTime.of(10,0));
        Assertions.assertEquals(ts1, slots.get(0));
    }

    @Test
    public void findConflictingParticipantsTest() {
        meetingService.bookMeeting(mr1);
        meetingService.bookMeeting(mr2);
        meetingService.bookMeeting(mr3);
        meetingService.bookMeeting(mr4);

        List<Long> participantList = new ArrayList<>();
        participantList.add(1L);
        participantList.add(2L);
        participantList.add(3L);
        participantList.add(4L);
        participantList.add(5L);

        MeetingRequest meetingRequest = MeetingRequest.builder()
                .startTime(LocalTime.of(15,0))
                .endTime(LocalTime.of(16,0))
                .participants(participantList)
                .build();

        List<Long> actualList = meetingService.findConflictingParticipants(meetingRequest);
        Assertions.assertEquals(2, actualList.size());
        Assertions.assertEquals(2, actualList.get(0));
        Assertions.assertEquals(3, actualList.get(1));
    }

    public void createMeetings() {
        List<Long> participantList = new ArrayList<>();
        participantList.add(2L);
        participantList.add(3L);

        mr1 = MeetingRequest.builder()
                .id(1L)
                .date(LocalDate.now())
                .startTime(LocalTime.of(10,0))
                .endTime(LocalTime.of(11,0))
                .ownerId(1L)
                .participants(participantList)
                .build();

        mr2 = MeetingRequest.builder()
                .id(2L)
                .startTime(LocalTime.of(13,0))
                .endTime(LocalTime.of(14,0))
                .ownerId(1L)
                .build();

        mr3 = MeetingRequest.builder()
                .id(3L)
                .startTime(LocalTime.of(14,30))
                .endTime(LocalTime.of(15,30))
                .ownerId(2L)
                .build();

        mr4 = MeetingRequest.builder()
                .id(4L)
                .startTime(LocalTime.of(15,0))
                .endTime(LocalTime.of(17,0))
                .ownerId(3L)
                .build();
    }
}
