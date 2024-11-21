package com.fares.ticketmanagement.clients;

import com.fares.ticketmanagement.entities.Evenement;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "event-service", url = "http://localhost:8082/api/evenements")
public interface EventClient {

    @PutMapping("/{idEvenement}/seats")
    Evenement updateRemainingSeats(@PathVariable Long idEvenement, @RequestParam int remainingSeats);

    @GetMapping("/by-id")
    Evenement findById(@RequestParam Long id);
}
