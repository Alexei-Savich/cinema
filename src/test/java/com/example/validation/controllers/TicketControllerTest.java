//package com.example.validation.controllers;
//
//import com.example.validation.entities.Ticket;
//import com.example.validation.repositories.TicketRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@TestPropertySource(locations = "classpath:application-integrationtest.properties")
//public class TicketControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private TicketRepository ticketRepository;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @AfterEach
//    void clearData() {
//        ticketRepository.deleteAll();
//    }
//
//    @Test
//    public void addTicketTest() throws Exception {
//        Ticket ticketDTO = new Ticket(null, LocalDate.now(), LocalTime.of(20, 0), new BigDecimal("10.00"), 1, 10, "123456789", "John", "Doe", "john.doe@example.com", "The Matrix", 1, false);
//        mockMvc.perform(post("/tickets")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(ticketDTO)))
//                .andExpect(status().isOk());
//
//        List<Ticket> tickets = ticketRepository.findAll();
//        assertEquals(1, tickets.size());
//
//        Ticket ticket = tickets.get(0);
//        assertEquals(LocalDate.now(), ticket.getDate());
//        assertEquals(LocalTime.of(20, 0), ticket.getTime());
//        assertEquals(new BigDecimal("10.00"), ticket.getPrice());
//        assertEquals(10, ticket.getSeatNumber());
//        assertEquals("123456789", ticket.getTelephoneNumber());
//        assertEquals("John", ticket.getName());
//        assertEquals("Doe", ticket.getSurname());
//        assertEquals("john.doe@example.com", ticket.getEmail());
//        assertEquals("The Matrix", ticket.getFilmName());
//        assertFalse(ticket.isValidated());
//    }
//
//    @Test
//    public void deleteTicketTest() throws Exception {
//        Ticket ticket = new Ticket(null, LocalDate.now(), LocalTime.of(20, 0), BigDecimal.valueOf(10.0), 1, 10, "123456789", "John", "Doe", "john.doe@example.com", "The Matrix", 1, false);
//        ticketRepository.save(ticket);
//        mockMvc.perform(delete("/tickets/{id}", ticket.getId()))
//                .andExpect(status().isOk());
//
//        assertFalse(ticketRepository.findById(ticket.getId()).isPresent());
//    }
//
//    @Test
//    public void updateTicketTest() throws Exception {
//        Ticket ticket = ticketRepository.save(new Ticket(null, LocalDate.now(), LocalTime.of(20, 0), BigDecimal.valueOf(10.0), 1, 10, "123456789", "John", "Doe", "john.doe@example.com", "The Matrix", 1, false));
//        Ticket ticketDTO = new Ticket(null, LocalDate.now(), LocalTime.of(21, 0), null, 1, 9, null, "Jane", null, null, "The Matrix Reloaded", 1, null);
//        mockMvc.perform(put("/tickets/{id}", ticket.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(ticketDTO)))
//                .andExpect(status().isOk());
//
//        Ticket updatedTicket = ticketRepository.findById(ticket.getId()).get();
//        assertEquals(LocalDate.now(), updatedTicket.getDate());
//        assertEquals(LocalTime.of(21, 0), updatedTicket.getTime());
//        assertEquals(new BigDecimal("10.00"), updatedTicket.getPrice());
//        assertEquals(9, updatedTicket.getSeatNumber());
//        assertEquals("123456789", updatedTicket.getTelephoneNumber());
//        assertEquals("Jane", updatedTicket.getName());
//        assertEquals("Doe", updatedTicket.getSurname());
//        assertEquals("john.doe@example.com", updatedTicket.getEmail());
//        assertEquals("The Matrix Reloaded", updatedTicket.getFilmName());
//        assertFalse(updatedTicket.isValidated());
//    }
//}
