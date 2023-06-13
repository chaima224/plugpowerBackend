package com.example.ElectricStations.repositories;
import com.example.ElectricStations.entities.Borne;
import com.example.ElectricStations.entities.Stations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StationsRepository extends MongoRepository<Stations,String> {

    //List<Stations> findByLocationNear(Point point, Distance distance);

    StationsRepository findByBornesContains(Borne borne);
    List<Stations> findByBornes(String id);


    Stations findByName(String stationName);
}
