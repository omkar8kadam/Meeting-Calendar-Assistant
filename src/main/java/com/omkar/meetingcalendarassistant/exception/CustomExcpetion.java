package com.omkar.meetingcalendarassistant.exception;

import lombok.Data;

@Data
public class CustomExcpetion extends RuntimeException {

    private String message;
    private String details;

    public CustomExcpetion(String message, String details) {
        super(message);
        this.message = message;
        this.details = details;
    }
}
