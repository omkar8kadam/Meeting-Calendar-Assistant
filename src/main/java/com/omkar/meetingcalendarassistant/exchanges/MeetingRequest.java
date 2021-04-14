package com.omkar.meetingcalendarassistant.exchanges;

import lombok.Data;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class MeetingRequest {

    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String agenda;
    private Long ownerId;
    private List<Long> participants;
}
