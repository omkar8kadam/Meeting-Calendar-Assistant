package com.omkar.meetingcalendarassistant.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class Meeting {

    private Long id;
    private LocalTime startTime;
    private LocalTime endTime;
    private String agenda;
}
