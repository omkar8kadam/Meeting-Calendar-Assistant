package com.omkar.meetingcalendarassistant.service;

import com.omkar.meetingcalendarassistant.exchanges.MeetingRequest;
import com.omkar.meetingcalendarassistant.model.TimeSlot;

import java.util.List;

public interface MeetingService {

    boolean bookMeeting(MeetingRequest meetingRequest);
    List<Long> findConflictingParticipants(MeetingRequest meetingRequest);
    List<TimeSlot> getFreeSlots(Long empId1, Long empId2, int durationInMinutes);
}
