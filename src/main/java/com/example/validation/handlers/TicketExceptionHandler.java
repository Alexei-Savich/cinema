package com.example.validation.handlers;

import com.example.validation.dto.ApiError;
import com.example.validation.exceptions.TicketAlreadyValidatedException;
import com.example.validation.exceptions.TicketNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class TicketExceptionHandler {

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<ApiError> handleTicketNotFoundException(TicketNotFoundException ex) {
        String message = ex.getMessage();
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, message, LocalDateTime.now());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TicketAlreadyValidatedException.class)
    public ResponseEntity<ApiError> handleTicketAlreadyValidatedException(TicketAlreadyValidatedException ex) {
        String message = ex.getMessage();
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, message, LocalDateTime.now());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
