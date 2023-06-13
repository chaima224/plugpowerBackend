package com.example.ElectricStations.repositories;

import com.example.ElectricStations.entities.Borne;
import com.example.ElectricStations.enums.Connecteur;
import com.example.ElectricStations.enums.Mode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BorneRepository extends MongoRepository<Borne, String> {

    List<Borne> findByPuissanceAndModeAndConnecteur( float puissance, Mode mode, Connecteur connecteur);

    Optional<Borne> findById();

    List<Borne> findByMode(Mode mode);

    List<Borne> findByPuissance(Float aFloat);


    List<Borne> findByConnecteur(Connecteur connecteur);

    List<Borne> findAllByNameIn(List<String> nomBornes);


    Borne findByName(String borneName);
}
