package com.fares.ticketmanagement.controllers;

import com.fares.ticketmanagement.entities.Ticket;
import com.fares.ticketmanagement.entities.TypeTicket;
import com.fares.ticketmanagement.entities.User;
import com.fares.ticketmanagement.services.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // D. Endpoint to add tickets and associate them with an event and user
    @PostMapping("/{idEvenement}/internautes/{idInternaute}")
    public ResponseEntity<List<Ticket>> ajouterTickets(
            @RequestBody List<Ticket> tickets,
            @PathVariable Long idEvenement,
            @PathVariable Long idInternaute) {
        return ResponseEntity.ok(ticketService.ajouterTicketsEtAffecterAEvenementEtInternaute(tickets, idEvenement, idInternaute));
    }

    // E. Endpoint to calculate revenue for an event by ticket type
    @GetMapping("/revenue")
    public ResponseEntity<Double> calculerMontantRecupere(
            @RequestParam Long id,
            @RequestParam TypeTicket typeTicket) {
        return ResponseEntity.ok(ticketService.montantRecupereParEvtEtTypeTicket(id, typeTicket));
    }

    @GetMapping("/most-active-user")
    public ResponseEntity<User> getMostActiveUser() {
        return ResponseEntity.ok(ticketService.getMostActiveUser());
    }
}
