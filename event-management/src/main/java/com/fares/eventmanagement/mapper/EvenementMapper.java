package com.fares.eventmanagement.mapper;

import com.fares.eventmanagement.dto.EvenementDTO;
import com.fares.eventmanagement.entities.Categorie;
import com.fares.eventmanagement.entities.Evenement;
import com.fares.eventmanagement.repositories.CategorieRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class EvenementMapper {

    private final CategorieRepository categorieRepository;

    public EvenementMapper(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    public Evenement toEntity(EvenementDTO evenementDTO) {
        Evenement evenement = new Evenement();
        evenement.setNomEvenement(evenementDTO.getNomEvenement());
        evenement.setNbPlacesRestantes(evenementDTO.getNbPlacesRestantes());
        evenement.setDateEvenement(evenementDTO.getDateEvenement());
        Set<Categorie> categories  = new HashSet<>();
        for(Long id : evenementDTO.getCategoriesid()){
            categories.add(categorieRepository.findById(id).get());
        }
        evenement.setCategories(categories);

        return evenement;
    }

    public EvenementDTO toDTO(Evenement evenement) {
        EvenementDTO evenementDTO = new EvenementDTO();
        evenementDTO.setNomEvenement(evenement.getNomEvenement());
        evenementDTO.setNbPlacesRestantes(evenement.getNbPlacesRestantes());
        evenementDTO.setDateEvenement(evenement.getDateEvenement());

        Set<Long> categoryIds = new HashSet<>();
        for (Categorie categorie : evenement.getCategories()) {
            categoryIds.add(categorie.getId());
        }
        evenementDTO.setCategoriesid(categoryIds);

        return evenementDTO;
    }
}
