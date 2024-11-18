package com.fares.ticketmanagement.repositories;

import com.fares.ticketmanagement.entities.Ticket;
import com.fares.ticketmanagement.entities.TypeTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // Query to calculate revenue by event and ticket type
    Double sumPrixTicketByIdEventAndTypeTicket(Long idEvent, TypeTicket typeTicket);

    // Query to count tickets by internaute
    Long countByIdInternaute(Long idInternaute);
}
