package com.fares.ticketmanagement.controllers;


import com.fares.ticketmanagement.entities.Ticket;
import com.fares.ticketmanagement.entities.TypeTicket;
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

    @PostMapping("/{idEvent}/{idInternaute}")
    public ResponseEntity<List<Ticket>> addTickets(@RequestBody List<Ticket> tickets,
                                                   @PathVariable Long idEvent,
                                                   @PathVariable Long idInternaute) {
        return ResponseEntity.ok(ticketService.addTickets(tickets, idEvent, idInternaute));
    }

    @GetMapping("/revenue")
    public ResponseEntity<Double> calculateRevenue(@RequestParam Long idEvent,
                                                   @RequestParam TypeTicket typeTicket) {
        return ResponseEntity.ok(ticketService.calculateRevenue(idEvent, typeTicket));
    }

    @GetMapping("/most-active")
    public ResponseEntity<Long> getMostActiveUser() {
        return ResponseEntity.ok(ticketService.getMostActiveUser());
    }
}
