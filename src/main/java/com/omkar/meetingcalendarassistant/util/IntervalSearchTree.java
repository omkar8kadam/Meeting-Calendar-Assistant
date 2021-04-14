package com.omkar.meetingcalendarassistant.util;

import com.omkar.meetingcalendarassistant.exception.CustomExcpetion;
import com.omkar.meetingcalendarassistant.model.TimeSlot;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class IntervalSearchTree {

    private IntervalNode root;

    public IntervalSearchTree() {
        root = null;
    }

    public void add(int startTime, int endTime, Long meetingId) {
        if (startTime >= endTime)
            throw new CustomExcpetion("Invalid Input", "start time: " + convertIntoTime(startTime) + " cannot be greater than or equal to end time: " + convertIntoTime(endTime));

        IntervalNode currNode = root;
        while (currNode != null) {
            currNode.setMaxTime(Math.max(currNode.getMaxTime(), endTime));
            if (startTime < currNode.getStartTime()) {
                if (currNode.getLeft() == null) {
                    currNode.setLeft(new IntervalNode(startTime, endTime, endTime, meetingId, null, null));
                    return;
                }
                currNode = currNode.getLeft();
            }
            else {
                if (currNode.getRight() == null) {
                    currNode.setRight(new IntervalNode(startTime, endTime, endTime, meetingId, null, null));
                    return;
                }
                currNode = currNode.getRight();
            }
        }
        root = new IntervalNode(startTime, endTime, endTime, meetingId, null, null);
    }

    public boolean conflict(int startTime, int endTime) {
        if (startTime >= endTime)
            throw new CustomExcpetion("Invalid Input", "start time: " + convertIntoTime(startTime) + " cannot be greater than or equal to end time: " + convertIntoTime(endTime));

        IntervalNode currNode = root;
        while (currNode != null) {
            if (startTime < currNode.getEndTime() && endTime > currNode.getStartTime())
                return true;
            if (currNode.getLeft() != null && currNode.getLeft().getMaxTime() > startTime)
                currNode = currNode.getLeft();
            else
                currNode = currNode.getRight();
        }
        return false;
    }

    static List<TimeSlot> timeSlots;
    public List<TimeSlot> getTimeSlotList() {
        timeSlots = new ArrayList<>();
        getTimeSlotListHelper(root);
        return timeSlots;
    }

    private void getTimeSlotListHelper(IntervalNode root) {
        if (root == null)
            return;
        getTimeSlotListHelper(root.getLeft());
        LocalTime startTime = convertIntoTime(root.getStartTime());
        LocalTime endTime = convertIntoTime(root.getEndTime());
        timeSlots.add(new TimeSlot(startTime, endTime));
        getTimeSlotListHelper(root.getRight());
    }

    private LocalTime convertIntoTime(int minutes) {
        int hours = minutes / 60;
        int mins = minutes - (hours * 60);
        return LocalTime.of(hours, mins);
    }
}
