package com.omkar.meetingcalendarassistant.service.impl;

import com.omkar.meetingcalendarassistant.exception.CustomExcpetion;
import com.omkar.meetingcalendarassistant.exception.EmployeeNotFoundExcpetion;
import com.omkar.meetingcalendarassistant.exchanges.MeetingRequest;
import com.omkar.meetingcalendarassistant.model.Employee;
import com.omkar.meetingcalendarassistant.model.Meeting;
import com.omkar.meetingcalendarassistant.model.TimeSlot;
import com.omkar.meetingcalendarassistant.service.EmployeeService;
import com.omkar.meetingcalendarassistant.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class MeetingServiceImpl implements MeetingService {

    @Autowired
    EmployeeService employeeService;

    @Override
    public void bookMeeting(MeetingRequest meetingRequest) {

        Employee meetingOwner = employeeService.getEmployee(meetingRequest.getOwnerId());
        if (meetingOwner == null)
            throw new EmployeeNotFoundExcpetion("Meeting owner id: " + meetingRequest.getOwnerId() + " not found");
        Meeting meeting = new Meeting(meetingRequest.getId(),
                meetingRequest.getStartTime(),
                meetingRequest.getEndTime(),
                meetingRequest.getAgenda());
        bookMeetingForEmployee(meetingOwner, meeting);

        for (Long id : meetingRequest.getParticipants()) {
            Employee participant = employeeService.getEmployee(id);
            if (participant == null)
                throw new EmployeeNotFoundExcpetion("Participant id: " + id + " not found");
            bookMeetingForEmployee(participant, meeting);
        }
    }

    private void bookMeetingForEmployee(Employee employee, Meeting meeting) {
        int startTime = convertIntoMinutes(meeting.getStartTime());
        int endTime = convertIntoMinutes(meeting.getEndTime());

        if (employee.getCalendar().getAllMeetings().conflict(startTime, endTime))
            throw new CustomExcpetion("Cannot book meeting", "There is a conflict with other meeting");

        employee.getCalendar().getAllMeetings().add(startTime, endTime, meeting.getId());
    }

    private int convertIntoMinutes(LocalTime time) {
        return (time.getHour() * 60 + time.getMinute());
    }

    @Override
    public List<Long> findConflictingParticipants(MeetingRequest meetingRequest) {
        List<Long> conflictingParticipantList = new ArrayList<>();
        for (Long id : meetingRequest.getParticipants()) {
            Employee participant = employeeService.getEmployee(id);
            if (participant == null)
                throw new EmployeeNotFoundExcpetion("Participant id: " + id + " not found");
            int startTime = convertIntoMinutes(meetingRequest.getStartTime());
            int endTime = convertIntoMinutes(meetingRequest.getEndTime());
            if (participant.getCalendar().getAllMeetings().conflict(startTime, endTime))
                conflictingParticipantList.add(participant.getId());
        }
        return conflictingParticipantList;
    }

    @Override
    public List<TimeSlot> getFreeSlots(Long empId1, Long empId2, int durationInMinutes) {
        List<TimeSlot> timeSlots;

        Employee emp1 = employeeService.getEmployee(empId1);
        Employee emp2 = employeeService.getEmployee(empId2);
        if (emp1 == null)
            throw new EmployeeNotFoundExcpetion("Employee id: " + empId1 + " not found");
        if (emp2 == null)
            throw new EmployeeNotFoundExcpetion("Employee id: " + empId2 + " not found");

        List<TimeSlot> commonTimeSlots = getCommonTimeSlots(emp1, emp2);

        timeSlots = findSlotsForDuration(commonTimeSlots, durationInMinutes);

        return timeSlots;
    }

    private List<TimeSlot> findSlotsForDuration(List<TimeSlot> commonTimeSlots, int durationInMinutes) {
        List<TimeSlot> timeSlots = new ArrayList<>();

        if (commonTimeSlots == null || commonTimeSlots.isEmpty()) {
            timeSlots.add(new TimeSlot(LocalTime.of(0, 0), LocalTime.of(23, 59)));
            return timeSlots;
        }

        int i=0;
        TimeSlot currSlot = commonTimeSlots.get(i);

        LocalTime startTime;
        LocalTime endTime;
        LocalTime lastEndTime = LocalTime.of(23,59);

        if (currSlot.getStartTime().equals(LocalTime.of(0,0))) {
            startTime = currSlot.getEndTime();
            i++;
        }
        else
            startTime = LocalTime.of(0, 0);

        while (i < commonTimeSlots.size()) {
            currSlot = commonTimeSlots.get(i);
            endTime = currSlot.getStartTime();
            if (startTime.until(endTime, ChronoUnit.MINUTES) >= durationInMinutes)
                timeSlots.add(new TimeSlot(startTime, endTime));
            lastEndTime = currSlot.getStartTime();
            startTime = currSlot.getEndTime();
            i++;
        }
        if (!commonTimeSlots.get(i-1).getEndTime().equals(LocalTime.of(23,59)))
            lastEndTime = LocalTime.of(23,59);
        if (startTime.until(lastEndTime, ChronoUnit.MINUTES) >= durationInMinutes)
            timeSlots.add(new TimeSlot(startTime, lastEndTime));
        return timeSlots;
    }

    private List<TimeSlot> getCommonTimeSlots(Employee emp1, Employee emp2) {
        List<TimeSlot> commonTimeSlots;

        List<TimeSlot> timeSlots1 = emp1.getCalendar().getAllMeetings().getTimeSlotList();
        List<TimeSlot> timeSlots2 = emp2.getCalendar().getAllMeetings().getTimeSlotList();

        commonTimeSlots = mergeCommonTimeSlots(timeSlots1, timeSlots2);

        return commonTimeSlots;
    }

    private List<TimeSlot> mergeCommonTimeSlots(List<TimeSlot> timeSlots1, List<TimeSlot> timeSlots2) {
        List<TimeSlot> timeSlots = new ArrayList<>();

        int i=0, j=0;
        while (i < timeSlots1.size() && j < timeSlots2.size()) {
            TimeSlot ts1 = timeSlots1.get(i);
            TimeSlot ts2 = timeSlots2.get(j);
            //if they intersect
            if (intersect(ts1, ts2)) {
                LocalTime startTime = minStartTime(ts1, ts2);
                LocalTime endTime = maxEndTime(ts1, ts2);
                TimeSlot currSlot = new TimeSlot(startTime, endTime);

                int tmp_i = i++;
                int tmp_j = j++;

                while (tmp_i < timeSlots1.size()) {
                    TimeSlot tmpTS1 = timeSlots1.get(tmp_i);
                    if (intersect(tmpTS1, currSlot)) {
                        endTime = maxEndTime(tmpTS1, currSlot);
                        currSlot = new TimeSlot(startTime, endTime);
                        i = tmp_i;
                        tmp_i++;
                    }
                    else
                        break;
                }

                while (tmp_j < timeSlots2.size()) {
                    TimeSlot tmpTS2 = timeSlots2.get(tmp_j);
                    if (intersect(tmpTS2, currSlot)) {
                        endTime = maxEndTime(tmpTS2, currSlot);
                        currSlot = new TimeSlot(startTime, endTime);
                        j = tmp_j;
                        tmp_j++;
                    }
                    else
                        break;
                }

                timeSlots.add(currSlot);
                i++;
                j++;
            }
            else {
                if (ts1.getStartTime().isBefore(ts2.getStartTime())) {
                    timeSlots.add(ts1);
                    i++;
                }
                else {
                    timeSlots.add(ts2);
                    j++;
                }
            }
        }

        while (i < timeSlots1.size()) {
            TimeSlot ts1 = timeSlots1.get(i);
            timeSlots.add(ts1);
            i++;
        }

        while (j < timeSlots2.size()) {
            TimeSlot ts2 = timeSlots2.get(j);
            timeSlots.add(ts2);
            j++;
        }

        return timeSlots;
    }

    private boolean intersect(TimeSlot ts1, TimeSlot ts2) {
        return ts1.getStartTime().isBefore(ts2.getEndTime()) && ts1.getEndTime().isAfter(ts2.getStartTime());
    }

    private LocalTime minStartTime(TimeSlot ts1, TimeSlot ts2) {
        return ts1.getStartTime().isBefore(ts2.getStartTime()) ? ts1.getStartTime() : ts2.getStartTime();
    }

    private LocalTime maxEndTime(TimeSlot ts1, TimeSlot ts2) {
        return ts1.getEndTime().isAfter(ts2.getEndTime()) ? ts1.getEndTime() : ts2.getEndTime();
    }
}
