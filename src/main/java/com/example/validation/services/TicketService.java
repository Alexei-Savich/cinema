package com.example.validation.services;

import com.example.validation.dto.TicketDto;
import com.example.validation.entities.Ticket;
import com.example.validation.exceptions.TicketAlreadyValidatedException;
import com.example.validation.exceptions.TicketNotFoundException;
import com.example.validation.repositories.StaffWorkerRepository;
import com.example.validation.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final StaffWorkerRepository staffWorkerRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository, StaffWorkerRepository staffWorkerRepository) {
        this.ticketRepository = ticketRepository;
        this.staffWorkerRepository = staffWorkerRepository;
    }

    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with id: " + id));
    }

    public Ticket getTicketByIdNullable(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    public Ticket createTicket(TicketDto ticketDetails) {
        Ticket ticket = new Ticket();
        LocalDateTime dateTime = LocalDateTime.parse(ticketDetails.getDateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        ticket.setDateTime(dateTime);
        ticket.setPrice(ticketDetails.getPrice());
        ticket.setSeat(ticketDetails.getSeat());
        ticket.setTelephoneNumber(ticketDetails.getTelephoneNumber());
        ticket.setName(ticketDetails.getName());
        ticket.setSurname(ticketDetails.getSurname());
        ticket.setEmail(ticketDetails.getEmail());
        ticket.setFilmName(ticketDetails.getFilmName());
        ticket.setValidated(false);
        ticket.setHall(ticketDetails.getHall());
        ticket.setAdditionalServices(ticketDetails.getAdditionalServices());
        ticket.setSessionId(ticketDetails.getSessionId());
        return ticketRepository.save(ticket);
    }

    public Ticket updateTicket(Long id, TicketDto ticketDetails) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with id: " + id));

        if (ticketDetails.getDateTime() != null) {
            LocalDateTime dateTime = LocalDateTime.parse(ticketDetails.getDateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            ticket.setDateTime(dateTime);
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
        if (ticketDetails.getValidated() != null) {
            ticket.setValidated(ticketDetails.getValidated());
        }
        if (ticketDetails.getHall() != null) {
            ticket.setHall(ticketDetails.getHall());
        }
        if (ticketDetails.getAdditionalServices() != null) {
            ticket.setAdditionalServices(ticketDetails.getAdditionalServices());
        }
        if (ticketDetails.getSessionId() != null) {
            ticket.setSessionId(ticketDetails.getSessionId());
        }
        return ticketRepository.save(ticket);
    }


    public void deleteTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with id: " + id));

        ticketRepository.delete(ticket);
    }

    public Ticket validateTicket(Long id, Long workerId) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with id: " + id));

        if (!ticket.isValidated()) {
            ticket.setValidated(true);
            ticket.setValidatedAt(LocalDateTime.now());
            ticket.setValidatedBy(staffWorkerRepository.findById(workerId).orElseThrow(() -> new RuntimeException("Worker not found with id: " + workerId)));
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

    public List<Ticket> findTicketsBySessionId(Long sessionId) {
        return ticketRepository.findBySessionId(sessionId);
    }

    public List<Ticket> findTicketsByEmail(String email) {
        return ticketRepository.findByEmail(email);
    }
}
