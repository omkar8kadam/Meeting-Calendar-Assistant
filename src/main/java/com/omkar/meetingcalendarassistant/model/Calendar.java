package com.omkar.meetingcalendarassistant.model;

import com.omkar.meetingcalendarassistant.util.IntervalSearchTree;
import lombok.Data;

@Data
public class Calendar {

    private IntervalSearchTree allMeetings;

    public Calendar() {
        this.allMeetings = new IntervalSearchTree();
    }
}
