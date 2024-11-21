package com.fares.ticketmanagement.repositories;

import com.fares.ticketmanagement.entities.Ticket;
import com.fares.ticketmanagement.entities.TypeTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // Query to calculate revenue by event and ticket type
    @Query("SELECT SUM(t.prixTicket) FROM Ticket t WHERE t.idEvent = :idEvent AND t.typeTicket = :typeTicket")
    Double sumPrixTicketByIdEventAndTypeTicket(@Param("idEvent") Long idEvent, @Param("typeTicket") TypeTicket typeTicket);

    // Count tickets by user (idInternaute)
    Long countByIdInternaute(Long idInternaute);

    // Get all tickets for an event and ticket type
    List<Ticket> findByIdEventAndTypeTicket(Long idEvent, TypeTicket typeTicket);

    @Query("SELECT t.idInternaute, COUNT(t.id) FROM Ticket t GROUP BY t.idInternaute")
    List<Object[]> countTicketsGroupedByIdInternaute();
}
