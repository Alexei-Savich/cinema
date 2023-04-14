package com.example.validation.exceptions;

public class TicketNotFoundException extends RuntimeException {

    private final static String DEFAULT_MESSAGE = "Ticket not found";

    public TicketNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public TicketNotFoundException(String message) {
        super(message);
    }
}
