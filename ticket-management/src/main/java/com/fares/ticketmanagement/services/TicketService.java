package com.fares.ticketmanagement.services;

import com.fares.ticketmanagement.clients.EventClient;
import com.fares.ticketmanagement.clients.UserClient;
import com.fares.ticketmanagement.entities.Evenement;
import com.fares.ticketmanagement.entities.Ticket;
import com.fares.ticketmanagement.entities.TypeTicket;
import com.fares.ticketmanagement.entities.User;
import com.fares.ticketmanagement.repositories.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final EventClient eventClient;
    private final UserClient userClient;

    public TicketService(TicketRepository ticketRepository, EventClient eventClient, UserClient userClient) {
        this.ticketRepository = ticketRepository;
        this.eventClient = eventClient;
        this.userClient = userClient;
    }

    // D. Add tickets and associate them with event and internaute, update seats in the event
    public List<Ticket> ajouterTicketsEtAffecterAEvenementEtInternaute(List<Ticket> tickets, Long idEvenement, Long idInternaute) {
        // Fetch user details from User Management Microservice
        User user = userClient.getUserById(idInternaute);
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + idInternaute);
        }

        // Fetch event details from Event Management Microservice
        Evenement event = eventClient.findById(idEvenement);
        if (event.getNbPlacesRestantes() < tickets.size()) {
            throw new UnsupportedOperationException("nombre de places demandées indisponible");
        }

        // Update the remaining seats in the event
        event.setNbPlacesRestantes(event.getNbPlacesRestantes() - tickets.size());
        eventClient.updateRemainingSeats(idEvenement, event.getNbPlacesRestantes());

        // Assign tickets to the event and user
        tickets.forEach(ticket -> {
            ticket.setIdEvent(idEvenement);
            ticket.setIdInternaute(idInternaute);
        });

        return ticketRepository.saveAll(tickets);
    }

    // E. Calculate revenue for an event and ticket type
    public Double montantRecupereParEvtEtTypeTicket(Long id, TypeTicket typeTicket) {
        Evenement event = eventClient.findById(id);
        List<Ticket> tickets = ticketRepository.findByIdEventAndTypeTicket(event.getId(), typeTicket);

        // Calculate total revenue
        return tickets.stream()
                .mapToDouble(Ticket::getPrixTicket)
                .sum();
    }

    public User getMostActiveUser() {
        // Fetch ticket counts grouped by user
        List<Object[]> userTicketCounts = ticketRepository.countTicketsGroupedByIdInternaute();

        // Find the user ID with the highest ticket count
        Long mostActiveUserId = userTicketCounts.stream()
                .max((a, b) -> ((Long) a[1]).compareTo((Long) b[1])) // Compare ticket counts
                .map(a -> (Long) a[0]) // Extract the user ID
                .orElseThrow(() -> new RuntimeException("No users found"));

        // Fetch user details from User Management Microservice
        return userClient.getUserById(mostActiveUserId);
    }

}
