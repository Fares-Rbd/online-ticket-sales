package com.fares.ticketmanagement.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Evenement {

    private Long id;                // Event ID
    private String nomEvenement;    // Event name
    private int nbPlacesRestantes;  // Remaining seats for the event
    private String dateEvenement;   // Event date (optional, based on your needs)
}
