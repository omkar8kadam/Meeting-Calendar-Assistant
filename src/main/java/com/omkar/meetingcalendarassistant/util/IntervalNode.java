package com.omkar.meetingcalendarassistant.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IntervalNode {

    private int startTime;
    private int endTime;
    private int maxTime;
    private Long meetingId;
    private IntervalNode left;
    private IntervalNode right;
}
