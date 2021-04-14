package com.omkar.meetingcalendarassistant.util;

import com.omkar.meetingcalendarassistant.model.Meeting;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Time;

@Data
@AllArgsConstructor
public class IntervalNode {

    private int startTime;
    private int endTime;
    private int maxTime;
    private IntervalNode left;
    private IntervalNode right;
}
