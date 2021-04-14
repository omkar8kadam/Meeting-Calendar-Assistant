package com.omkar.meetingcalendarassistant.util;

import com.omkar.meetingcalendarassistant.model.Meeting;
import com.omkar.meetingcalendarassistant.model.TimeSlot;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class IntervalSearchTree {

    private IntervalNode root;

    public IntervalSearchTree() {
        root = null;
    }

    public void add(int startTime, int endTime) {

        if (startTime >= endTime)
            throw new IllegalArgumentException("End time should be grater than Start time");

        IntervalNode currNode = root;
        while (currNode != null) {
            currNode.setMaxTime(Math.max(currNode.getMaxTime(), endTime));
            if (startTime < currNode.getStartTime()) {
                if (currNode.getLeft() == null) {
                    currNode.setLeft(new IntervalNode(startTime, endTime, endTime, null, null));
                    return;
                }
                currNode = currNode.getLeft();
            }
            else {
                if (currNode.getRight() == null) {
                    currNode.setRight(new IntervalNode(startTime, endTime, endTime, null, null));
                    return;
                }
                currNode = currNode.getRight();
            }
        }
        root = new IntervalNode(startTime, endTime, endTime, null, null);
    }

    public boolean conflict(int startTime, int endTime) {
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


    static String treeData;
    public String printTree() {
        treeData = "";
        printTreeHelper(root);
        return treeData;
    }

    private String printTreeHelper(IntervalNode currNode) {
        if (currNode == null)
            return treeData;
        printTreeHelper(currNode.getLeft());
        treeData += convertIntoTime(currNode.getStartTime()).toString();
        treeData += " to ";
        treeData += (convertIntoTime(currNode.getEndTime()).toString() + "\n");
//        System.out.println(treeData);
        printTreeHelper(currNode.getRight());
        return treeData;
    }

    public LocalTime convertIntoTime(int minutes) {
        int hours = minutes / 60;
        int mins = minutes - (hours * 60);
        return LocalTime.of(hours, mins);
    }
}
