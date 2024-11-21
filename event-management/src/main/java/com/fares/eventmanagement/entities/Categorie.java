package com.fares.eventmanagement.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codeCategorie;

    @Column(nullable = false)
    private String nomCategorie;

    @ManyToMany(mappedBy = "categories")
    @JsonBackReference
    private Set<Evenement> evenements;
}
