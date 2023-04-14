package com.example.validation.exceptions;

public class TicketAlreadyValidatedException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "Ticket has been already validated";

    public TicketAlreadyValidatedException() {
        super(DEFAULT_MESSAGE);
    }

    public TicketAlreadyValidatedException(String message) {
        super(message);
    }
}
