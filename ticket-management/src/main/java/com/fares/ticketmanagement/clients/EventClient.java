package com.fares.ticketmanagement.clients;

import com.fares.ticketmanagement.dto.Event;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "event-service", url = "http://localhost:8082/api/events")
public interface EventClient {

    @GetMapping("/{id}")
    Event getEventById(@PathVariable Long id);

    @PutMapping("/{id}")
    void updateEvent(@PathVariable Long id, @RequestBody Event event);
}
