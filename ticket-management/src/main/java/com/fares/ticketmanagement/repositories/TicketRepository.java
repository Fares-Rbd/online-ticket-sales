package com.fares.ticketmanagement.repositories;

import com.fares.ticketmanagement.entities.Ticket;
import com.fares.ticketmanagement.entities.TypeTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // Query to calculate revenue by event and ticket type
    Double sumPrixTicketByIdEventAndTypeTicket(Long idEvent, TypeTicket typeTicket);

    // Custom query to count tickets grouped by internaute (user ID)
    @Query("SELECT t.idInternaute, COUNT(t) FROM Ticket t GROUP BY t.idInternaute")
    List<Object[]> countTicketsGroupedByIdInternaute();
}
