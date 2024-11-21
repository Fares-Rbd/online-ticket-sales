package com.fares.eventmanagement.controllers;

import com.fares.eventmanagement.dto.EvenementDTO;
import com.fares.eventmanagement.entities.Evenement;
import com.fares.eventmanagement.services.EvenementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evenements")
public class EvenementController {

    private final EvenementService evenementService;

    public EvenementController(EvenementService evenementService) {
        this.evenementService = evenementService;
    }

    @GetMapping()
    public ResponseEntity<List<Evenement>> findAllEvents() {
        return ResponseEntity.ok(evenementService.findAllEvents());
    }

    @PostMapping
    public ResponseEntity<Evenement> ajouterEvenement(@RequestBody EvenementDTO evenement) {
        return ResponseEntity.ok(evenementService.ajouterEvenement(evenement));
    }

    @PutMapping("/{idEvenement}/seats")
    public ResponseEntity<Evenement> updateRemainingSeats(
            @PathVariable Long idEvenement,
            @RequestParam int remainingSeats) {
        return ResponseEntity.ok(evenementService.updateRemainingSeats(idEvenement, remainingSeats));
    }

    @GetMapping("/by-name")
    public ResponseEntity<Evenement> findByName(@RequestParam String nomEvt) {
        return ResponseEntity.ok(evenementService.findByName(nomEvt));
    }

    @GetMapping("/by-id")
    public ResponseEntity<Evenement> findById(@RequestParam Long id) {
        return ResponseEntity.ok(evenementService.findById(id));
    }
}
