package com.example.ElectricStations.Controller;

import com.example.ElectricStations.entities.Disponibilite;
import com.example.ElectricStations.repositories.DisponibiliteRepository;
import com.example.ElectricStations.services.DisponibiliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/disponibilites")
@CrossOrigin("http://localhost:4200")
public class DisponibiliteController {
    private final DisponibiliteService disponibiliteService;




    @Autowired
    public DisponibiliteController(DisponibiliteService disponibiliteService, DisponibiliteRepository disponibiliteRepository) {
        this.disponibiliteService = disponibiliteService;

    }




    @PostMapping("/dispo")
    public ResponseEntity<Disponibilite> createDisponibilite(@RequestBody Disponibilite disponibilite) {
        LocalDateTime dateDebut = disponibilite.getDateDebut();
        LocalDateTime dateFin = disponibilite.getDateFin();
        String etat = disponibilite.getEtat();

        // Appel du service pour créer la disponibilité
        Disponibilite createdDisponibilite = disponibiliteService.createDisponibilite(
                disponibilite.getStation().getId(),
                disponibilite.getBorne().getId(),
                dateDebut,
                dateFin,
                etat
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(createdDisponibilite);
    }

    @GetMapping("/disponibilites")
    public List<Disponibilite> getAllDisponibilites() {
        return disponibiliteService.getAllDisponibilites();
    }


    @GetMapping("/{disponibiliteId}")
    public Disponibilite getDisponibiliteById(@PathVariable String disponibiliteId) {
        return disponibiliteService.findById(disponibiliteId);
    }
    @GetMapping("/{stationId}/{borneId}")
    public ResponseEntity<List<Disponibilite>> getDisponibilite(
            @PathVariable String stationId,
            @PathVariable String borneId
    ) {
        List<Disponibilite> disponibilite = disponibiliteService.getDisponibiliteByStationAndBorne(stationId, borneId);
        if (disponibilite != null) {
            return ResponseEntity.ok(disponibilite);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}





