package com.omkar.meetingcalendarassistant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundExcpetion.class)
    public final ResponseEntity<Object> handleEmployeeNotFoundException(EmployeeNotFoundExcpetion ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Employee record not found", details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomExcpetion.class)
    public final ResponseEntity<Object> handleCustomException(CustomExcpetion ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getDetails());
        ErrorResponse error = new ErrorResponse(ex.getMessage(), details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
