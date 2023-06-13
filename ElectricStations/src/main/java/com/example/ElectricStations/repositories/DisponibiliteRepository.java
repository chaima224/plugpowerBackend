package com.example.ElectricStations.repositories;

import com.example.ElectricStations.entities.Borne;
import com.example.ElectricStations.entities.Disponibilite;
import com.example.ElectricStations.entities.Stations;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DisponibiliteRepository extends MongoRepository<Disponibilite, String> {
    Disponibilite findByStationAndBorne(Stations station, Borne borne);
}
