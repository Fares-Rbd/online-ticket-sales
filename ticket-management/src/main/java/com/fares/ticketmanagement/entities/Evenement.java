package com.fares.ticketmanagement.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Evenement {

    private Long id;
    private String nomEvenement;
    private int nbPlacesRestantes;
    private String dateEvenement;
}
