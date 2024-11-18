package com.fares.ticketmanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Event {
    private Long id;
    private String name; // Example additional field
    private int nbPlacesRestantes;
}
