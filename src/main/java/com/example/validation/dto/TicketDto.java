package com.example.validation.dto;

import com.example.validation.entities.StaffWorker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;


import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Jacksonized
public class TicketDto {

    private Long id;
    private String hall;
    private String dateTime;
    private String seat;
    private Float price;
    private String telephoneNumber;
    private String name;
    private String surname;
    private String email;
    private String filmName;
    private Boolean validated;
    private Long validatedById;
    private LocalDate validatedAt;
    private String additionalServices;

}
