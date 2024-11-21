package com.fares.eventmanagement.services;

import com.fares.eventmanagement.dto.EvenementDTO;
import com.fares.eventmanagement.entities.Categorie;
import com.fares.eventmanagement.entities.Evenement;
import com.fares.eventmanagement.mapper.EvenementMapper;
import com.fares.eventmanagement.repositories.CategorieRepository;
import com.fares.eventmanagement.repositories.EvenementRepository;
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
    public Evenement ajouterEvenement(EvenementDTO evenementDTO) {

        Set<Long> categories = evenementDTO.getCategoriesid();
        categories.forEach(categorie -> {
            Optional<Categorie> existingCategorie = categorieRepository.findById(categorie);
            if (existingCategorie.isEmpty()) {
                throw new RuntimeException("No Category not found with this id: " + categorie);
            }
        });

        Evenement evenement =  evenementMapper.toEntity(evenementDTO);
        return evenementRepository.save(evenement);
    }

    // D. Update remaining seats for an event (called by Ticket Management Microservice)
    public Evenement updateRemainingSeats(Long idEvenement, int remainingSeats) {
        Evenement evenement = evenementRepository.findById(idEvenement)
                .orElseThrow(() -> new RuntimeException("Event not found with ID: " + idEvenement));

        if (remainingSeats < 0) {
            throw new UnsupportedOperationException("Not enough seats available!");
        }

        evenement.setNbPlacesRestantes(remainingSeats);
        return evenementRepository.save(evenement);
    }

    public Evenement findByName(String nomEvt) {
        return evenementRepository.findByNomEvenement(nomEvt)
                .orElseThrow(() -> new RuntimeException("Event not found with name: " + nomEvt));
    }

    public Evenement findById(Long id) {
        return evenementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with the id: " + id));
    }

    public List<Evenement> findAllEvents() {
        return evenementRepository.findAll();
    }
}
