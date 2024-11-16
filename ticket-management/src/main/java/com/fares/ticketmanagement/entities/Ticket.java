package com.fares.ticketmanagement.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codeTicket;
    private Double prixTicket;
    private String typeTicket;
    private Long userId; // References the User in the User Service
    private Long eventId; // References the Event in the Event Service
}
