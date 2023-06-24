package com.example.ElectricStations.repositories;

import com.example.ElectricStations.entities.Evaluation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EvaluationRepository extends MongoRepository<Evaluation , String> {
    List<Evaluation> findByStationId(String stationId);

    List<Evaluation> findByStationIdAndStatus(String stationId, String approved);
}
