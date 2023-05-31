package com.example.validation.services;

import com.example.validation.dto.TicketDto;
import com.example.validation.entities.StaffWorker;
import com.example.validation.entities.Ticket;
import com.example.validation.exceptions.TicketAlreadyValidatedException;
import com.example.validation.exceptions.TicketNotFoundException;
import com.example.validation.repositories.StaffWorkerRepository;
import com.example.validation.repositories.TicketRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private StaffWorkerRepository staffWorkerRepository;

    @InjectMocks
    private TicketService ticketService;

    private Ticket ticket1;

    @BeforeEach
    public void setUp() {
        ticket1 = new Ticket(1L, "", LocalDateTime.now(), "", 0f, "", "", "", "", "", false, null, null, null, 1L);
    }

    @Test
    public void testGetAllTickets() {
        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket1));

        Ticket result = ticketService.getTicketById(1L);

        assertEquals(1, result.getId());

        Mockito.verify(ticketRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void testGetTicketByIdThrowsException() {
        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(TicketNotFoundException.class, () -> {
            ticketService.getTicketById(1L);
        });
    }

    @Test
    public void testDeleteTicket() {
        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket1));
        ticketService.deleteTicket(1L);

        Mockito.verify(ticketRepository, Mockito.times(1)).delete(ticket1);
        Mockito.verify(ticketRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void testDeleteTicketThrowsException() {
        Mockito.when(ticketRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(TicketNotFoundException.class, () -> {
            ticketService.deleteTicket(1L);
        });
    }

    @Test
    public void testUpdateTicket() {
        Mockito.when(ticketRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(ticket1));
        Mockito.when(ticketRepository.save(ArgumentMatchers.any(Ticket.class))).thenReturn(ticket1);

        TicketDto updatedTicket = new TicketDto();
        updatedTicket.setPrice(15.0f);
        updatedTicket.setId(1L);
        Ticket result = ticketService.updateTicket(1L, updatedTicket);

        Mockito.verify(ticketRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(ticketRepository, Mockito.times(1)).save(ticket1);

        assertEquals(ticket1, result);
    }

    @Test
    public void testValidateTicketAlreadyValidated() {
        ticket1.setValidated(true);
        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket1));
        assertThrows(TicketAlreadyValidatedException.class, () -> {
            ticketService.validateTicket(1L, 1L);
        });
    }

    @Test
    public void testValidateTicketNotFound() {
        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(TicketNotFoundException.class, () -> {
            ticketService.validateTicket(1L, 1L);
        });
    }

    @Test
    public void testValidateTicket() {
        ticket1.setValidated(false);
        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket1));
        Mockito.when(ticketRepository.save(Mockito.any(Ticket.class))).thenReturn(ticket1);
        Mockito.when(staffWorkerRepository.findById(1L)).thenReturn(Optional.of(new StaffWorker()));
        ticketService.validateTicket(1L, 1L);
        assertTrue(ticket1.isValidated());
    }

    @Test
    public void testDeleteTicketNotFound() {
        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(TicketNotFoundException.class, () -> {
            ticketService.deleteTicket(1L);
        });
    }

    @Test
    public void testUpdateTicketNotFound() {
        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(TicketNotFoundException.class, () -> {
            ticketService.updateTicket(1L, new TicketDto());
        });
    }

    @Test
    public void testCheckValidationStatus() {
        ticket1.setValidated(false);
        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket1));
        boolean isValidated = ticketService.checkValidationStatus(1L);
        assertFalse(isValidated);
        ticket1.setValidated(true);
        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket1));
        isValidated = ticketService.checkValidationStatus(1L);
        assertTrue(isValidated);
    }

}