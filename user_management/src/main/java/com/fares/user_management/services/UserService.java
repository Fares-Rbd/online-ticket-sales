package com.fares.user_management.services;

import com.fares.user_management.entities.User;
import com.fares.user_management.repositories.UserRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Add a new user
    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackAddUser")
    @Retry(name = "userService", fallbackMethod = "fallbackAddUser")
    public User addUser(User user) {
        return userRepository.save(user);
    }

    // Get a user by ID
    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackGetUserById")
    @Retry(name = "userService", fallbackMethod = "fallbackGetUserById")
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    // Get all users
    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackGetAllUsers")
    @Retry(name = "userService", fallbackMethod = "fallbackGetAllUsers")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Update user details
    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackUpdateUser")
    @Retry(name = "userService", fallbackMethod = "fallbackUpdateUser")
    public User updateUser(Long id, User userDetails) {
        User existingUser = getUserById(id);
        existingUser.setName(userDetails.getName());
        existingUser.setTrancheAge(userDetails.getTrancheAge());
        return userRepository.save(existingUser);
    }

    // Delete a user
    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackDeleteUser")
    @Retry(name = "userService", fallbackMethod = "fallbackDeleteUser")
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Fallback methods for resilience, now throwing RuntimeException
    public User fallbackAddUser(User user, Throwable t) {
        // Throwing a runtime exception with a relevant error message
        throw new RuntimeException("Failed to add user. Reason: " + t.getMessage());
    }

    public User fallbackGetUserById(Long id, Throwable t) {
        // Throwing a runtime exception with a relevant error message
        throw new RuntimeException("Failed to retrieve user with ID: " + id + ". Reason: " + t.getMessage());
    }

    public List<User> fallbackGetAllUsers(Throwable t) {
        // Throwing a runtime exception with a relevant error message
        throw new RuntimeException("Failed to retrieve all users. Reason: " + t.getMessage());
    }

    public User fallbackUpdateUser(Long id, User userDetails, Throwable t) {
        // Throwing a runtime exception with a relevant error message
        throw new RuntimeException("Failed to update user with ID: " + id + ". Reason: " + t.getMessage());
    }

    public void fallbackDeleteUser(Long id, Throwable t) {
        // Throwing a runtime exception with a relevant error message
        throw new RuntimeException("Failed to delete user with ID: " + id + ". Reason: " + t.getMessage());
    }
}
