package com.example.validation.controllers;

import com.example.validation.entities.Ticket;
import com.example.validation.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("tickets/{id}")
    public ResponseEntity<Ticket> getTicket(@PathVariable Long id) {
        Ticket ticket = ticketService.getTicketById(id);
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @PostMapping("tickets")
    public ResponseEntity<Ticket> addTicket(@RequestBody Ticket ticket) {
        Ticket ticketNew = ticketService.createTicket(ticket);
        return new ResponseEntity<>(ticketNew, HttpStatus.CREATED);
    }

    @DeleteMapping("tickets/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("tickets/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @RequestBody Ticket ticketDetails) {
        Ticket ticket = ticketService.updateTicket(id, ticketDetails);
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @PutMapping("tickets/{id}/validate")
    public ResponseEntity<Ticket> validateTicket(@PathVariable Long id) {
        Ticket ticket = ticketService.validateTicket(id);
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @GetMapping("tickets/{id}/validation-status")
    public ResponseEntity<Boolean> checkValidationStatus(@PathVariable Long id) {
        boolean status = ticketService.checkValidationStatus(id);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @GetMapping("tickets/{movieName}/at/{time}")
    public ResponseEntity<List<Ticket>> getTicketsByMovieAndTime(@PathVariable String movieName, @PathVariable String time){
        LocalDateTime dateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        List<Ticket> tickets = ticketService.findTicketsByFilmNameAndTimeDate(movieName, dateTime);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

}
