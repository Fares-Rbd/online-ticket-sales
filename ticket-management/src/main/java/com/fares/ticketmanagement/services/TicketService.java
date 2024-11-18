package com.fares.ticketmanagement.services;


import com.fares.ticketmanagement.clients.EventClient;
import com.fares.ticketmanagement.dto.Event;
import com.fares.ticketmanagement.entities.Ticket;
import com.fares.ticketmanagement.entities.TypeTicket;
import com.fares.ticketmanagement.repositories.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final EventClient eventClient;

    public TicketService(TicketRepository ticketRepository, EventClient eventClient) {
        this.ticketRepository = ticketRepository;
        this.eventClient = eventClient;
    }

    public List<Ticket> addTickets(List<Ticket> tickets, Long idEvent, Long idInternaute) {
        // Fetch event details from Event Microservice
        Event event = eventClient.getEventById(idEvent);

        if (event == null) {
            throw new RuntimeException("Event not found with id: " + idEvent);
        }

        if (event.getNbPlacesRestantes() < tickets.size()) {
            throw new UnsupportedOperationException("Nombre de places demandées indisponible");
        }

        // Update event seat count
        event.setNbPlacesRestantes(event.getNbPlacesRestantes() - tickets.size());
        eventClient.updateEvent(idEvent, event);

        // Save tickets
        tickets.forEach(ticket -> {
            ticket.setIdEvent(idEvent);
            ticket.setIdInternaute(idInternaute);
        });
        return ticketRepository.saveAll(tickets);
    }
    public Double calculateRevenue(Long idEvent, TypeTicket typeTicket) {
        return ticketRepository.sumPrixTicketByIdEventAndTypeTicket(idEvent, typeTicket);
    }

    public Long getMostActiveUser() {
        List<Object[]> userTicketCounts = ticketRepository.countTicketsByIdInternaute();
        return userTicketCounts.stream()
                .max((a, b) -> (Long) a[1] - (Long) b[1])
                .map(a -> (Long) a[0])
                .orElseThrow(() -> new RuntimeException("No users found"));
    }
}
