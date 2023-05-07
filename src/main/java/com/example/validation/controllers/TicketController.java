package com.example.validation.controllers;

import com.example.validation.dto.TicketDto;
import com.example.validation.entities.Ticket;
import com.example.validation.services.TicketService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    @Value("${vars.server-ip}")
    private String serverIp;

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
    public ResponseEntity<Ticket> addTicket(@RequestBody TicketDto ticket) {
        Ticket ticketNew = ticketService.createTicket(ticket);
        return new ResponseEntity<>(ticketNew, HttpStatus.CREATED);
    }

    @DeleteMapping("tickets/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("tickets/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @RequestBody TicketDto ticketDetails) {
        Ticket ticket = ticketService.updateTicket(id, ticketDetails);
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @GetMapping("tickets/{id}/validation-status")
    public ResponseEntity<Boolean> checkValidationStatus(@PathVariable Long id) {
        boolean status = ticketService.checkValidationStatus(id);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @GetMapping("tickets/{movieName}/at/{time}")
    public ResponseEntity<List<Ticket>> getTicketsByMovieAndTime(@PathVariable String movieName, @PathVariable String time) {
        LocalDateTime dateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        List<Ticket> tickets = ticketService.findTicketsByFilmNameAndTimeDate(movieName, dateTime);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @GetMapping("tickets/session/{sessionId}")
    public ResponseEntity<List<Ticket>> getTicketsBySessionId(@PathVariable Long sessionId) {
        List<Ticket> tickets = ticketService.findTicketsBySessionId(sessionId);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @GetMapping("tickets/email/{email}")
    public ResponseEntity<List<Ticket>> getTicketsByEmail(@PathVariable String email) {
        List<Ticket> tickets = ticketService.findTicketsByEmail(email);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @GetMapping("/qr/{id}")
    public ResponseEntity<byte[]> generateQrCode(@PathVariable Long id) throws WriterException, IOException {
        String qrCodeText = "http://" + serverIp + "/tickets?ticketId=" + id;
        int qrCodeWidth = 250;
        int qrCodeHeight = 250;
        String fileType = "png";

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, qrCodeWidth, qrCodeHeight);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, fileType, outputStream);
        byte[] qrCodeBytes = outputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(qrCodeBytes, headers, HttpStatus.OK);
    }

}
