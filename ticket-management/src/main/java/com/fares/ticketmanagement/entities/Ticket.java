package com.fares.ticketmanagement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String codeTicket;

    private Double prixTicket;

    @Enumerated(EnumType.STRING)
    private TypeTicket typeTicket;

    private Long idEvent; // Reference to Event

    private Long idInternaute; // Reference to Internaute
}
