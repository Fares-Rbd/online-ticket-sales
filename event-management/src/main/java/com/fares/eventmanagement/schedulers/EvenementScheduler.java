package com.fares.eventmanagement.schedulers;

import com.fares.eventmanagement.entities.Categorie;
import com.fares.eventmanagement.repositories.CategorieRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EvenementScheduler {

    private final CategorieRepository categorieRepository;

    public EvenementScheduler(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    @Scheduled(fixedRate = 15000)
    @Transactional
    public void listEvenementsByCategory() {
        System.out.println("Listing events by category:");
        for (Categorie categorie : categorieRepository.findAll()) {
            System.out.println("Category: " + categorie.getNomCategorie());
            categorie.getEvenements().forEach(e -> System.out.println("- " + e.getNomEvenement()));
        }
    }
}
