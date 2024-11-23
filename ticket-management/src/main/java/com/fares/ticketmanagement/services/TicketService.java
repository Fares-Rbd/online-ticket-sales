package com.fares.ticketmanagement.services;

import com.fares.ticketmanagement.clients.EventClient;
import com.fares.ticketmanagement.clients.UserClient;
import com.fares.ticketmanagement.entities.Evenement;
import com.fares.ticketmanagement.entities.Ticket;
import com.fares.ticketmanagement.entities.TypeTicket;
import com.fares.ticketmanagement.entities.User;
import com.fares.ticketmanagement.repositories.TicketRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
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
    @CircuitBreaker(name = "default", fallbackMethod = "fallbackAjouterTickets")
    @Retry(name = "default", fallbackMethod = "fallbackAjouterTickets")
    public List<Ticket> ajouterTicketsEtAffecterAEvenementEtInternaute(List<Ticket> tickets, Long idEvenement, Long idInternaute) {
        User user = userClient.getUserById(idInternaute);
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + idInternaute);
        }

        Evenement event = eventClient.findById(idEvenement);
        if (event.getNbPlacesRestantes() < tickets.size()) {
            throw new UnsupportedOperationException("nombre de places demandées indisponible");
        }

        event.setNbPlacesRestantes(event.getNbPlacesRestantes() - tickets.size());
        eventClient.updateRemainingSeats(idEvenement, event.getNbPlacesRestantes());

        tickets.forEach(ticket -> {
            ticket.setIdEvent(idEvenement);
            ticket.setIdInternaute(idInternaute);
        });

        return ticketRepository.saveAll(tickets);
    }


    // E. Calculate revenue for an event and ticket type
    @CircuitBreaker(name = "default", fallbackMethod = "fallbackMontantRecupere")
    @Retry(name = "default", fallbackMethod = "fallbackMontantRecupere")
    public Double montantRecupereParEvtEtTypeTicket(Long id, TypeTicket typeTicket) {
        Evenement event = eventClient.findById(id);
        List<Ticket> tickets = ticketRepository.findByIdEventAndTypeTicket(event.getId(), typeTicket);

        return tickets.stream()
                .mapToDouble(Ticket::getPrixTicket)
                .sum();
    }


    @CircuitBreaker(name = "default", fallbackMethod = "fallbackGetMostActiveUser")
    @Retry(name = "default", fallbackMethod = "fallbackGetMostActiveUser")
    public User getMostActiveUser() {
        List<Object[]> userTicketCounts = ticketRepository.countTicketsGroupedByIdInternaute();
        Long mostActiveUserId = userTicketCounts.stream()
                .max((a, b) -> ((Long) a[1]).compareTo((Long) b[1]))
                .map(a -> (Long) a[0])
                .orElseThrow(() -> new RuntimeException("No users found"));

        return userClient.getUserById(mostActiveUserId);
    }

    public List<Ticket> fallbackAjouterTickets(List<Ticket> tickets, Long idEvenement, Long idInternaute, Throwable throwable) {
        throw new RuntimeException("Failed to add tickets and associate with event and user. Reason: " + throwable.getMessage());
    }

    public Double fallbackMontantRecupere(Long id, TypeTicket typeTicket, Throwable throwable) {
        throw new RuntimeException("Failed to calculate revenue for event and ticket type. Reason: " + throwable.getMessage());
    }

    public User fallbackGetMostActiveUser(Throwable throwable) {
        throw new RuntimeException("Failed to retrieve most active user. Reason: " + throwable.getMessage());
    }
}
