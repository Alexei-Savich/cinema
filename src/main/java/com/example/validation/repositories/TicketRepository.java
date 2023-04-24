package com.example.validation.repositories;

import com.example.validation.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByFilmName(String filmName);

    List<Ticket> findByFilmNameAndDateTime(String filmName, LocalDateTime dateTime);

}
