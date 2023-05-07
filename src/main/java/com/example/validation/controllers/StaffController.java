package com.example.validation.controllers;

import com.example.validation.entities.StaffWorker;
import com.example.validation.entities.Ticket;
import com.example.validation.repositories.StaffWorkerRepository;
import com.example.validation.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StaffController {

    private final TicketService ticketService;
    private final StaffWorkerRepository staffWorkerService;

    @Autowired
    public StaffController(TicketService ticketService, StaffWorkerRepository staffWorkerService) {
        this.ticketService = ticketService;
        this.staffWorkerService = staffWorkerService;
    }

    @GetMapping("/tickets")
    public String getTicketByIdView(@RequestParam(name = "ticketId", required = false) Long id,
                                    RedirectAttributes redirectAttributes,
                                    Model model) {
        if (id == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Id is missing!");
            return "redirect:/";
        }
        Ticket ticket = ticketService.getTicketByIdNullable(id);
        if (ticket != null) {
            model.addAttribute("ticket", ticket);
            model.addAttribute("staffWorker", ticket.getValidatedBy());
            return "ticket/ticket-details";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "User not found");
        return "redirect:/";
    }

    @PutMapping("/staff/tickets/{id}/validate")
    public ResponseEntity<Ticket> validateTicket(@PathVariable Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userEmail = userDetails.getUsername();
        StaffWorker user = staffWorkerService.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));
        Long validatedByUserId = user.getWorkerId();

        Ticket ticket = ticketService.validateTicket(id, validatedByUserId);
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

}
