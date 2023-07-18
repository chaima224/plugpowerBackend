package com.example.ElectricStations.services;

import com.example.ElectricStations.entities.Borne;
import com.example.ElectricStations.entities.Disponibilite;
import com.example.ElectricStations.entities.Stations;
import com.example.ElectricStations.repositories.BorneRepository;
import com.example.ElectricStations.repositories.DisponibiliteRepository;
import com.example.ElectricStations.repositories.StationsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DisponibiliteService {
    private final DisponibiliteRepository disponibiliteRepository;
    private final StationsRepository stationRepository;
    private final BorneRepository borneRepository;

    public DisponibiliteService(DisponibiliteRepository disponibiliteRepository, StationsRepository stationRepository, BorneRepository borneRepository) {
        this.disponibiliteRepository = disponibiliteRepository;
        this.stationRepository = stationRepository;
        this.borneRepository = borneRepository;
    }


    public Disponibilite createDisponibilite(String stationId, String borneId, LocalDateTime dateDebut, LocalDateTime dateFin, String etat) {
        Disponibilite disponibilite = new Disponibilite();

        // Charger la station à partir de son ID et définir son nom
        Stations station = stationRepository.findById(stationId).orElse(null);
        if (station != null) {
            disponibilite.setStation(station);
            disponibilite.setNomStation(station.getName());
        }

        // Charger la borne à partir de son ID et définir son nom
        Borne borne = borneRepository.findById(borneId).orElse(null);
        if (borne != null) {
            disponibilite.setBorne(borne);
            disponibilite.setNomBorne(borne.getName());
        }

        disponibilite.setEtat(etat);

        // Ajouter une condition pour définir les dates uniquement si l'état est "indisponible"
        if (etat.equals("Unavailable")) {
            disponibilite.setDateDebut(dateDebut);
            disponibilite.setDateFin(dateFin);
        }

        // Enregistrer la disponibilité dans la base de données
        return disponibiliteRepository.save(disponibilite);
    }

    public List<Disponibilite> getAllDisponibilites() {
        return disponibiliteRepository.findAll();
    }

    public Disponibilite findById(String id) {
        return disponibiliteRepository.findById(id)
                .orElse(null);
    }
    public List<Disponibilite> getDisponibiliteByStationAndBorne(String stationId, String borneId) {
        Stations station = new Stations();
        station.setId(stationId);

        Borne borne = new Borne();
        borne.setId(borneId);

        return disponibiliteRepository.findByStationAndBorne(station, borne);
    }

}
