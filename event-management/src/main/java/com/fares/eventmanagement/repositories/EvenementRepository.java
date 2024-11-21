package com.fares.eventmanagement.repositories;

import com.fares.eventmanagement.entities.Evenement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EvenementRepository extends JpaRepository<Evenement, Long> {
    Optional<Evenement> findByNomEvenement(String nomEvenement);
}
