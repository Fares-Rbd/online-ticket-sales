package com.fares.ticketmanagement.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long id;
    private String name;
    private String trancheAge; // Age group
}