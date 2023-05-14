package com.example.validation.controllers;

import com.example.validation.entities.StaffWorker;
import com.example.validation.entities.Ticket;
import com.example.validation.repositories.StaffWorkerRepository;
import com.example.validation.repositories.TicketRepository;
import com.example.validation.services.StaffWorkerService;
import com.example.validation.services.TicketService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integration-test.properties")
public class StaffControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketRepository ticketRepository;

    @AfterEach
    void clearData() {
        ticketRepository.deleteAll();
    }

    @Test
    public void getTicketByIdViewTest() throws Exception {
        Ticket ticket = new Ticket(1L, "Hall 1", LocalDateTime.now(), "A1", 10.00f, "123456789", "John", "Doe", "john.doe@example.com", "The Matrix", false, null, null, null, 1L);
        ticket = ticketRepository.save(ticket);

        //somewhere in the code rounding is happening, so performing it here as well
        if(ticket.getDateTime().getNano() >= 500000000) {
            ticket.setDateTime(ticket.getDateTime().truncatedTo(ChronoUnit.SECONDS).plusSeconds(1));
        } else {
            ticket.setDateTime(ticket.getDateTime().truncatedTo(ChronoUnit.SECONDS));
        }
        String userEmail = "user@example.com";

        UserDetails userDetails = mock(UserDetails.class);
        Authentication authentication = mock(Authentication.class);

        StaffWorker staffWorker = new StaffWorker();
        staffWorker.setWorkerId(1L);

        when(userDetails.getUsername()).thenReturn(userEmail);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        StaffWorkerRepository staffWorkerRepository = mock(StaffWorkerRepository.class);

        StaffController staffController = new StaffController(ticketService, staffWorkerRepository);
        MockMvc testMockMvc = MockMvcBuilders.standaloneSetup(staffController).build();

        testMockMvc.perform(MockMvcRequestBuilders.get("/staff/tickets")
                        .principal(authentication)
                        .param("ticketId", String.valueOf(ticket.getId())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("ticket/ticket-details"))
                .andExpect(model().attributeExists("ticket"))
                .andExpect(model().attribute("ticket", ticket))
                .andExpect(model().attributeDoesNotExist("staffWorker"));
    }

    @Test
    public void getTicketByIdViewTest_NoAuthorization() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/staff/tickets"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void getTicketByIdViewTest_NotFound() throws Exception {
        Long ticketId = 1L;

        String userEmail = "user@example.com";
        StaffWorker staffWorker = new StaffWorker();
        staffWorker.setWorkerId(1L);

        UserDetails userDetails = mock(UserDetails.class);
        Authentication authentication = mock(Authentication.class);

        when(userDetails.getUsername()).thenReturn(userEmail);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        StaffWorkerRepository staffWorkerRepository = mock(StaffWorkerRepository.class);

        StaffController staffController = new StaffController(ticketService, staffWorkerRepository);
        MockMvc testMockMvc = MockMvcBuilders.standaloneSetup(staffController).build();

        testMockMvc.perform(MockMvcRequestBuilders.get("/staff/tickets")
                        .param("ticketId", String.valueOf(ticketId)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("errorMessage"));
    }

    @Test
    public void validateTicketTest() throws Exception {
        Ticket ticket = new Ticket(1L, "Hall 1", LocalDateTime.now(), "A1", 10.00f, "123456789", "John", "Doe", "john.doe@example.com", "The Matrix", false, null, null, null, 1L);
        String userEmail = "user@example.com";
        StaffWorker staffWorker = new StaffWorker();
        staffWorker.setWorkerId(1L);

        TicketRepository ticketRepository = mock(TicketRepository.class);
        UserDetails userDetails = mock(UserDetails.class);
        Authentication authentication = mock(Authentication.class);
        StaffWorkerRepository staffWorkerRepository = mock(StaffWorkerRepository.class);
        TicketService ticketService = new TicketService(ticketRepository, staffWorkerRepository);
        StaffController staffController = new StaffController(ticketService, staffWorkerRepository);

        when(userDetails.getUsername()).thenReturn(userEmail);

        when(authentication.getPrincipal()).thenReturn(userDetails);

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        Ticket validated = new Ticket(1L, "Hall 1", LocalDateTime.now(), "A1", 10.00f, "123456789", "John", "Doe", "john.doe@example.com", "The Matrix", true, staffWorker, LocalDateTime.now(), null, 1L);

        when(ticketRepository.save(any(Ticket.class))).thenReturn(validated);

        when(staffWorkerRepository.findByEmail(userEmail)).thenReturn(Optional.of(staffWorker));
        when(staffWorkerRepository.findById(1L)).thenReturn(Optional.of(staffWorker));

        MockMvc testMockMvc = MockMvcBuilders.standaloneSetup(staffController).build();

        testMockMvc.perform(MockMvcRequestBuilders.put("/staff/tickets/{id}/validate", ticket.getId())
                        .principal(authentication))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(ticket.getId()));
    }

    @Test
    public void validateTicketTest_Unauthorized() throws Exception {
        Ticket ticket = new Ticket(1L, "Hall 1", LocalDateTime.now(), "A1", 10.00f, "123456789", "John", "Doe", "john.doe@example.com", "The Matrix", false, null, null, null, 1L);

        ticket = ticketRepository.save(ticket);

        mockMvc.perform(MockMvcRequestBuilders.put("/staff/tickets/{id}/validate", ticket.getId()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

}
