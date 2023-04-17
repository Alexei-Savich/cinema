package com.example.validation.services;

import com.example.validation.entities.Ticket;
import com.example.validation.exceptions.TicketAlreadyValidatedException;
import com.example.validation.exceptions.TicketNotFoundException;
import com.example.validation.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with id: " + id));
    }

    public Ticket addTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Ticket updateTicket(Long id, Ticket ticketDetails) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with id: " + id));

        if (ticketDetails.getDateTime() != null) {
            ticket.setDateTime(ticketDetails.getDateTime());
        }
        if (ticketDetails.getPrice() != null) {
            ticket.setPrice(ticketDetails.getPrice());
        }
        if (ticketDetails.getSeat() != null) {
            ticket.setSeat(ticketDetails.getSeat());
        }
        if (ticketDetails.getTelephoneNumber() != null) {
            ticket.setTelephoneNumber(ticketDetails.getTelephoneNumber());
        }
        if (ticketDetails.getName() != null) {
            ticket.setName(ticketDetails.getName());
        }
        if (ticketDetails.getSurname() != null) {
            ticket.setSurname(ticketDetails.getSurname());
        }
        if (ticketDetails.getEmail() != null) {
            ticket.setEmail(ticketDetails.getEmail());
        }
        if (ticketDetails.getFilmName() != null) {
            ticket.setFilmName(ticketDetails.getFilmName());
        }
        if (ticketDetails.isValidated() != null) {
            ticket.setValidated(ticketDetails.isValidated());
        }
        if (ticketDetails.getHall() != null) {
            ticket.setHall(ticketDetails.getHall());
        }
        return ticketRepository.save(ticket);
    }


    public void deleteTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with id: " + id));

        ticketRepository.delete(ticket);
    }

    public Ticket validateTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with id: " + id));

        if (!ticket.isValidated()) {
            ticket.setValidated(true);
            return ticketRepository.save(ticket);
        } else {
            throw new TicketAlreadyValidatedException("Ticket already validated with id: " + id);
        }
    }

    public boolean checkValidationStatus(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with id: " + id));

        return ticket.isValidated();
    }

    public List<Ticket> findTicketsByFilmNameAndTimeAndDate(String filmName, LocalTime time, LocalDate date) {
        return ticketRepository.findByFilmNameAndDateAndTime(filmName, date, time);
    }

}
