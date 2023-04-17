//package com.example.validation.services;
//
//import com.example.validation.entities.Ticket;
//import com.example.validation.exceptions.TicketAlreadyValidatedException;
//import com.example.validation.exceptions.TicketNotFoundException;
//import com.example.validation.repositories.TicketRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentMatchers;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@ExtendWith(MockitoExtension.class)
//public class TicketServiceTest {
//
//    @Mock
//    private TicketRepository ticketRepository;
//
//    @InjectMocks
//    private TicketService ticketService;
//
//    private Ticket ticket1;
//    private Ticket ticket2;
//
//    @BeforeEach
//    public void setUp() {
//        ticket1 = new Ticket(1L, "1", LocalDate.now(), "1",  BigDecimal.valueOf(10.0), "1234567890",
//                "John", "Doe", "johndoe@example.com", "The Matrix", false, null, null, null);
//        ticket2 = new Ticket(2L, "2", LocalDate.now().plusDays(1), "2",  BigDecimal.valueOf(10.0), "1234567890",
//                "John", "Doe", "johndoe@example.com", "The Matrix", false, null, null, null);
//    }
//
//    @Test
//    public void testGetAllTickets() {
//        List<Ticket> ticketList = new ArrayList<>();
//        ticketList.add(ticket1);
//        ticketList.add(ticket2);
//
//        Mockito.when(ticketRepository.findAll()).thenReturn(ticketList);
//
//        List<Ticket> result = ticketService.getAllTickets();
//
//        assertEquals(2, result.size());
//        assertEquals(ticket1, result.get(0));
//        assertEquals(ticket2, result.get(1));
//
//        Mockito.verify(ticketRepository, Mockito.times(1)).findAll();
//    }
//
//    @Test
//    public void testGetTicketById() {
//        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket1));
//
//        Ticket result = ticketService.getTicketById(1L);
//
//        assertEquals(ticket1, result);
//
//        Mockito.verify(ticketRepository, Mockito.times(1)).findById(1L);
//    }
//
//    @Test
//    public void testGetTicketByIdThrowsException() {
//        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.empty());
//
//        Assertions.assertThrows(TicketNotFoundException.class, () -> {
//            ticketService.getTicketById(1L);
//        });
//    }
//
//    @Test
//    public void testAddTicket() {
//        Mockito.when(ticketRepository.save(ticket1)).thenReturn(ticket1);
//
//        Ticket result = ticketService.addTicket(ticket1);
//
//        assertEquals(ticket1, result);
//
//        Mockito.verify(ticketRepository, Mockito.times(1)).save(ticket1);
//    }
//
//    @Test
//    public void testDeleteTicket() {
//        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket1));
//        ticketService.deleteTicket(1L);
//
//        Mockito.verify(ticketRepository, Mockito.times(1)).delete(ticket1);
//        Mockito.verify(ticketRepository, Mockito.times(1)).findById(1L);
//    }
//
//    @Test
//    public void testDeleteTicketThrowsException() {
//        Mockito.when(ticketRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
//
//        Assertions.assertThrows(TicketNotFoundException.class, () -> {
//            ticketService.deleteTicket(1L);
//        });
//    }
//
//    @Test
//    public void testUpdateTicket() {
//        Mockito.when(ticketRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(ticket1));
//        Mockito.when(ticketRepository.save(ArgumentMatchers.any(Ticket.class))).thenReturn(ticket1);
//
//        Ticket updatedTicket = new Ticket();
//        updatedTicket.setPrice(BigDecimal.valueOf(15.0));
//        updatedTicket.setId(1L);
//        Ticket result = ticketService.updateTicket(1L, updatedTicket);
//
//        Mockito.verify(ticketRepository, Mockito.times(1)).findById(1L);
//        Mockito.verify(ticketRepository, Mockito.times(1)).save(ticket1);
//
//        assertEquals(ticket1, result);
//    }
//
//    @Test
//    public void testValidateTicketAlreadyValidated() {
//        ticket1.setValidated(true);
//        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket1));
//        assertThrows(TicketAlreadyValidatedException.class, () -> {
//            ticketService.validateTicket(1L);
//        });
//    }
//
//    @Test
//    public void testValidateTicketNotFound() {
//        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.empty());
//        assertThrows(TicketNotFoundException.class, () -> {
//            ticketService.validateTicket(1L);
//        });
//    }
//
//    @Test
//    public void testValidateTicket() {
//        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket1));
//        Mockito.when(ticketRepository.save(Mockito.any(Ticket.class))).thenReturn(ticket1);
//        ticketService.validateTicket(1L);
//        assertTrue(ticket1.isValidated());
//    }
//
//    @Test
//    public void testDeleteTicketNotFound() {
//        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.empty());
//        assertThrows(TicketNotFoundException.class, () -> {
//            ticketService.deleteTicket(1L);
//        });
//    }
//
//    @Test
//    public void testUpdateTicketNotFound() {
//        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.empty());
//        assertThrows(TicketNotFoundException.class, () -> {
//            ticketService.updateTicket(1L, new Ticket());
//        });
//    }
//
//    @Test
//    public void testCheckValidationStatus() {
//        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket1));
//        boolean isValidated = ticketService.checkValidationStatus(1L);
//        assertFalse(isValidated);
//        ticket1.setValidated(true);
//        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket1));
//        isValidated = ticketService.checkValidationStatus(1L);
//        assertTrue(isValidated);
//    }
//
//}