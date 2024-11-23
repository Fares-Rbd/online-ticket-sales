package com.fares.ticketmanagement.clients;

import com.fares.ticketmanagement.entities.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "http://localhost:8081/api/users")
public interface UserClient {

    @GetMapping("/{id}")
    User getUserById(@PathVariable Long id);

    default User fallbackGetUserById(Long id, Throwable throwable) {
        throw new RuntimeException("Failed to retrieve user by ID: " + id + ". Reason: " + throwable.getMessage());
    }
}

