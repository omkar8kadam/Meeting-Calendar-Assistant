package com.omkar.meetingcalendarassistant.service;

import com.omkar.meetingcalendarassistant.exchanges.MeetingRequest;
import com.omkar.meetingcalendarassistant.model.TimeSlot;

import java.util.List;

public interface MeetingService {

    public boolean bookMeeting(MeetingRequest meetingRequest);
    public List<Long> findConflictingParticipants(MeetingRequest meetingRequest);
    public List<TimeSlot> getFreeSlots(Long empId1, Long empId2, int duration);
}
