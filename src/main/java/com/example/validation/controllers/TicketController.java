package com.example.validation.controllers;

import com.example.validation.entities.Ticket;
import com.example.validation.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }


    // Add a new ticket
    @PostMapping("")
    public Ticket addTicket(@RequestBody Ticket ticket) {
        return ticketService.addTicket(ticket);
    }

    // Delete a ticket by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.ok().build();
    }

    // Update a ticket by ID
    @PutMapping("/{id}")
    public Ticket updateTicket(@PathVariable Long id, @RequestBody Ticket ticketDetails) {
        return ticketService.updateTicket(id, ticketDetails);
    }

    // Validate a ticket by ID
    @PutMapping("/{id}/validate")
    public Ticket validateTicket(@PathVariable Long id) {
        return ticketService.validateTicket(id);
    }

    // Check the validation status of a ticket by ID
    @GetMapping("/{id}/validation-status")
    public boolean checkValidationStatus(@PathVariable Long id) {
        return ticketService.checkValidationStatus(id);
    }

}
