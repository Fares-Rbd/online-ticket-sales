package com.fares.eventmanagement.repositories;

import com.fares.eventmanagement.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
}
