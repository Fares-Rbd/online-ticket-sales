package com.fares.eventmanagement.services;

import com.fares.eventmanagement.dto.EvenementDTO;
import com.fares.eventmanagement.entities.Categorie;
import com.fares.eventmanagement.entities.Evenement;
import com.fares.eventmanagement.mapper.EvenementMapper;
import com.fares.eventmanagement.repositories.CategorieRepository;
import com.fares.eventmanagement.repositories.EvenementRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EvenementService {

    private final EvenementRepository evenementRepository;
    private final CategorieRepository categorieRepository;
    private final EvenementMapper evenementMapper;

    public EvenementService(EvenementRepository evenementRepository, CategorieRepository categorieRepository, EvenementMapper evenementMapper) {
        this.evenementRepository = evenementRepository;
        this.categorieRepository = categorieRepository;
        this.evenementMapper = evenementMapper;
    }

    // A. Add events with associated categories
    @CircuitBreaker(name = "evenementService", fallbackMethod = "fallbackAjouterEvenement")
    @Retry(name = "evenementService", fallbackMethod = "fallbackAjouterEvenement")
    public Evenement ajouterEvenement(EvenementDTO evenementDTO) {
        Set<Long> categories = evenementDTO.getCategoriesid();
        categories.forEach(categorie -> {
            Optional<Categorie> existingCategorie = categorieRepository.findById(categorie);
            if (existingCategorie.isEmpty()) {
                throw new RuntimeException("No Category found with this id: " + categorie);
            }
        });

        Evenement evenement =  evenementMapper.toEntity(evenementDTO);
        return evenementRepository.save(evenement);
    }

    // D. Update remaining seats for an event (called by Ticket Management Microservice)
    @CircuitBreaker(name = "evenementService", fallbackMethod = "fallbackUpdateRemainingSeats")
    @Retry(name = "evenementService", fallbackMethod = "fallbackUpdateRemainingSeats")
    public Evenement updateRemainingSeats(Long idEvenement, int remainingSeats) {
        Evenement evenement = evenementRepository.findById(idEvenement)
                .orElseThrow(() -> new RuntimeException("Event not found with ID: " + idEvenement));

        if (remainingSeats < 0) {
            throw new UnsupportedOperationException("Not enough seats available!");
        }

        evenement.setNbPlacesRestantes(remainingSeats);
        return evenementRepository.save(evenement);
    }

    // Get event by name
    @CircuitBreaker(name = "evenementService", fallbackMethod = "fallbackFindByName")
    @Retry(name = "evenementService", fallbackMethod = "fallbackFindByName")
    public Evenement findByName(String nomEvt) {
        return evenementRepository.findByNomEvenement(nomEvt)
                .orElseThrow(() -> new RuntimeException("Event not found with name: " + nomEvt));
    }

    // Get event by ID
    @CircuitBreaker(name = "evenementService", fallbackMethod = "fallbackFindById")
    @Retry(name = "evenementService", fallbackMethod = "fallbackFindById")
    public Evenement findById(Long id) {
        return evenementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with the id: " + id));
    }

    // Get all events
    @CircuitBreaker(name = "evenementService", fallbackMethod = "fallbackFindAllEvents")
    @Retry(name = "evenementService", fallbackMethod = "fallbackFindAllEvents")
    public List<Evenement> findAllEvents() {
        return evenementRepository.findAll();
    }

    // Fallback methods for resilience
    public Evenement fallbackAjouterEvenement(EvenementDTO evenementDTO, Throwable t) {
        // Throwing a runtime exception with a relevant error message
        throw new RuntimeException("Failed to add event. Reason: " + t.getMessage());
    }

    public Evenement fallbackUpdateRemainingSeats(Long idEvenement, int remainingSeats, Throwable t) {
        // Throwing a runtime exception with a relevant error message
        throw new RuntimeException("Failed to update remaining seats for event ID: " + idEvenement + ". Reason: " + t.getMessage());
    }

    public Evenement fallbackFindByName(String nomEvt, Throwable t) {
        // Throwing a runtime exception with a relevant error message
        throw new RuntimeException("Failed to retrieve event with name: " + nomEvt + ". Reason: " + t.getMessage());
    }

    public Evenement fallbackFindById(Long id, Throwable t) {
        // Throwing a runtime exception with a relevant error message
        throw new RuntimeException("Failed to retrieve event with ID: " + id + ". Reason: " + t.getMessage());
    }

    public List<Evenement> fallbackFindAllEvents(Throwable t) {
        // Throwing a runtime exception with a relevant error message
        throw new RuntimeException("Failed to retrieve all events. Reason: " + t.getMessage());
    }
}
