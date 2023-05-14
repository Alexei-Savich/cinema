package com.example.validation.controllers;

import com.example.validation.entities.Ticket;
import com.example.validation.repositories.TicketRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void clearData() {
        ticketRepository.deleteAll();
    }

    @Test
    public void addTicketTest() throws Exception {
        String body = """
                {
                    "hall": "3",
                    "dateTime": "2023-05-10 18:45",
                    "seat": "28",
                    "price": 42.8,
                    "telephoneNumber": "",
                    "name": "John",
                    "surname": "Doe",
                    "email": "john.doe@example.com",
                    "filmName": "John Wick 4",
                    "additionalServices": "",
                    "sessionId": 13
                }
                """;
        mockMvc.perform(post("/tickets/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());

        List<Ticket> tickets = ticketRepository.findAll();
        assertEquals(1, tickets.size());

        Ticket ticket = tickets.get(0);
        assertEquals("3", ticket.getHall());
        assertEquals("28", ticket.getSeat());
        assertEquals(42.8f, ticket.getPrice());
        assertEquals("", ticket.getTelephoneNumber());
        assertEquals("John", ticket.getName());
        assertEquals("Doe", ticket.getSurname());
        assertEquals("john.doe@example.com", ticket.getEmail());
        assertEquals("John Wick 4", ticket.getFilmName());
        assertFalse(ticket.isValidated());
    }

    @Test
    public void deleteTicketTest() throws Exception {
        Ticket ticket = new Ticket(1L, "Hall 1", LocalDateTime.now(), "A1", 10.00f, "123456789", "John", "Doe", "john.doe@example.com", "The Matrix", false, null, null, null, 1L);
        ticket = ticketRepository.save(ticket);
        mockMvc.perform(delete("/tickets/tickets/{id}", ticket.getId()))
                .andExpect(status().isNoContent());

        assertFalse(ticketRepository.findById(ticket.getId()).isPresent());
    }

    @Test
    public void updateTicketTest() throws Exception {
        Ticket ticket = ticketRepository.save(new Ticket(1L, "Hall 1", LocalDateTime.now(), "A1", 10.00f, "123456789", "John", "Doe", "john.doe@example.com", "The Matrix", false, null, null, null, 1L));
        String body = """
                {
                    "hall": "3",
                    "dateTime": "2023-05-10 18:45",
                    "seat": "28",
                    "price": 42.8,
                    "telephoneNumber": "",
                    "name": "John",
                    "surname": "Doe",
                    "email": "john.doe@example.com",
                    "filmName": "John Wick 4",
                    "additionalServices": "",
                    "sessionId": 13
                }
                """;
        mockMvc.perform(put("/tickets/tickets/{id}", ticket.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
        Ticket updatedTicket = ticketRepository.findById(ticket.getId()).get();
        assertEquals("3", updatedTicket.getHall());
        assertEquals("28", updatedTicket.getSeat());
        assertEquals(42.8f, updatedTicket.getPrice());
        assertEquals("", updatedTicket.getTelephoneNumber());
        assertEquals("John", updatedTicket.getName());
        assertEquals("Doe", updatedTicket.getSurname());
        assertEquals("john.doe@example.com", updatedTicket.getEmail());
        assertEquals("John Wick 4", updatedTicket.getFilmName());
        assertFalse(updatedTicket.isValidated());
    }

}
