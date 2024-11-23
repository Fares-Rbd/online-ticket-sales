package com.fares.eventmanagement.dto;

import com.fares.eventmanagement.entities.Categorie;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@ToString
public class EvenementDTO {
    private String nomEvenement;
    private int nbPlacesRestantes;
    private LocalDate dateEvenement;
    private Set<Long> categoriesid;
}
