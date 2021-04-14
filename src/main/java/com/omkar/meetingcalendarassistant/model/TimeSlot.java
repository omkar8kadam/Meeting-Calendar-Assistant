package com.omkar.meetingcalendarassistant.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class TimeSlot {

    LocalTime startTime;
    LocalTime endTime;
}
