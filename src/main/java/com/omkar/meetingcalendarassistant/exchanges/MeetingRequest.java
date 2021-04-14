package com.omkar.meetingcalendarassistant.exchanges;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class MeetingRequest {

    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String agenda;
    private Long ownerId;
    private List<Long> participants;
}
